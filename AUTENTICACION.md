# Inicialización con JWT y migración de la firma anterior — Android

La versión **1.3.0** agrega la inicialización con `jwt` como opción recomendada. La firma con `clienId` y `clientSecret` se conserva y sigue funcionando para las integraciones existentes, pero está marcada como **deprecada**. Esta actualización no anuncia una fecha de retiro de esa firma.

## Firma recomendada

Obtén un JWT vigente desde el backend de tu aplicación antes de iniciar la SDK. Pasa únicamente el valor del token, **sin el prefijo `Bearer `**, y el identificador del contrato.

```kotlin
val config = BDIVConfig(
    jwt, contractId,
    arrayOf(DocumetType.DNI, DocumetType.PASSPORT),
    true, userId
)
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

La opción `flow` sigue seleccionando onboarding o autenticación de un usuario existente. Es independiente de cómo obtienes el JWT. Esta actualización conserva BlinkID para la captura documental, los resultados públicos y las opciones de presentación.

No muestres ni registres el JWT. Los ejemplos del [README](README.md) y el demo de esta distribución utilizan la firma recomendada.
