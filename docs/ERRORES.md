# Manejo de errores — Android

## Callbacks públicos

| Callback | Manejo recomendado |
| --- | --- |
| `onFinish(response)` con `SUCCES` | Consulta el objeto opcional del flujo. En autenticación, evalúa también `authentication.result`. |
| `onFinish(response)` con `ERROR` | Restaura la interfaz de la app y ofrece un nuevo intento iniciado por el usuario. |
| `onFinish(response)` con `PENDING` o `NOFOUND` | No consideres aprobada la verificación. |
| `onCancel()` | Regresa a la app sin aprobar la verificación. |

`SUCCES` conserva su escritura por compatibilidad. El mensaje de error o respuesta es descriptivo, no un código estable. Los objetos tipados `onboarding`, `verification` y `authentication` son opcionales. Cuando `performVerificationCheck=false`, `onboarding` confirma el inicio y no representa una aprobación final.

La SDK muestra los reintentos de captura disponibles dentro de su interfaz. La app no recibe un callback por cada intento de cámara que no se complete.

## Problemas que puede resolver la app

- **Configuración incompleta:** revisa `clienId`, `clientSecret`, `contractId`, `userId` y los tipos documentales requeridos en onboarding.
- **Cámara no disponible o permiso denegado:** revisa el dispositivo y el permiso de cámara en tiempo de ejecución.
- **Licencia de captura:** revisa `app/src/main/assets/com.become.blinkid.key` y el `applicationId` autorizado.
- **Conexión interrumpida:** permite que el usuario inicie un nuevo intento cuando se restablezca la conexión.
- **Captura sin completar:** permite que el usuario vuelva a intentarlo dentro de la SDK.
- **Sesión de verificación vencida o no válida:** cierra el flujo actual y permite que el usuario inicie una nueva verificación.

No compares mensajes localizados para decidir si se aprueba la verificación o si se ejecutan reintentos automáticos. Si el problema persiste, comparte con soporte la versión de la SDK, el dispositivo, el paso visible y los [registros de diagnóstico](LOGGING.md) relevantes. Excluye credenciales, contenido de licencias, documentos capturados y otros datos personales.
