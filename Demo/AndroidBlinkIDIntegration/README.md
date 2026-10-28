# Android integration demo

This app shows how to start identity verification from a Jetpack Compose screen through an `AppCompatActivity` and handle the SDK callbacks. It uses `becomedigitalsdk.aar` from the root of this distribution, so the binary is not duplicated in the demo.

## Prepare the demo

1. Open this directory in Android Studio. Use JDK 21 for Gradle and install Android SDK 36.
2. In `app/build.gradle`, replace `applicationId 'com.example.become.integration'` with the application ID covered by your BlinkID license.
3. Copy `credentials.properties.example` to `credentials.properties` and fill in `clientId`, `clientSecret`, and `contractId` with the values assigned to your contract.
4. Create `app/src/main/assets` if needed and save the BlinkID license for that application ID as `app/src/main/assets/com.become.blinkid.key`.
5. Build and run the app on a physical device with Android 7.0 or later. Enter a user ID and tap **Start onboarding**.

Contact BecomeDigital to obtain credentials, a contract ID, and a license for your app. They are not included in this demo. The project compiles without them, but the app displays a message and does not start verification until both the configuration and license are present. The local files are ignored by Git. Credentials are embedded into a locally built APK through `BuildConfig`; do not distribute a build containing test credentials. Diagnostic logs are enabled for manual testing in `BecomeSdkLauncher.kt`.

## Project map

- `MainActivity.kt` hosts the Compose UI in an `AppCompatActivity`.
- `VerificationScreen.kt` collects the user ID, requests camera permission, and calls the launcher.
- `BecomeSdkLauncher.kt` checks the local configuration and license, builds `BDIVConfig`, retains the callback manager, and handles completion or cancellation.
- `app/src/main/res/values-es` demonstrates Spanish localization for the host app and supported SDK strings.
- `app/build.gradle` declares the SDK AAR and its required dependencies. `AndroidManifest.xml` declares the activity and camera permission.

See the [installation guide](../../INSTALACION.md), [localization guide](../../docs/PERSONALIZACION_TEXTOS.md), [logging guide](../../docs/LOGGING.md), and [error handling guide](../../docs/ERRORES.md) when adapting this integration to your app.
