package com.becomedigital.blinkidintegration

import androidx.appcompat.app.AppCompatActivity
import com.becomedigital.sdk.identity.becomedigitalsdk.core.callback.BecomeCallBackManager
import com.becomedigital.sdk.identity.becomedigitalsdk.core.callback.BecomeInterfaseCallback
import com.becomedigital.sdk.identity.becomedigitalsdk.core.callback.BecomeResponseManager
import com.becomedigital.sdk.identity.becomedigitalsdk.models.BDIVConfig
import com.becomedigital.sdk.identity.becomedigitalsdk.models.BDIdentityVerificationResponse
import com.becomedigital.sdk.identity.becomedigitalsdk.models.DocumetType
import java.io.IOException

class BecomeSdkLauncher(
    private val activity: AppCompatActivity,
    private val onStatus: (String) -> Unit
) {
    private val callbackManager = BecomeCallBackManager.createNew()

    init {
        BecomeResponseManager.getInstance().registerCallback(
            callbackManager,
            object : BecomeInterfaseCallback {
                override fun onFinish(response: BDIdentityVerificationResponse) {
                    activity.runOnUiThread {
                        onStatus(activity.getString(R.string.sdk_status, response.responseStatus.toString()))
                    }
                }

                override fun onCancel() {
                    activity.runOnUiThread { onStatus(activity.getString(R.string.status_cancelled)) }
                }
            }
        )
    }

    fun startOnboarding(userId: String) {
        if (BuildConfig.BECOME_JWT.isBlank() ||
            BuildConfig.BECOME_RESULTS_JWT.isBlank() ||
            BuildConfig.BECOME_CONTRACT_ID.isBlank()
        ) {
            onStatus(activity.getString(R.string.credentials_missing))
            return
        }
        if (!hasBlinkIdLicense()) {
            onStatus(activity.getString(R.string.license_missing))
            return
        }
        val config = BDIVConfig(
            BuildConfig.BECOME_JWT,
            BuildConfig.BECOME_CONTRACT_ID,
            arrayOf(DocumetType.DNI, DocumetType.PASSPORT),
            true,
            userId
        )
        config.verificationResultsJwt = BuildConfig.BECOME_RESULTS_JWT
        config.flow = BDIVConfig.Flow.Onboarding
        config.setDebugLogsEnabled(true)
        onStatus(activity.getString(R.string.status_starting))
        BecomeResponseManager.getInstance().startAuthentication(activity, config)
    }

    private fun hasBlinkIdLicense(): Boolean = try {
        activity.assets.open("com.become.blinkid.key").use { it.read() != -1 }
    } catch (_: IOException) {
        false
    }
}
