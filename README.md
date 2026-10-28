# SDK de BecomeDigital AutoCapture OCR para Android

Verificación de identidad nativa con captura automática de documentos, reconocimiento mediante OCR, alternativa de captura manual y validación facial. Este repositorio distribuye `becomedigitalsdk.aar` y la documentación de integración para las aplicaciones cliente.

## Funcionalidades

- Onboarding y autenticación con respuestas tipadas.
- Captura automática con guía de encuadre, indicaciones de calidad de imagen e instrucciones para frente y reverso.
- Captura manual disponible después de 30 segundos de forma predeterminada si la captura automática no termina, con reconocimiento del documento y opción de volver a intentar.
- Pantallas Compose, selección de país con búsqueda, identidad visual y textos asignados al contrato.
- Modo claro, oscuro o del sistema elegido por la app, y protección opcional contra capturas de pantalla.

Consulta los [cambios de esta actualización](CHANGELOG.md) y la [guía de diagnóstico](docs/LOGGING.md).

## Requisitos y dependencias

| Componente | Versión |
| --- | --- |
| `minSdk` | 24 |
| `compileSdk` | 36 |
| `targetSdk` recomendado | 36 |
| Compatibilidad de código fuente y destino de Java | 11 |
| Android Gradle Plugin recomendado | 9.0.1 |
| Kotlin recomendado | 2.2.10 |
| BlinkID Core / UX | 8002.0.0 |
| Compose BOM | 2026.06.00 |
| Amplify UI Face Liveness | 1.10.0 |
| Amplify AWS Cognito | 2.33.0 |
| CameraX | 1.5.3 |

Usa un JDK compatible con la versión de Android Gradle Plugin seleccionada. La configuración Java 11 de la tabla controla la compatibilidad de compilación. Habilita Compose para la interfaz de la SDK y de Face Liveness. Con Kotlin 2.x, aplica el plugin del compilador de Compose con la misma versión de Kotlin.

Este repositorio distribuye un AAR local; no es una publicación Maven. El paquete Java sigue siendo `com.becomedigital.sdk.identity.becomedigitalsdk`.

## Instalación

Copia `becomedigitalsdk.aar` en `app/libs` de la app integradora. Configura los repositorios de dependencias con `google()` y `mavenCentral()`.

Ejemplo de configuración en el archivo de compilación raíz:

```groovy
buildscript {
    ext.kotlin_version = "2.2.10"
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
    dependencies {
        classpath "com.android.tools.build:gradle:9.0.1"
        classpath "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version"
        classpath "org.jetbrains.kotlin:compose-compiler-gradle-plugin:$kotlin_version"
    }
}
```

Ejemplo de configuración en el módulo de la aplicación:

```groovy
plugins {
    id "com.android.application"
    id "org.jetbrains.kotlin.android"
    id "org.jetbrains.kotlin.plugin.compose"
}

android {
    compileSdk 36
    defaultConfig {
        minSdk 24
        targetSdk 36
    }
    compileOptions {
        coreLibraryDesugaringEnabled true
        sourceCompatibility JavaVersion.VERSION_11
        targetCompatibility JavaVersion.VERSION_11
    }
    kotlinOptions { jvmTarget = "11" }
    buildFeatures { compose true }
}

dependencies {
    implementation files("libs/becomedigitalsdk.aar")
    implementation platform("androidx.compose:compose-bom:2026.06.00")
    coreLibraryDesugaring "com.android.tools:desugar_jdk_libs:2.1.5"

    implementation "androidx.appcompat:appcompat:1.6.1"
    implementation "androidx.constraintlayout:constraintlayout:2.1.4"
    implementation "androidx.core:core-ktx:1.10.1"
    implementation "androidx.activity:activity-ktx:1.8.2"
    implementation "androidx.fragment:fragment-ktx:1.6.2"
    implementation "androidx.navigation:navigation-fragment:2.5.3"
    implementation "androidx.navigation:navigation-ui:2.5.3"

    implementation "androidx.camera:camera-core:1.5.3"
    implementation "androidx.camera:camera-camera2:1.5.3"
    implementation "androidx.camera:camera-view:1.5.3"
    implementation "androidx.camera:camera-lifecycle:1.5.3"
    implementation "com.google.guava:guava:33.3.1-android"

    implementation "com.google.code.gson:gson:2.10.1"
    implementation "com.squareup.okhttp3:okhttp:4.9.3"
    implementation "com.android.volley:volley:1.2.1"
    implementation "com.github.bumptech.glide:glide:4.10.0"

    implementation "com.amplifyframework.ui:liveness:1.10.0"
    implementation "com.amplifyframework:aws-auth-cognito:2.33.0"
    implementation "androidx.compose.material3:material3"
    implementation "androidx.activity:activity-compose:1.12.4"

    implementation "com.microblink:blinkid-core:8002.0.0"
    implementation "com.microblink:blinkid-ux:8002.0.0"

    implementation "androidx.room:room-runtime:2.6.1"
    implementation "androidx.lifecycle:lifecycle-livedata:2.8.7"
    implementation "androidx.lifecycle:lifecycle-viewmodel:2.8.7"
}
```

