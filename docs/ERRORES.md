# Resultados y errores en Android

## Manejar los callbacks

| Callback | Acción de la app |
| --- | --- |
| `onFinish(response)` con `SUCCES` | Consulta el resultado opcional correspondiente al flujo. En autenticación, evalúa también `authentication.result`. |
| `onFinish(response)` con `ERROR` | Regresa a la interfaz de la app y ofrece un nuevo intento iniciado por el usuario. |
| `onCancel()` | Regresa a la app sin considerar aprobada la verificación. |
| Estado `PENDING` o `NOFOUND` | No consideres aprobada la verificación. |

`SUCCES` es el nombre del estado en la API pública. Los resultados `onboarding`, `verification` y `authentication` son opcionales. Con `performVerificationCheck=false`, `onboarding` confirma la creación del proceso, no su aprobación final. Los flujos de solo documentos no requieren un resultado facial.

El mensaje recibido describe lo sucedido; no es un código de error estable. No compares mensajes traducidos para aprobar verificaciones ni para iniciar reintentos automáticos. La SDK presenta las acciones disponibles para repetir una captura dentro de su interfaz; la app no recibe un callback por cada intento de cámara.

## Resolver problemas de integración

| Situación | Qué revisar |
| --- | --- |
| No inicia la verificación | Credenciales, identificador del contrato y parámetros requeridos. Autenticación necesita un usuario existente. |
| Cámara no disponible | Dispositivo físico, permiso de cámara y solicitud del permiso en tiempo de ejecución. |
| Licencia no válida | Archivo `app/src/main/assets/com.become.blinkid.key` y coincidencia con el identificador exacto de la app. |
| Falta un módulo, clase o recurso | Archivos y dependencias de la [guía de instalación](../INSTALACION.md). Evita mezclar versiones de distribuciones. |
| Conexión interrumpida | Restablece la conexión y permite un nuevo intento iniciado por el usuario. |
| Espera del resultado final | Revisa `pollingTimeoutSeconds` y `pollingMaxAttempts` según los tiempos que necesita tu integración. |
| Captura incompleta | Permite que el usuario repita la captura y complete los lados solicitados. |
| Sesión vencida o no válida | Finaliza el intento actual y permite iniciar una nueva verificación. |

## Solicitar soporte

Incluye la versión de la SDK, modelo del dispositivo, versión del sistema, paso visible y descripción de cómo reproducir el problema. Si soporte necesita más información, activa temporalmente el [diagnóstico](LOGGING.md). Excluye credenciales, contenido de licencias, imágenes de documentos y datos personales.
