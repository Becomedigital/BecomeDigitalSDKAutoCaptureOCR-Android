# Demo de integración para Android

Esta aplicación muestra cómo iniciar una verificación de identidad desde una pantalla Jetpack Compose alojada en una `AppCompatActivity` y cómo recibir las respuestas de la SDK. Utiliza `becomedigitalsdk.aar` desde la raíz de esta distribución, sin duplicar el archivo binario en el demo.

## Preparar el demo

1. Abre este directorio en Android Studio. Usa JDK 21 para Gradle e instala Android SDK 36.
2. En `app/build.gradle`, reemplaza `applicationId 'com.example.become.integration'` por el identificador de aplicación autorizado para tu licencia BlinkID.
3. Copia `credentials.properties.example` como `credentials.properties` y completa `clientId`, `clientSecret` y `contractId` con los valores asignados a tu contrato.
4. Crea `app/src/main/assets` si aún no existe y guarda la licencia BlinkID correspondiente a ese identificador de aplicación en `app/src/main/assets/com.become.blinkid.key`.
5. Compila y ejecuta la aplicación en un dispositivo físico con Android 7.0 o posterior. Ingresa un ID de usuario y toca **Iniciar verificación**.

Solicita a BecomeDigital las credenciales, el identificador del contrato y la licencia correspondientes a tu aplicación. Estos datos no se incluyen en el demo. El proyecto compila sin ellos, pero la aplicación muestra un aviso y no inicia la verificación hasta que estén presentes la configuración y la licencia. Los archivos locales se excluyen de Git. `BuildConfig` incorpora las credenciales en el APK compilado localmente; no distribuyas una compilación que contenga credenciales de prueba. `BecomeSdkLauncher.kt` activa los registros de diagnóstico para las pruebas manuales.

## Estructura del proyecto

- `MainActivity.kt` aloja la interfaz Compose en una `AppCompatActivity`.
- `VerificationScreen.kt` recibe el ID de usuario, solicita el permiso de cámara y llama al lanzador.
- `BecomeSdkLauncher.kt` comprueba la configuración y la licencia locales, crea `BDIVConfig`, conserva el administrador de callbacks y maneja la finalización o cancelación.
- `app/src/main/res/values-es` muestra la localización al español de la aplicación anfitriona y los textos admitidos por la SDK.
- `app/build.gradle` declara el AAR de la SDK y sus dependencias necesarias. `AndroidManifest.xml` declara la actividad y el permiso de cámara.

Consulta la [guía de instalación](../../INSTALACION.md), la [guía de localización](../../docs/PERSONALIZACION_TEXTOS.md), la [guía de diagnóstico](../../docs/LOGGING.md) y la [guía de errores](../../docs/ERRORES.md) para adaptar esta integración a tu aplicación.
