# Registros de diagnóstico — Android

Habilita la opción antes de iniciar la SDK para revisar un problema:

```kotlin
config.setDebugLogsEnabled(true)
BecomeResponseManager.getInstance().startAuthentication(activity, config)
```

El valor predeterminado es `false`; deshabilita los registros al terminar la revisión. En Java usa también `setDebugLogsEnabled(true)`. En Logcat, filtra con `tag:BecomeSDK level:DEBUG`.

Desde una terminal:

```sh
adb logcat -v threadtime 'BecomeSDK:D' '*:S'
```

Para solicitar soporte, incluye la versión de la SDK, el dispositivo, el paso visible y las líneas relevantes de `BecomeSDK`. Revisa los registros antes de compartirlos y excluye credenciales, contenido de licencias, imágenes y datos personales.
