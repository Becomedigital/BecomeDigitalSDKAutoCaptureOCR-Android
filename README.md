# SDK de BecomeDigital AutoCapture OCR para Android

Integra la verificación de identidad en tu aplicación: onboarding, autenticación, captura documental automática, reconocimiento OCR y alternativa manual y validación facial. Esta distribución incluye `becomedigitalsdk.aar` y utiliza BlinkID para la captura de documentos.

## Antes de comenzar

- Usa Android 7.0 (API 24) o posterior; `compileSdk` 36.
- Solicita las credenciales de tu integración, el identificador del contrato y la licencia para el identificador de tu app.
- Sigue la [guía de instalación](INSTALACION.md) para agregar el archivo de la SDK, sus dependencias y los permisos necesarios.
- Realiza las pruebas de cámara, documento y validación facial en un dispositivo físico.

Esta entrega corresponde a producción. La configuración de compilación de tu app no cambia ese ambiente. Si necesitas otra entrega, solicítala a BecomeDigital con una licencia válida para el identificador de esa aplicación.

## Iniciar una verificación

Registra el callback antes de iniciar y conserva `BecomeCallBackManager` durante la verificación. El siguiente ejemplo se integra en la actividad que inicia el recorrido.

```kotlin
import androidx.appcompat.app.AppCompatActivity
import com.becomedigital.sdk.identity.becomedigitalsdk.core.callback.BecomeCallBackManager
import com.becomedigital.sdk.identity.becomedigitalsdk.core.callback.BecomeInterfaseCallback
import com.becomedigital.sdk.identity.becomedigitalsdk.core.callback.BecomeResponseManager
import com.becomedigital.sdk.identity.becomedigitalsdk.models.BDIVConfig
import com.becomedigital.sdk.identity.becomedigitalsdk.models.BDIdentityVerificationResponse
import com.becomedigital.sdk.identity.becomedigitalsdk.models.DocumetType

class VerificationActivity : AppCompatActivity() {
    private val callbackManager = BecomeCallBackManager.createNew()

    fun startOnboarding() {
        BecomeResponseManager.getInstance().registerCallback(
            callbackManager,
            object : BecomeInterfaseCallback {
                override fun onFinish(response: BDIdentityVerificationResponse) {
                    when (response.responseStatus) {
                        BDIdentityVerificationResponse.StatusType.SUCCES -> {
                            response.onboarding?.let { started ->
                                // El proceso fue creado; no es su aprobación final.
                                val userId = started.userId
                            }
                            response.verification?.let { completed ->
                                // Consulta el resultado de onboarding.
                                val resultUrl = completed.urlGetData
                            }
                            response.authentication?.let { authentication ->
                                // Evalúa la decisión de autenticación por separado.
                                val accepted = authentication.result
                            }
                        }
                        BDIdentityVerificationResponse.StatusType.ERROR -> {
                            // Regresa a la app y permite un nuevo intento.
                        }
                        else -> Unit
                    }
                }

                override fun onCancel() {
                    // Regresa a la app sin aprobar la verificación.
                }
            }
        )

        val config = BDIVConfig(
            "TU_CLIENT_ID", "TU_CLIENT_SECRET", "TU_CONTRACT_ID",
            arrayOf(DocumetType.DNI, DocumetType.PASSPORT),
            true, "TU_USER_ID"
        )
        config.flow = BDIVConfig.Flow.Onboarding
        config.themeMode = BDIVConfig.ThemeMode.system
        BecomeResponseManager.getInstance().startAuthentication(this, config)
    }
}
```

Los nombres `clienId`, `DocumetType`, `BecomeInterfaseCallback` y `SUCCES` corresponden a la API pública. `startAuthentication` inicia el flujo seleccionado en `config.flow`, incluido onboarding.

### Autenticación de un usuario existente

Asigna `config.flow = BDIVConfig.Flow.Authentication` y un `userId` existente. Cuando no se soliciten documentos, puedes pasar `emptyArray()` en el constructor.

## Configurar la integración

