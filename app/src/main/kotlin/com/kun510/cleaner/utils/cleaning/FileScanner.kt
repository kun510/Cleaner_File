package com.kun510.cleaner.utils.cleaning

import android.content.res.Resources
import android.os.Environment
import com.kun510.cleaner.R
import com.kun510.cleaner.constants.cleaning.ExtensionsConstants
import com.kun510.cleaner.data.datastore.DataStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.withContext
import java.io.File

/**
 * A utility class for scanning and filtering files based on user-defined preferences.
 *
 * This class scans and filters files in the external storage directory based on user preferences such as file types (e.g., generic, archive, APK, audio, video, image files). The preferences are retrieved from a DataStore instance and the file types are defined in the provided Resources instance.
 *
 * @property dataStore A DataStore instance used for accessing user preferences.
 * @property resources A Resources instance used for accessing string arrays that define file types.
 */
class FileScanner(private val dataStore: DataStore, private val resources: Resources) {

    private var preferences: Map<String, Boolean> = emptyMap()
    private var filteredFiles: List<File> = emptyList()

    /**
     * Initiates the file scanning process asynchronously.
     *
     * This function loads user preferences, retrieves all files in the external storage directory, applies filters based on the preferences, and stores the filtered files. The scanning process runs asynchronously to avoid blocking the main thread.
     *
     * @throws Exception If an error occurs during the scanning process.
     */
    suspend fun startScanning() = withContext(Dispatchers.IO) {
        loadPreferences()
        filteredFiles = filterFiles(getAllFiles()).toList()
    }

    /**
     * Loads user preferences from the data datastore into the [preferences] map.
     *
     * The preferences include whether to filter generic files, archive files, APK files, audio files, video files, and image files.
     */
    private suspend fun loadPreferences() {
        preferences = with(ExtensionsConstants) {
            mapOf(
                GENERIC_EXTENSIONS to dataStore.genericFilter.first(),
                ARCHIVE_EXTENSIONS to dataStore.deleteArchives.first(),
                APK_EXTENSIONS to dataStore.deleteApkFiles.first(),
                IMAGE_EXTENSIONS to dataStore.deleteImageFiles.first(),
                AUDIO_EXTENSIONS to dataStore.deleteAudioFiles.first(),
                VIDEO_EXTENSIONS to dataStore.deleteVideoFiles.first()
            )
        }
    }

    /**
     * Retrieves all files from the external storage directory recursively.
     *
     * @return A list of all files found in the external storage directory.
     */
    private fun getAllFiles(): List<File> {
        val files: MutableList<File> = mutableListOf()
        val stack = ArrayDeque<File>()
        val root: File = Environment.getExternalStorageDirectory()
        stack.addFirst(root)

        while (stack.isNotEmpty()) {
            val currentFile: File = stack.removeFirst()
            if (currentFile.isDirectory) {
                currentFile.listFiles()?.forEach { stack.addLast(it) }
            } else {
                files.add(currentFile)
            }
        }

        return files
    }

    private fun filterFiles(allFiles: List<File>): Flow<File> = flow {
        allFiles.filter(::shouldFilterFile).forEach { emit(it) }
    }

    private fun shouldFilterFile(file: File): Boolean {
        return preferences.any { (key: String, value: Boolean) ->
            with(ExtensionsConstants) {
                when (key) {
                    GENERIC_EXTENSIONS -> {
                        val extensions: Array<String> =
                            resources.getStringArray(R.array.generic_extensions)
                        value && extensions.map { it.removePrefix(".") }.contains(file.extension)
                    }

                    ARCHIVE_EXTENSIONS -> {
                        val extensions: Array<String> =
                            resources.getStringArray(R.array.archive_extensions)
                        value && extensions.contains(file.extension)
                    }

                    APK_EXTENSIONS -> {
                        val extensions: Array<String> =
                            resources.getStringArray(R.array.apk_extensions)
                        value && extensions.contains(file.extension)
                    }

                    AUDIO_EXTENSIONS -> {
                        val extensions: Array<String> =
                            resources.getStringArray(R.array.audio_extensions)
                        value && extensions.contains(file.extension)
                    }

                    VIDEO_EXTENSIONS -> {
                        val extensions: Array<String> =
                            resources.getStringArray(R.array.video_extensions)
                        value && extensions.contains(file.extension)
                    }

                    IMAGE_EXTENSIONS -> {
                        val extensions: Array<String> =
                            resources.getStringArray(R.array.image_extensions)
                        value && extensions.contains(file.extension)
                    }

                    else -> false
                }
            }
        }
    }

    /**
     * Returns the list of filtered files.
     *
     * @return A list of files that match the user-defined preferences.
     */
    fun getFilteredFiles(): List<File> {
        return filteredFiles
    }
}