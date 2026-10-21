# BecomeDigital SDK AutoCapture OCR — Android

Native identity verification with automatic document capture, OCR-assisted recognition, a manual capture fallback, and facial verification. This repository distributes `becomedigitalsdk.aar` and integration documentation for client applications.

## Features

- Onboarding and authentication with typed results.
- Automatic document scanning with framing, image-quality feedback, and front/back guidance.
- Manual capture after 30 seconds by default when automatic scanning has not completed, with document recognition and retry support.
- Compose screens, searchable country selection, and contract-assigned branding and text.
- Host-selected light, dark, or system appearance and optional screen-capture protection.

## Requirements and dependencies

| Component | Version |
| --- | --- |
| `minSdk` | 24 |
| `compileSdk` | 36 |
| Recommended `targetSdk` | 36 |
| Java source/target compatibility | 11 |
| Recommended Android Gradle Plugin | 9.0.1 |
| Recommended Kotlin | 2.2.10 |
| BlinkID Core / UX | 8002.0.0 |
| Compose BOM | 2026.06.00 |
| Amplify UI Face Liveness | 1.10.0 |
| Amplify AWS Cognito | 2.33.0 |
| CameraX | 1.5.3 |

Use a JDK supported by the selected Android Gradle Plugin. The Java 11 setting above controls compilation compatibility. Enable Compose for the SDK and Face Liveness UI. With Kotlin 2.x, apply the Compose compiler plugin with the same Kotlin version.

This repository is a local AAR distribution, not a Maven publication. The Java package remains `com.becomedigital.sdk.identity.becomedigitalsdk`.

## Installation

Copy `becomedigitalsdk.aar` into the host application's `app/libs` directory. Configure dependency repositories with `google()` and `mavenCentral()`.

Example root build configuration:

```groovy
buildscript {
    ext.kotlin_version = "2.2.10"
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
    dependencies {
        classpath "com.android.tools.build:gradle:9.0.1"
        classpath "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version"
        classpath "org.jetbrains.kotlin:compose-compiler-gradle-plugin:$kotlin_version"
    }
}
```

Example application module configuration:

```groovy
plugins {
    id "com.android.application"
    id "org.jetbrains.kotlin.android"
    id "org.jetbrains.kotlin.plugin.compose"
}

android {
    compileSdk 36
    defaultConfig {
        minSdk 24
        targetSdk 36
    }
    compileOptions {
        coreLibraryDesugaringEnabled true
        sourceCompatibility JavaVersion.VERSION_11
        targetCompatibility JavaVersion.VERSION_11
    }
    kotlinOptions { jvmTarget = "11" }
    buildFeatures { compose true }
}

dependencies {
    implementation files("libs/becomedigitalsdk.aar")
    implementation platform("androidx.compose:compose-bom:2026.06.00")
    coreLibraryDesugaring "com.android.tools:desugar_jdk_libs:2.1.5"

    implementation "androidx.appcompat:appcompat:1.6.1"
    implementation "androidx.constraintlayout:constraintlayout:2.1.4"
    implementation "androidx.core:core-ktx:1.10.1"
    implementation "androidx.activity:activity-ktx:1.8.2"
    implementation "androidx.fragment:fragment-ktx:1.6.2"
    implementation "androidx.navigation:navigation-fragment:2.5.3"
    implementation "androidx.navigation:navigation-ui:2.5.3"

    implementation "androidx.camera:camera-core:1.5.3"
    implementation "androidx.camera:camera-camera2:1.5.3"
    implementation "androidx.camera:camera-view:1.5.3"
    implementation "androidx.camera:camera-lifecycle:1.5.3"
    implementation "com.google.guava:guava:33.3.1-android"

    implementation "com.google.code.gson:gson:2.10.1"
    implementation "com.squareup.okhttp3:okhttp:4.9.3"
    implementation "com.android.volley:volley:1.2.1"
    implementation "com.github.bumptech.glide:glide:4.10.0"

    implementation "com.amplifyframework.ui:liveness:1.10.0"
    implementation "com.amplifyframework:aws-auth-cognito:2.33.0"
    implementation "androidx.compose.material3:material3"
    implementation "androidx.activity:activity-compose:1.12.4"

    implementation "com.microblink:blinkid-core:8002.0.0"
    implementation "com.microblink:blinkid-ux:8002.0.0"

    implementation "androidx.room:room-runtime:2.6.1"
    implementation "androidx.lifecycle:lifecycle-livedata:2.8.7"
    implementation "androidx.lifecycle:lifecycle-viewmodel:2.8.7"
}
```

A local AAR carries no Gradle dependency metadata. Declare its external dependencies explicitly and align Compose UI, runtime, and Material 3 with the indicated BOM. If the app already uses a listed library, resolve its version consistently. When upgrading from the earlier Capture integration, remove the old `capture-core` and `capture-ux` dependencies.

### Application license and permissions

Obtain a BlinkID Core license from BecomeDigital for the host application's `applicationId`. Add it as `app/src/main/assets/com.become.blinkid.key`. License contents and application credentials are not included in this repository. A different application identifier, including a development variant, requires a matching license.

The AAR declares Internet and camera permissions. The host app must handle the runtime camera permission before a flow that needs it.

```xml
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.CAMERA" />
```

## Start verification

Register a callback before starting and retain the callback manager for the flow. The following members belong to the host activity:

