# Diagnóstico y soporte en Android

La opción `debugLogsEnabled` permite recopilar información para revisar un problema de integración. Su valor predeterminado es `false` y los registros no se presentan en la interfaz del usuario.

## Habilitar el diagnóstico

Activa la opción antes de iniciar el flujo:

```kotlin
config.setDebugLogsEnabled(true)
BecomeResponseManager.getInstance().startAuthentication(activity, config)
```

En Logcat de Android Studio, filtra por `tag:BecomeSDK level:DEBUG`. En Java también se habilita mediante `config.setDebugLogsEnabled(true)`.

El diagnóstico permite revisar el inicio de la verificación, los reintentos y los motivos de repetición de la captura facial. Para determinar el resultado de la verificación, utiliza siempre los callbacks públicos.

## Compartir información con soporte

Incluye la versión de la SDK, el dispositivo, la versión del sistema, el paso visible y las líneas relevantes del intento. Revisa los registros antes de compartirlos: no adjuntes credenciales, licencias, imágenes ni datos personales.

Desactiva `debugLogsEnabled` cuando termine la revisión. Los registros ayudan a investigar un fallo; no sustituyen el [manejo de resultados y errores](ERRORES.md).