| Parámetro | Valor predeterminado | Uso |
| --- | --- | --- |
| `clienId`, `clientSecret`, `contractId` | Argumentos requeridos | Credenciales e identificador del contrato asignados a tu integración; no deben estar vacíos. |
| `userId` | Argumento requerido | Identificador del usuario. Debe estar informado en autenticación; onboarding también admite una cadena vacía. |
| `documentTypes` | Argumento requerido | Tipos de documento: `DNI`, `PASSPORT`, `LICENSE`. Una lista vacía utiliza los tipos disponibles para el contrato. |
| `allowLibraryLoading` | Argumento requerido | Se conserva en el constructor por compatibilidad. No selecciona ni sustituye el modo de captura del contrato. |
| `flow` | `Flow.Onboarding` | Selecciona onboarding o autenticación. |
| `performVerificationCheck` | `true` | Espera el resultado final de onboarding. Con `false`, entrega la confirmación de creación del proceso. |
| `pollingMaxAttempts` | `0` | Máximo de consultas del resultado. Con `0`, la espera no tiene límite de intentos. |
| `pollingTimeoutSeconds` | 2 segundos | Tiempo máximo de cada consulta del resultado; no es la duración máxima de toda la verificación. |
| `debugLogsEnabled` | `false` | Activa registros de diagnóstico para revisar un problema con soporte. |
| `preventScreenCapture` | `false` | Activa la protección de las pantallas de la SDK contra capturas y grabaciones. |
| `country`, `state` | `null` | País de dos letras o lista de países separados por comas; estado de EE. UU., cuando corresponda. |
| `nationalIdType`, `documentNumber` | `null` | Datos opcionales del documento precargados. |
| `nationalIdTypeChoices` | Lista vacía | Opciones de subtipo documental, cuando correspondan al contrato. |
| `themeMode` | `ThemeMode.system` | Modo claro (`light`), oscuro (`dark`) o preferencia del dispositivo (`system`). |

Asigna los parámetros opcionales mediante las propiedades o métodos `set…` de `BDIVConfig` antes de iniciar la SDK.

Si necesitas limitar la espera del resultado final, asigna un `pollingMaxAttempts` mayor que cero y maneja el callback de error. Un valor mayor en `pollingTimeoutSeconds`, por ejemplo `30`, concede más tiempo a cada consulta individual.

## Apariencia, documentos e idioma

El contrato define los pasos disponibles, la apariencia, el logo y los textos del recorrido. La app elige el modo claro, oscuro o del sistema mediante `themeMode`. `BDIVConfig` no modifica los colores, el logo ni los textos del recorrido.

La lista de documentos de la app se combina con los tipos disponibles para el contrato. Si no hay coincidencias, se utiliza la lista del contrato. Un único tipo de documento también se presenta para confirmación. La SDK solicita los lados requeridos para cada documento; los flujos que solo piden documentos no requieren una captura facial.

La pantalla de carga permanece visible hasta que el primer paso está listo. Su color inicial toma el color primario del tema de la app o su color de énfasis.

La alternativa de captura manual se habilita, de forma predeterminada, después de 30 segundos sin completar la autocaptura. Este tiempo es independiente de la consulta del resultado final.

Los textos asignados al contrato se aplican sin agregar archivos de traducción a la app. Para personalizar instrucciones de captura, prueba de vida o mensajes locales de carga y conexión, consulta la [guía de localización](docs/PERSONALIZACION_TEXTOS.md). Solo necesitas agregar las claves que quieras modificar.

## Recibir resultados y manejar la cancelación

`onFinish(response)` entrega `BDIdentityVerificationResponse`, con `responseStatus`, `message` y los resultados opcionales del flujo:

| Resultado | Campos públicos | Cómo utilizarlo |
| --- | --- | --- |
| `onboarding` | `code`, `message`, `urlResource`, `userId` | Confirma la creación cuando `performVerificationCheck=false`. No equivale a una aprobación final. |
| `verification` | `urlGetData` | Resultado final de onboarding cuando se espera su finalización. |
| `authentication` | `company`, `confidence`, `executionId`, `liveness`, `result`, `userId` | Resultado de autenticación. Evalúa también `result` antes de continuar en la app. |

Consulta únicamente el resultado que corresponda al flujo; los demás objetos pueden ser `null`. El estado `SUCCES` indica que la operación de la SDK terminó correctamente; no sustituye la evaluación del resultado de autenticación. No consideres aprobados los estados `PENDING` o `NOFOUND`.

La respuesta dispone de `toJson()` si necesitas representarla como texto. Los errores se entregan mediante `onFinish` con estado `ERROR`; la cancelación habitual se informa en `onCancel()`. Los mensajes son descriptivos: no los uses como códigos fijos para aprobar verificaciones ni para decidir reintentos automáticos. Consulta el [manejo de errores](docs/ERRORES.md).

## Guías de integración

- [Instalación, dependencias, licencia y permisos](INSTALACION.md)
- [Localización y claves admitidas](docs/PERSONALIZACION_TEXTOS.md)
- [Resultados y errores](docs/ERRORES.md)
- [Diagnóstico y soporte](docs/LOGGING.md)
- [Protección contra capturas de pantalla](SCREEN_CAPTURE_CONFIGURATION.md)
- [Optimización de la app con R8](docs/R8.md)
- [Configuración opcional del tiempo de conexión](examples/network-config/res/values/become_config.xml)
- [Cambios de la SDK](CHANGELOG.md)

Antes de publicar tu app, comprueba los permisos, la captura de los lados solicitados, los reintentos, la validación facial, los idiomas personalizados, la cancelación y el manejo de resultados.