Un AAR local no incluye los metadatos de dependencias de Gradle. Declara sus dependencias externas de forma explícita y alinea Compose UI, runtime y Material 3 con la BOM indicada. Si la app ya usa alguna de estas librerías, resuelve su versión de forma consistente. Si vienes de la integración anterior de Capture, elimina las dependencias antiguas `capture-core` y `capture-ux`.

### Licencia de la aplicación y permisos

Solicita a BecomeDigital una licencia BlinkID Core para el `applicationId` de tu app. Agrega el archivo `app/src/main/assets/com.become.blinkid.key`. Este repositorio no incluye el contenido de las licencias ni las credenciales de las aplicaciones. Un identificador diferente, incluido el de una variante de desarrollo, requiere su propia licencia.

El AAR declara los permisos de Internet y cámara. La app debe gestionar el permiso de cámara en tiempo de ejecución antes de iniciar un flujo que lo requiera.

```xml
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.CAMERA" />
```

## Iniciar la verificación

Registra el callback antes de iniciar y conserva el administrador de callbacks durante el flujo. Los siguientes miembros se definen dentro de la actividad de la app:

```kotlin
import com.becomedigital.sdk.identity.becomedigitalsdk.core.callback.BecomeCallBackManager
import com.becomedigital.sdk.identity.becomedigitalsdk.core.callback.BecomeInterfaseCallback
import com.becomedigital.sdk.identity.becomedigitalsdk.core.callback.BecomeResponseManager
import com.becomedigital.sdk.identity.becomedigitalsdk.models.BDIVConfig
import com.becomedigital.sdk.identity.becomedigitalsdk.models.BDIdentityVerificationResponse
import com.becomedigital.sdk.identity.becomedigitalsdk.models.DocumetType

private val callbackManager = BecomeCallBackManager.createNew()

private fun startOnboarding() {
    BecomeResponseManager.getInstance().registerCallback(
        callbackManager,
        object : BecomeInterfaseCallback {
            override fun onFinish(response: BDIdentityVerificationResponse) {
                when (response.responseStatus) {
                    BDIdentityVerificationResponse.StatusType.SUCCES -> {
                        response.onboarding?.let { /* Inicio del proceso confirmado. */ }
                        response.verification?.let { /* Resultado final disponible. */ }
                        response.authentication?.let { /* Evalúa it.result. */ }
                    }
                    BDIdentityVerificationResponse.StatusType.ERROR -> {
                        // Restaura la interfaz de la app y permite que el usuario inicie un nuevo intento.
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
```

Los nombres `clienId`, `DocumetType`, `SUCCES` y `startAuthentication` se conservan por compatibilidad. `startAuthentication` inicia el flujo seleccionado en `BDIVConfig`. Para autenticación usa `BDIVConfig.Flow.Authentication`; puedes usar `emptyArray()` cuando no se requiera capturar documentos.

## Configuración pública

