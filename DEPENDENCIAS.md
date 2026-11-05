# Dependencias compartidas y compatibilidad en Android

Esta guía corresponde a `ai.becomedigital:identity-verification:3.5.1-rc.2`. Gradle descarga automáticamente sus dependencias desde el POM. Usa este inventario para comparar las bibliotecas generales que tu aplicación ya utiliza; no es una lista para agregarlas de nuevo al módulo.

## Versión declarada y versión que utiliza la app

Una misma biblioteca puede llegar al proyecto por BecomeDigital, por una dependencia directa de tu app o por otra biblioteca. Gradle selecciona una versión para cada módulo y configuración. Normalmente resuelve un conflicto eligiendo la versión más alta solicitada, pero restricciones, plataformas, bloqueos y reglas de resolución pueden cambiar ese resultado.

Por eso, escribir una versión en el POM no fija por sí solo toda la combinación de bibliotecas de la app. Revisa siempre la versión seleccionada para la variante que vas a distribuir.

## Bibliotecas generales declaradas en el POM

Los valores corresponden al [POM público de `3.5.1-rc.2`](https://repo.maven.apache.org/maven2/ai/becomedigital/identity-verification/3.5.1-rc.2/identity-verification-3.5.1-rc.2.pom).

| Coordenada | Versión solicitada por el POM |
| --- | --- |
| `androidx.appcompat:appcompat` | `1.6.1` |
| `androidx.constraintlayout:constraintlayout` | `2.1.4` |
| `androidx.core:core-ktx` | `1.15.0` |
| `androidx.activity:activity-ktx` | `1.12.4` |
| `androidx.activity:activity-compose` | `1.12.4` |
| `androidx.fragment:fragment-ktx` | `1.6.2` |
| `androidx.navigation:navigation-fragment` | `2.5.3` |
| `androidx.navigation:navigation-ui` | `2.5.3` |
| `androidx.compose.material3:material3` | `1.4.0` |
| `androidx.compose.ui:ui` | `1.11.3` |
| `androidx.compose.runtime:runtime` | `1.11.3` |
| `androidx.camera:camera-core` | `1.5.3` |
| `androidx.camera:camera-camera2` | `1.5.3` |
| `androidx.camera:camera-view` | `1.5.3` |
| `androidx.camera:camera-lifecycle` | `1.5.3` |
| `com.google.guava:guava` | `33.3.1-android` |
| `com.google.code.gson:gson` | `2.10.1` |
| `com.squareup.okhttp3:okhttp` | `4.9.3` |
| `com.android.volley:volley` | `1.2.1` |
| `com.github.bumptech.glide:glide` | `4.10.0` |
| `androidx.room:room-runtime` | `2.6.1` |
| `androidx.lifecycle:lifecycle-livedata` | `2.8.7` |
| `androidx.lifecycle:lifecycle-viewmodel` | `2.8.7` |

AppCompat está declarado como `1.6.1` en esta publicación. `1.7.1` puede ser el resultado de la resolución del proyecto; no es el valor declarado en su POM.

Las dependencias anteriores pueden incorporar otros módulos transitivos, como Kotlin Standard Library, Kotlin Coroutines, Okio y módulos adicionales de AndroidX. Su versión también depende del conjunto completo de dependencias de tu app. El informe de Gradle permite verlas aunque no figuren como dependencias directas de BecomeDigital.

## Compose BOM y desugaring

| Configuración | Valor de referencia | Qué significa para la app |
| --- | --- | --- |
| `androidx.compose:compose-bom` | `2026.06.00` | Está importada en `dependencyManagement` del POM. Es una referencia de versiones, no una biblioteca ejecutable ni una garantía de que la app resuelva exactamente ese conjunto. |
| `com.android.tools:desugar_jdk_libs` | `2.1.5` | Se configura en `coreLibraryDesugaring` del módulo consumidor, junto con su habilitación en `compileOptions`; no se agrega como una dependencia `implementation` del POM. |

El POM declara explícitamente las versiones de los módulos Compose de la tabla anterior. Si tu app ya usa una BOM de Compose, compara el conjunto que realmente resuelve Gradle. Una BOM coordina versiones: no agrega por sí sola todos los módulos ni certifica cualquier combinación con otras dependencias. Material 3 y Compose UI pueden usar numeraciones distintas.

Conserva la configuración de desugaring de la [guía de instalación](INSTALACION.md). El POM no puede habilitar por sí solo las opciones de compilación del módulo de tu app.

## Referencia observada en una app consumidora

Estos ejemplos provienen de los metadatos de las bibliotecas incluidas en el APK de referencia compilado consumiendo la versión publicada. Muestran que una resolución puede elevar las versiones solicitadas:

| Biblioteca o familia | Solicitada por el POM | Incluida en el APK de referencia |
| --- | --- | --- |
| CameraX: los cuatro módulos declarados | `1.5.3` | `1.6.1` |
| AppCompat | `1.6.1` | `1.7.1` |
| Core KTX | `1.15.0` | `1.18.0` |
| Activity KTX | `1.12.4` | `1.13.0` |
| Lifecycle ViewModel | `2.8.7` | `2.10.0` |

La integración de referencia compiló en Debug y Release con R8. Este resultado corresponde a ese conjunto de dependencias; no es una certificación de las combinaciones de otras aplicaciones ni sustituye las pruebas funcionales en un dispositivo físico.

## Mínimos y máximos de compatibilidad

| Dato | Alcance de esta entrega |
| --- | --- |
| Base de resolución solicitada | Las versiones declaradas en el POM, sujetas a la resolución completa de Gradle. |
| Mínimo funcional compatible por biblioteca | No establecido. Las versiones declaradas no equivalen a mínimos certificados. |
| Máximo funcional compatible por biblioteca | No establecido. El POM no publica límites superiores de compatibilidad para estas bibliotecas. |
| Versiones observadas | Las del conjunto de referencia; no definen un intervalo mínimo/máximo. |

Si tu app declara una versión anterior, Gradle puede elevarla por las dependencias transitivas. Si declara una posterior, esa versión puede terminar en el APK. En ambos casos compara el resultado final y valida las funciones de tu app que utilizan esa biblioteca. No deduzcas compatibilidad, incompatibilidad o deprecación únicamente a partir de que el número sea mayor o menor.

## Comparar las versiones en tu proyecto

Desde la raíz de tu aplicación, consulta el árbol de la variante de publicación:

```sh
./gradlew :app:dependencies --configuration releaseRuntimeClasspath
```

Para conocer el origen y el motivo de selección de una biblioteca compartida:

```sh
./gradlew :app:dependencyInsight --configuration releaseRuntimeClasspath --dependency androidx.camera:camera-core
./gradlew :app:dependencyInsight --configuration releaseRuntimeClasspath --dependency androidx.appcompat:appcompat
./gradlew :app:dependencyInsight --configuration releaseRuntimeClasspath --dependency androidx.compose.ui:ui
```

Sustituye `app` y `releaseRuntimeClasspath` si tu módulo o variante tienen otro nombre. También puedes consultar `debugRuntimeClasspath` para comparar desarrollo y publicación.

1. Identifica las coordenadas compartidas entre tu aplicación y esta tabla.
2. Compara la versión declarada, la seleccionada y el motivo de selección del informe. Revisa también catálogos, archivos de bloqueo, `force`, `strictly`, exclusiones o `enforcedPlatform` que tu proyecto ya tenga configurados.
3. Si decides alinear o actualizar una dependencia de tu app, revisa la documentación de esa biblioteca y vuelve a resolver el conjunto. Conserva los módulos relacionados de una familia en una combinación coherente; no fuerces una versión solo para ocultar un error de resolución.
4. Compila la variante de publicación, incluida la optimización con R8, y prueba en un dispositivo físico compatible: cámara, documento, validación facial, cancelación y resultados, además de las funciones propias de tu aplicación.

Si solicitas soporte, comparte la versión de BecomeDigital, los informes de las coordenadas en conflicto y el error de compilación o ejecución. Omite credenciales, datos de usuarios y el contenido de la licencia de la app.

Consulta la documentación de Gradle sobre [resolución y versiones](https://docs.gradle.org/current/userguide/dependency_versions.html), [inspección de dependencias](https://docs.gradle.org/current/userguide/viewing_debugging_dependencies.html) y [plataformas](https://docs.gradle.org/current/userguide/platforms.html), y las guías Android de [Compose BOM](https://developer.android.com/develop/ui/compose/bom) y [desugaring](https://developer.android.com/studio/write/java8-support).
