package com.becomedigital.blinkidintegration

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class MainActivity : AppCompatActivity() {
    private var status by mutableStateOf("")
    private lateinit var sdkLauncher: BecomeSdkLauncher

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        status = getString(R.string.status_ready)
        sdkLauncher = BecomeSdkLauncher(this) { status = it }

        setContent {
            MaterialTheme {
                VerificationScreen(
                    status = status,
                    onStart = sdkLauncher::startOnboarding,
                    onMessage = { status = it }
                )
            }
        }
    }
}
