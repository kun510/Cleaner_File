package com.kun510.cleaner.ui.startup

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.kun510.cleaner.ui.settings.display.theme.style.AppTheme

import kotlinx.coroutines.flow.MutableStateFlow

class StartupActivity : AppCompatActivity() {

    val consentFormShown = MutableStateFlow(value = false)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    StartupComposable(activity = this@StartupActivity)
                }
            }
        }

    }

    /**
     * Loads the consent form for user messaging platform (UMP) based on consent status.
     *
     * This function initiates the loading of the consent form using UserMessagingPlatform (UMP) API.
     * Upon successful loading of the consent form, it assigns the form to a local variable `consentForm`.
     * If user consent is required (`ConsentStatus.REQUIRED`), the form is displayed to the user.
     * If the consent status is not required or an error occurs during loading, the function handles this gracefully.
     *
     * @see com.google.android.gms.ads.UserMessagingPlatform
     * @see com.google.ads.consent.ConsentInformation
     */
    private fun loadForm() {

    }
}