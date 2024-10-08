package com.kun510.cleaner.utils.cleaning

import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.AdaptiveIconDrawable
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.media.MediaMetadataRetriever
import com.kun510.cleaner.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend fun getVideoThumbnail(
    videoPath: String,
    thumbnailWidth: Int = 64,
    thumbnailHeight: Int = 64,
): Bitmap? = withContext(Dispatchers.IO) {
    val mediaMetadataRetriever = MediaMetadataRetriever()
    try {
        mediaMetadataRetriever.setDataSource(videoPath)
        val bitmap: Bitmap? = mediaMetadataRetriever.getFrameAtTime(
            1000, MediaMetadataRetriever.OPTION_CLOSEST_SYNC
        )
        bitmap?.let {
            Bitmap.createScaledBitmap(it, thumbnailWidth, thumbnailHeight, false)
        }
    } catch (e: Exception) {
        null
    } finally {
        mediaMetadataRetriever.release()
    }
}

fun getFileIcon(extension: String, context: Context): Int {
    val lowercaseExtension: String = extension.lowercase()
    val resources: Resources = context.resources
    return when (lowercaseExtension) {
        in resources.getStringArray(R.array.apk_extensions) -> R.drawable.ic_apk_document
        in resources.getStringArray(R.array.image_extensions) -> R.drawable.ic_image
        in resources.getStringArray(R.array.video_extensions) -> R.drawable.ic_video_file
        in resources.getStringArray(R.array.audio_extensions) -> R.drawable.ic_audio_file
        in resources.getStringArray(R.array.archive_extensions) -> R.drawable.ic_archive_filter
        else -> R.drawable.ic_file_present
    }
}

fun Drawable.toBitmapDrawable(resources: Resources = Resources.getSystem()): BitmapDrawable {
    return when (this) {
        is BitmapDrawable -> this
        is AdaptiveIconDrawable -> {
            val bitmap: Bitmap =
                Bitmap.createBitmap(intrinsicWidth, intrinsicHeight, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(bitmap)
            setBounds(0, 0, canvas.width, canvas.height)
            draw(canvas)
            BitmapDrawable(resources, bitmap)
        }

        else -> throw IllegalArgumentException("Unsupported drawable type: ${this::class.java.name}")
    }
}