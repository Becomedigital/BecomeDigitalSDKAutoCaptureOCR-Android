# Demo de integración para Android

Esta aplicación utiliza el archivo de la SDK incluido en la raíz de esta distribución y la firma recomendada con JWT. Muestra cómo iniciar la verificación y recibir sus resultados desde una pantalla Jetpack Compose en una `AppCompatActivity`.

## Preparar el demo

1. Abre este directorio en Android Studio. Usa JDK 21 para Gradle y Android SDK 36.
2. En `app/build.gradle`, usa el identificador de aplicación autorizado para tu licencia BlinkID.
3. Copia `credentials.properties.example` como `credentials.properties`. Completa `jwt` y `contractId`.
4. Agrega la licencia de tu app como `app/src/main/assets/com.become.blinkid.key`.
5. Compila y ejecuta la app en un dispositivo físico con Android 7.0 o posterior. Ingresa un ID de usuario y toca **Iniciar verificación**.

El JWT debe estar vigente, corresponder a producción y autorizar tu contrato y flujo. Introduce solo el token, sin el prefijo `Bearer `. Los archivos de configuración y licencia son locales y sus valores no se incluyen en esta distribución.

## Usar el ejemplo en tu aplicación

El archivo `credentials.properties` permite introducir un JWT únicamente para esta prueba local. El valor queda incorporado a la app compilada; no publiques una app con un token de prueba. En tu integración real, obtén el JWT desde tu backend antes de iniciar la SDK y renueva los tokens vencidos. El demo no solicita ni renueva tokens.

Si falta la configuración o la licencia, el demo muestra un mensaje y no inicia la verificación. `BecomeSdkLauncher.kt` crea `BDIVConfig`, conserva los objetos necesarios y maneja los callbacks. La configuración local de diagnóstico sirve para revisar tus pruebas; desactívala al terminar.

La firma con credenciales sigue disponible y deprecada. Los ejemplos de ambas firmas y los pasos de migración están en la [guía de autenticación](../../AUTENTICACION.md).

Consulta también la [instalación](../../INSTALACION.md), [localización](../../docs/PERSONALIZACION_TEXTOS.md), [diagnóstico](../../docs/LOGGING.md) y [manejo de errores](../../docs/ERRORES.md).
