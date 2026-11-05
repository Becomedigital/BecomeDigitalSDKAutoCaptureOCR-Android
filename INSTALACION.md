# Instalación para Android con Maven Central

Agrega BecomeDigital como una dependencia de tu aplicación. Gradle consulta el POM publicado en Maven Central y descarga automáticamente las bibliotecas necesarias para la integración; no debes copiarlas ni declararlas una por una.

## Requisitos

| Requisito | Valor de referencia |
| --- | --- |
| Versión publicada usada en esta guía | `3.5.1-rc.2` |
| Coordenada Maven | `ai.becomedigital:identity-verification:3.5.1-rc.2` |
| Android mínimo | 7.0, API 24 |
| Arquitecturas admitidas | ARMv7 (`armeabi-v7a`) y ARM64 (`arm64-v8a`) |
| `compileSdk` / `targetSdk` de referencia | 36 / 36 |
| Android Gradle Plugin | 9.0.1 |
| Gradle | 9.2.1 |
| JDK para esta configuración | 21 |
| Compatibilidad de Java y Kotlin | 11 |
| Kotlin | 2.2.10 |

`3.5.1-rc.2` es una versión candidata para evaluación. La versión interna de la biblioteca es `3.5.1`. Selecciona JDK 21 para Gradle en Android Studio al usar esta configuración; la compatibilidad Java 11 se refiere al código de la aplicación.

## 1. Configurar los repositorios

En `settings.gradle`, incorpora estos repositorios al bloque de dependencias existente:

```groovy
dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
}
```

La misma configuración en `settings.gradle.kts`:

```kotlin
dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
}
```

No necesitas configurar un repositorio privado ni credenciales de descarga para este paquete.

## 2. Agregar BecomeDigital al módulo de la app

En `app/build.gradle` con Groovy:

```groovy
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
}

dependencies {
    implementation "ai.becomedigital:identity-verification:3.5.1-rc.2"
    coreLibraryDesugaring "com.android.tools:desugar_jdk_libs:2.1.5"
}
```

El equivalente en `app/build.gradle.kts` con Kotlin DSL:

```kotlin
android {
    compileSdk = 36
    defaultConfig {
        minSdk = 24
        targetSdk = 36
    }
    compileOptions {
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions { jvmTarget = "11" }
}

dependencies {
    implementation("ai.becomedigital:identity-verification:3.5.1-rc.2")
    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:2.1.5")
}
```

Incorpora estos bloques a la configuración existente de tu módulo. Conserva su `namespace`, su `applicationId` y los plugins que utiliza tu aplicación. El `applicationId` debe coincidir con el autorizado para tu licencia.

La única dependencia de implementación que debes agregar para BecomeDigital es `ai.becomedigital:identity-verification`. El POM declara sus dependencias transitivas. `coreLibraryDesugaring` es una configuración de compilación de la app necesaria para utilizar las API de Java requeridas en las versiones de Android admitidas; configúrala además de la dependencia de BecomeDigital.

Usa la versión exacta indicada, sin rangos ni versiones dinámicas. Conserva las dependencias que tu aplicación utiliza directamente. Antes de alinearlas o actualizarlas, compara las versiones declaradas, las resueltas y el alcance de compatibilidad en la [guía de dependencias compartidas](DEPENDENCIAS.md).

Con Android Gradle Plugin 9.0.1 y el plugin Kotlin de esta configuración, conserva estas opciones de `gradle.properties`:

```properties
android.useAndroidX=true
android.enableJetifier=true
android.builtInKotlin=false
android.newDsl=false
```

Si vienes de la integración por archivo, sigue la [migración a Maven Central](MIGRACION.md) para retirar las referencias locales anteriores.

## 3. Agregar la licencia de la app

Solicita a BecomeDigital la licencia emitida para el `applicationId` de tu app y agrégala como **`app/src/main/assets/com.become.blinkid.key`**. Una variante con otro identificador requiere su propia licencia. No compartas el contenido de la licencia en registros ni documentación.

Los permisos de uso de esta versión se describen en las [condiciones de uso](README.md#condiciones-de-uso). El acceso y consumo de los servicios requieren el contrato y las credenciales correspondientes.

## 4. Configurar permisos

La integración requiere los permisos de Internet y cámara:

```xml
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.CAMERA" />
```

La SDK incluye estas declaraciones. Solicita el permiso de cámara en tiempo de ejecución antes de iniciar un flujo que lo requiera. Conserva las declaraciones que la SDK incorpora al manifiesto de tu app. Si tu contrato utiliza ubicación, revisa también los permisos correspondientes.

## 5. Comprobar la integración

1. Sincroniza el proyecto en Android Studio y confirma que Gradle resuelva `ai.becomedigital:identity-verification:3.5.1-rc.2`.
2. Sigue el [ejemplo de inicialización](README.md#iniciar-una-verificación) con los JWT y el contrato de tu integración.
3. Compila las variantes de desarrollo y publicación de tu app. Si utilizas optimización, sigue la [configuración de R8](docs/R8.md).
4. En un dispositivo físico compatible, revisa permisos, captura documental, validación facial, cancelación y resultados. Usa también la variante de publicación de tu app.

Consulta la [localización](docs/PERSONALIZACION_TEXTOS.md), el [manejo de errores](docs/ERRORES.md) y el [demo de integración](Demo/AndroidBlinkIDIntegration/README.md) para continuar.

## Arquitecturas y emuladores

Esta distribución admite ARMv7 y ARM64. No ofrece soporte de captura documental para `x86_64`. Para revisar la integración en un emulador, utiliza una arquitectura ARM compatible; las pruebas funcionales de cámara, documento y rostro se realizan en un dispositivo físico con una licencia válida para la app.

## Resolver problemas de instalación

| Situación | Qué revisar |
| --- | --- |
| Gradle no encuentra el paquete | Comprueba `mavenCentral()`, `google()`, la coordenada completa, la versión exacta publicada y la conexión. |
| Clases duplicadas | Retira el AAR local y sus referencias anteriores siguiendo la [guía de migración](MIGRACION.md). |
| Se requiere desugaring | Habilita `coreLibraryDesugaringEnabled` y agrega la configuración indicada para el módulo de la app. |
| La verificación no acepta la licencia | Comprueba el nombre del archivo y el `applicationId` autorizado. |
| Falla la variante con optimización | Conserva las reglas de compatibilidad incluidas y revisa la [guía de R8](docs/R8.md). |
