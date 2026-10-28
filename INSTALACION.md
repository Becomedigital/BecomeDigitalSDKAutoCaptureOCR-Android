# Instalación de la SDK para Android

## Requisitos

| Requisito | Valor de referencia |
| --- | --- |
| Android mínimo | 7.0, API 24 |
| `compileSdk` / `targetSdk` | 36 / 36 |
| Android Gradle Plugin | 9.0.1 |
| Gradle | 9.2.1 |
| JDK para esta configuración | 21 |
| Compatibilidad de Java y Kotlin | 11 |
| Kotlin y plugin de Compose | 2.2.10 |
| Compose BOM | 2026.06.00 |
| Captura documental | BlinkID Core / UX 8002.0.0 |
| Amplify Face Liveness / Cognito | 1.10.0 / 2.33.0 |
| CameraX | 1.5.3 |

Selecciona JDK 21 para Gradle en Android Studio al utilizar esta configuración. La compatibilidad Java 11 indicada en el ejemplo se refiere al código de la aplicación. Mantén el plugin de Compose en la misma versión que Kotlin y utiliza la BOM indicada para las librerías de Compose.

## Agregar los archivos

Copia los siguientes archivos en `app/libs`:

- `becomedigitalsdk.aar`, incluido en la raíz del repositorio.

Al integrar un AAR local, declara también las dependencias que se muestran a continuación. Inclúyelas aunque tu contrato no muestre todos los pasos. Si reemplazas otra distribución, retira sus archivos y dependencias de captura anteriores para evitar duplicados.

## Configurar Gradle

### Archivo raíz `build.gradle`

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

Configura `google()` y `mavenCentral()` como repositorios de dependencias de tu aplicación.

### Archivo `gradle.properties`

Utiliza estas opciones con la configuración de Android Gradle Plugin y Kotlin mostrada en esta guía:

```properties
android.useAndroidX=true
android.enableJetifier=true
android.builtInKotlin=false
android.newDsl=false
```

### Módulo de la aplicación

En `app/build.gradle`, conserva las dependencias y sus versiones. Usa el `applicationId` real de tu app, que debe coincidir con su licencia:

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

Compose es necesario para esta integración. Si la app ya usa alguna dependencia de la lista, alinea su versión para evitar conflictos.

## Agregar la licencia

Solicita a BecomeDigital la licencia emitida para el `applicationId` de tu app y agrégala como **`app/src/main/assets/com.become.blinkid.key`**. Una variante con otro identificador requiere su propia licencia. No compartas el contenido de la licencia en registros ni documentación.

## Permisos

La integración requiere los permisos de Internet y cámara:

```xml
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.CAMERA" />
```

La SDK incluye estas declaraciones. Solicita el permiso de cámara en tiempo de ejecución antes de iniciar un flujo que lo requiera. Conserva las declaraciones que la SDK incorpora al manifiesto de tu app. Si tu contrato utiliza ubicación, revisa también los permisos correspondientes.

## Continuar con la integración

Consulta el [ejemplo de inicialización](README.md#iniciar-una-verificación), las [claves de localización](docs/PERSONALIZACION_TEXTOS.md) y la [configuración de R8](docs/R8.md). Prueba la app final en un dispositivo físico, incluida su versión de publicación.
