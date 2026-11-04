# Migrar la integración de Android a Maven Central

Estos pasos permiten pasar de la integración de BecomeDigital mediante un AAR local a la dependencia publicada. Conserva la configuración de tu contrato, los JWT, la licencia de la app y las personalizaciones que utilizas.

## Retirar la integración anterior

1. Retira la declaración `implementation files("libs/becomedigitalsdk.aar")` o la referencia equivalente que utiliza tu aplicación.
2. Retira la copia local de `becomedigitalsdk.aar` para que tampoco se incluya mediante una regla `fileTree`.
3. Si configuraste `flatDir` exclusivamente para este archivo, retíralo. Conserva los repositorios que necesiten otras dependencias de tu app.
4. Retira las declaraciones manuales que agregaste exclusivamente como dependencias de BecomeDigital: ahora provienen del POM. Conserva las que tu aplicación utiliza directamente y compáralas con la [referencia de dependencias compartidas](DEPENDENCIAS.md).
5. Conserva `app/src/main/assets/com.become.blinkid.key`, los permisos, los recursos de localización y la configuración de desugaring de tu aplicación.

## Agregar la dependencia publicada

Sigue la [instalación con Maven Central](INSTALACION.md): configura `google()` y `mavenCentral()`, agrega `ai.becomedigital:identity-verification:3.5.1-rc.2` y conserva el requisito de desugaring de la app. Gradle obtiene automáticamente las dependencias transitivas.

Continúa con la [inicialización y configuración pública](README.md#iniciar-una-verificación). Si además vienes de una versión con firma de credenciales, consulta por separado la [migración a JWT](AUTENTICACION.md).

## Verificar el resultado

Sincroniza el proyecto y comprueba la coordenada exacta resuelta. Compila las variantes de desarrollo y publicación, incluida la [configuración con R8](docs/R8.md) cuando corresponda. En un dispositivo físico ARM compatible, prueba la licencia, los permisos, la captura, la validación facial, la cancelación y los resultados antes de distribuir tu app.