| Parámetro | Valor predeterminado | Uso |
| --- | --- | --- |
| `clienId`, `clientSecret`, `contractId`, `userId` | Requeridos | Identifican al cliente, el contrato y el usuario. |
| `documentTypes` | Requerido en onboarding | `DocumetType[]`: `DNI`, `PASSPORT`, `LICENSE`; en autenticación puede usarse un arreglo vacío. |
| `allowLibraryLoading` | Argumento del constructor | Permite la opción de selección desde la galería disponible en el flujo. |
| `flow` | `Flow.Onboarding` | `Onboarding` o `Authentication`. |
| `performVerificationCheck` | `true` | Espera el resultado final de onboarding. |
| `pollingMaxAttempts` | `0` | Máximo de consultas del resultado; `0` no limita los intentos. |
| `pollingTimeoutSeconds` | 2 | Tiempo máximo en segundos de cada consulta del resultado. |
| `debugLogsEnabled` | `false` | Habilita registros de diagnóstico durante una revisión. |
| `preventScreenCapture` | `false` | Habilita la protección de las pantallas de la SDK contra capturas. |
| `country`, `state` | `null` | Código de país de dos letras y estado de EE. UU. precargados, cuando corresponda. |
| `nationalIdType`, `nationalIdTypeChoices`, `documentNumber` | Vacíos | Datos opcionales del documento precargados. |
| `themeMode` | `ThemeMode.system` | `light`, `dark` o `system`. |

La SDK aplica los pasos, apariencia y textos asignados al contrato. `BDIVConfig` no configura el diseño ni los textos del recorrido. Los archivos locales son opcionales para instrucciones de los componentes nativos y etiquetas técnicas de carga o red; consulta la [guía de textos nativos](docs/PERSONALIZACION_TEXTOS.md).

La animación de carga inicial permanece visible hasta que el flujo y su presentación estén listos. Al inicio usa el color primario del tema de la app o, si no está disponible, su color de énfasis, y un mensaje de carga localizado. El primer paso visible ya muestra la apariencia configurada. Los errores de inicialización se entregan mediante el callback existente.

Los 30 segundos predeterminados para habilitar la alternativa de captura manual son independientes del tiempo máximo de las consultas del resultado. Para configurar el tiempo general de conexión, lectura y escritura, consulta el [recurso de ejemplo](examples/network-config/res/values/become_config.xml).

## Respuestas tipadas

`onFinish` entrega `BDIdentityVerificationResponse`, con `responseStatus`, `message` y objetos de resultado opcionales:

| Objeto | Campos públicos | Uso |
| --- | --- | --- |
| `onboarding` | `code`, `message`, `urlResource`, `userId` | Confirma el inicio cuando `performVerificationCheck=false`; no representa una aprobación final. |
| `verification` | `urlGetData` | Resultado final de onboarding cuando está habilitada la consulta del resultado. |
| `authentication` | `company`, `confidence`, `executionId`, `liveness`, `result`, `userId` | Datos de autenticación; evalúa `result` por separado. |

Los objetos de resultado pueden ser `null`. Los estados incluyen `SUCCES`, `ERROR`, `PENDING`, `NOFOUND` y `CANCEL`. La cancelación habitual usa `onCancel()`. Un flujo que solo solicita documentos no requiere un resultado facial. Usa los objetos concretos de resultado en lugar de acceder a un diccionario de respuesta; consulta el [manejo de errores](docs/ERRORES.md).

## Guías de integración

- [Android integration demo with Jetpack Compose](Demo/AndroidBlinkIDIntegration/README.md)
- [Manejo de errores](docs/ERRORES.md)
- [Idioma y textos locales opcionales](docs/PERSONALIZACION_TEXTOS.md)
- [Registros de diagnóstico](docs/LOGGING.md)
- [Protección contra capturas de pantalla](SCREEN_CAPTURE_CONFIGURATION.md)
- [Sumas de verificación de los artefactos](CHECKSUMS.sha256)

Antes de publicar la app, prueba en un dispositivo físico la captura automática y manual, los reintentos, los permisos, la validación facial, los callbacks y la cancelación. Usa el identificador de aplicación que corresponda a la licencia. Revisa las librerías nativas de la app final y su compatibilidad con Android 15 o posterior mediante las herramientas exigidas por tu canal de distribución.