```kotlin
import com.becomedigital.sdk.identity.becomedigitalsdk.core.callback.BecomeCallBackManager
import com.becomedigital.sdk.identity.becomedigitalsdk.core.callback.BecomeInterfaseCallback
import com.becomedigital.sdk.identity.becomedigitalsdk.core.callback.BecomeResponseManager
import com.becomedigital.sdk.identity.becomedigitalsdk.models.BDIVConfig
import com.becomedigital.sdk.identity.becomedigitalsdk.models.BDIdentityVerificationResponse
import com.becomedigital.sdk.identity.becomedigitalsdk.models.DocumetType

private val callbackManager = BecomeCallBackManager.createNew()

private fun startOnboarding() {
    BecomeResponseManager.getInstance().registerCallback(
        callbackManager,
        object : BecomeInterfaseCallback {
            override fun onFinish(response: BDIdentityVerificationResponse) {
                when (response.responseStatus) {
                    BDIdentityVerificationResponse.StatusType.SUCCES -> {
                        response.onboarding?.let { /* Creation acknowledged. */ }
                        response.verification?.let { /* Final result available. */ }
                        response.authentication?.let { /* Evaluate it.result. */ }
                    }
                    BDIdentityVerificationResponse.StatusType.ERROR -> {
                        // Restore the host UI and offer a user-initiated restart.
                    }
                    else -> Unit
                }
            }

            override fun onCancel() {
                // Restore the host screen without approving verification.
            }
        }
    )

    val config = BDIVConfig(
        "YOUR_CLIENT_ID", "YOUR_CLIENT_SECRET", "YOUR_CONTRACT_ID",
        arrayOf(DocumetType.DNI, DocumetType.PASSPORT),
        true, "YOUR_USER_ID"
    )
    config.flow = BDIVConfig.Flow.Onboarding
    config.themeMode = BDIVConfig.ThemeMode.system
    BecomeResponseManager.getInstance().startAuthentication(this, config)
}
```

The existing names `clienId`, `DocumetType`, `SUCCES`, and `startAuthentication` are retained for compatibility. `startAuthentication` starts the flow selected in `BDIVConfig`. For authentication, use `BDIVConfig.Flow.Authentication`; `emptyArray()` is supported when document capture is not required.

## Public configuration

| Parameter | Default | Purpose |
| --- | --- | --- |
| `clienId`, `clientSecret`, `contractId`, `userId` | Required | Identify the client, contract, and user. |
| `documentTypes` | Required for onboarding | `DocumetType[]`: `DNI`, `PASSPORT`, `LICENSE`; authentication may use an empty array. |
| `allowLibraryLoading` | Constructor argument | Allow the supported gallery-selection option. |
| `flow` | `Flow.Onboarding` | `Onboarding` or `Authentication`. |
| `performVerificationCheck` | `true` | Wait for the final onboarding result. |
| `pollingMaxAttempts` | `0` | Maximum result checks; `0` has no attempt limit. |
| `pollingTimeoutSeconds` | 2 | Timeout in seconds for each result check. |
| `debugLogsEnabled` | `false` | Opt in to diagnostic logs during an investigation. |
| `preventScreenCapture` | `false` | Enable SDK screen-capture protection. |
| `country`, `state` | `null` | Prefilled two-letter country code and US state, when applicable. |
| `nationalIdType`, `nationalIdTypeChoices`, `documentNumber` | Empty | Optional prefilled document details. |
| `themeMode` | `ThemeMode.system` | `light`, `dark`, or `system`. |

Optional fields have Java setters and Kotlin property accessors. The SDK presents the steps, colors, logo, layout, and text assigned to the contract. Design overrides are not part of `BDIVConfig`. Override individual phrases using Android string resources; see [localization](docs/PERSONALIZACION_TEXTOS.md).

The initial animated loader remains visible until the flow and its presentation are ready. It starts with the host theme's primary color, falling back to its accent color, and a localized loading message. The first visible step already uses the configured appearance. Initialization failures use the existing callback.

The manual capture fallback's default 30-second delay is independent of result-check timeouts. For the general connection/read/write timeout resource, see [the configuration example](examples/network-config/res/values/become_config.xml).

## Typed responses

`onFinish` supplies `BDIdentityVerificationResponse` with `responseStatus`, `message`, and optional result objects:

| Object | Public fields | Meaning |
| --- | --- | --- |
| `onboarding` | `code`, `message`, `urlResource`, `userId` | Creation acknowledged when `performVerificationCheck=false`; this is not a final approval. |
| `verification` | `urlGetData` | Final onboarding result when result checking is enabled. |
| `authentication` | `company`, `confidence`, `executionId`, `liveness`, `result`, `userId` | Authentication details; evaluate `result` separately. |

Result objects can be `null`. Status values include `SUCCES`, `ERROR`, `PENDING`, `NOFOUND`, and `CANCEL`. Normal cancellation uses `onCancel()`. A document-only flow does not require a facial result. Use the concrete result objects instead of dictionary-based response access; see [error handling](docs/ERRORES.md).

## Integration guides

- [Error handling](docs/ERRORES.md)
- [Localization and text overrides](docs/PERSONALIZACION_TEXTOS.md)
- [Diagnostic logging](docs/LOGGING.md)
- [Screen-capture protection](SCREEN_CAPTURE_CONFIGURATION.md)
- [Artifact checksums](CHECKSUMS.sha256)

Before releasing the host app, test automatic/manual document capture, retry, permissions, facial verification, callbacks, and cancellation on a physical device using its licensed application identifier. Check the final application's native libraries and Android 15+ compatibility using the Android tooling required by your distribution channel.
