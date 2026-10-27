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

## Diagnóstico de validación facial

Con los registros habilitados, la SDK permite distinguir la evaluación del puntaje facial, el resultado de la validación adicional de la selfie y el motivo por el que se solicita repetir la captura.

| Evento | Interpretación |
| --- | --- |
| `confidence_evaluated` | Compara el puntaje recibido con el mínimo configurado. |
| `selfie_validation_result` | Distingue una validación aceptada, rechazada o no disponible. |
| `selfie_feedback_presented` | Identifica el motivo del mensaje de reintento. |

Una evaluación de confianza aceptada no equivale a la aprobación final del proceso. Usa los callbacks públicos para determinar el resultado de la verificación. Al solicitar soporte, conserva la secuencia del intento y el motivo del reintento.

Los registros utilizan categorías y campos acotados. La SDK no incluye cuerpos completos de respuestas, imágenes, tokens ni credenciales en estos diagnósticos. El diagnóstico continúa deshabilitado de forma predeterminada y se habilita desde la configuración de la app.
