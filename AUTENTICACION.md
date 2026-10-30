# Inicialización con JWT y migración de la firma anterior — Android

La versión **1.3.0** agrega la inicialización con `jwt` como opción recomendada. La firma con `clienId` y `clientSecret` se conserva y sigue funcionando para las integraciones existentes, pero está marcada como **deprecada**. Esta actualización no anuncia una fecha de retiro de esa firma.

## Tokens de creación y consulta

Para onboarding con consulta del resultado final, recibe dos JWT vigentes desde el backend de tu aplicación, sin el prefijo `Bearer` y para el mismo ambiente y contrato:

| Operación | Permiso solicitado (`grant_type`) | Configuración pública |
| --- | --- | --- |
| Crear la verificación | `verification:create` | Argumento `jwt` del constructor. |
| Consultar el resultado | `verification:get` | `verificationResultsJwt` en Kotlin o `setVerificationResultsJwt(...)` en Java. |

Consulta la [guía de autenticación](https://becomedigital.gitbook.io/kyc_api_es/authentication), la [obtención de tokens](https://becomedigital.gitbook.io/kyc_api_es/endpoints/auth) y los [permisos de consulta de resultados](https://becomedigital.gitbook.io/kyc_api_es/endpoints/verification-results). Mantén las credenciales en tu backend. Cambiar únicamente el permiso del JWT principal a `verification:get` deja sin cubrir la creación.

En Kotlin, asigna el token de consulta antes de iniciar:

```kotlin
val config = BDIVConfig(
    creationJwt, contractId,
    arrayOf(DocumetType.DNI, DocumetType.PASSPORT),
    true, userId
)
config.verificationResultsJwt = resultsJwt
config.flow = BDIVConfig.Flow.Onboarding
BecomeResponseManager.getInstance().startAuthentication(activity, config)
```

En Java:

```java
BDIVConfig config = new BDIVConfig(
    creationJwt, contractId,
    new DocumetType[] { DocumetType.DNI, DocumetType.PASSPORT },
    true, userId
);
config.setVerificationResultsJwt(resultsJwt);
config.setFlow(BDIVConfig.Flow.Onboarding);
BecomeResponseManager.getInstance().startAuthentication(activity, config);
```

Registra el callback antes de iniciar, como en el ejemplo de la primera verificación. `verificationResultsJwt` se usa únicamente al consultar el resultado final de onboarding con `performVerificationCheck=true`. No reemplaza el token principal en la creación ni modifica `Flow.Authentication`. Con `performVerificationCheck=false`, se entrega la creación sin esperar el resultado final. En autenticación de un usuario existente, el token principal debe autorizar ese flujo.

El valor predeterminado de `verificationResultsJwt` es `null`: si lo omites, se conserva el uso del token principal para consultar. Ese token debe tener el permiso requerido; uno limitado a `verification:create` no autoriza la consulta de resultados. Un valor explícito vacío o compuesto por espacios se rechaza al intentar consultar, sin utilizar otro token como reemplazo.

Si utilizas `setJwt(...)`, asigna `verificationResultsJwt` **después**: reemplazar el JWT principal elimina el token de consulta anterior. Obtén tokens vigentes y configura ambos antes de iniciar un nuevo intento. La firma JWT no obtiene ni renueva tokens automáticamente ni cambia a credenciales si un token falla.

La firma deprecada con credenciales conserva su comportamiento y no utiliza `verificationResultsJwt`. Para migrar esa configuración, selecciona primero el constructor JWT o llama a `setJwt(...)` y después asigna el token de consulta.

## Creación correcta y consulta con HTTP 403

Una creación confirmada con HTTP 201 seguida de HTTP 403 al consultar puede indicar que el token de consulta no está autorizado para esa operación. El estado 403 no demuestra por sí solo que el token haya vencido.

Comprueba que `jwt` corresponda a creación (`verification:create`) y que hayas asignado un token de consulta (`verification:get`) a `verificationResultsJwt`. Revisa también vigencia, ambiente, contrato y permisos de tu integración. Si el problema continúa, comparte con soporte el estado HTTP y el paso afectado, sin incluir tokens, credenciales ni datos personales.

## Firma recomendada

Obtén un JWT vigente desde el backend de tu aplicación antes de iniciar la SDK. Pasa únicamente el valor del token, **sin el prefijo `Bearer `**, y el identificador del contrato.

```kotlin
val config = BDIVConfig(
    jwt, contractId,
    arrayOf(DocumetType.DNI, DocumetType.PASSPORT),
    true, userId
)
config.verificationResultsJwt = resultsJwt
config.flow = BDIVConfig.Flow.Onboarding
config.themeMode = BDIVConfig.ThemeMode.system
BecomeResponseManager.getInstance().startAuthentication(activity, config)
```

En Java:

```java
BDIVConfig config = new BDIVConfig(
    jwt, contractId,
    new DocumetType[] { DocumetType.DNI, DocumetType.PASSPORT },
    true, userId
);
config.setVerificationResultsJwt(resultsJwt);
BecomeResponseManager.getInstance().startAuthentication(activity, config);
```

El backend de tu integración debe obtener y renovar el token de acuerdo con la [guía pública de autenticación de BecomeDigital](https://becomedigital.gitbook.io/kyc_api_es/authentication). Usa un token autorizado para tu contrato y flujo, y del mismo ambiente que la SDK. Mantén las credenciales de la nueva integración en tu backend.

La SDK utiliza el JWT que recibe. Un token vacío se informa mediante el callback de error; un token rechazado o vencido requiere que tu integración obtenga uno vigente antes de iniciar otro intento. La firma JWT no cambia automáticamente a la firma con credenciales ni renueva el token por su cuenta.

Crea una nueva configuración con el token vigente antes de iniciar otro intento. Si utilizas `setJwt(jwt)` antes de iniciar, seleccionas explícitamente JWT y se descartan las credenciales anteriores; asignar credenciales no sustituye el constructor JWT.

## Firma anterior, deprecada y compatible

```kotlin
// Firma deprecada; se conserva para las integraciones existentes.
val config = BDIVConfig(
    clientId, clientSecret, contractId,
    arrayOf(DocumetType.DNI, DocumetType.PASSPORT),
    true, userId
)
```

En Java:

```java
// Firma deprecada; se conserva para las integraciones existentes.
BDIVConfig config = new BDIVConfig(
    clientId, clientSecret, contractId,
    new DocumetType[] { DocumetType.DNI, DocumetType.PASSPORT },
    true, userId
);
```

Las aplicaciones que ya utilizan esta firma pueden conservar sus llamadas durante la migración. Reciben la advertencia de deprecación, pero mantienen su funcionamiento. Las propiedades de credenciales permanecen disponibles para esas integraciones.

Java y Kotlin muestran una advertencia de deprecación en los ocho constructores anteriores y en los métodos de acceso a credenciales. Las advertencias y la documentación de cada constructor indican el reemplazo con JWT. Los argumentos opcionales, su orden y sus valores predeterminados se conservan. En Kotlin, utiliza argumentos posicionales para estos constructores Java.

## Migrar una integración

1. Integra el nuevo archivo de la SDK y conserva las dependencias de la [guía de instalación](INSTALACION.md).
2. Prepara la obtención del JWT desde tu backend.
3. Sustituye los argumentos `clienId` y `clientSecret` por `jwt` en la creación de `BDIVConfig`.
4. Conserva `contractId`, documentos, usuario y los argumentos opcionales que ya utilizas: flujo, consulta del resultado, diagnóstico, protección de pantalla, datos del documento y modo de apariencia.
5. Mantén el manejo actual de resultados, errores y cancelación. Prueba la nueva firma en un dispositivo físico antes de publicar tu app.

La opción `flow` sigue seleccionando onboarding o autenticación de un usuario existente. Es independiente de cómo obtienes el JWT. Esta actualización conserva los resultados públicos y las opciones de presentación.

No muestres ni registres el JWT. Los ejemplos del [README](README.md) y el demo de esta distribución utilizan la firma recomendada.
