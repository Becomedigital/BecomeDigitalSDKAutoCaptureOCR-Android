# Cambios de la SDK

## 3.5.1-rc.3 — 2026-11-05

### Cambios

- Actualiza las ilustraciones de introducción de documento y rostro, conservando su transparencia y adaptación a los modos claro y oscuro.
- La revisión del documento muestra un lado a la vez y permite navegar entre las imágenes, conservando sus proporciones en pantallas de distintos tamaños.

### Correcciones

- Evita que las imágenes del documento desborden el espacio disponible durante la revisión.
- Solicita repetir la captura automática cuando falta la imagen necesaria para revisar el documento.

## 3.5.1-rc.2 — 2026-11-04

### Cambios

- Esta versión candidata conserva la API de integración, la configuración pública y los resultados de verificación de 3.5.1-rc.1.

## 3.5.1-rc.1 — 2026-11-04

### Correcciones

- El identificador de versión integrado corresponde a la entrega 3.5.1 para facilitar el diagnóstico de integración.

## JWT por operación — 29 de octubre de 2026

- Permite utilizar JWT diferentes para crear la verificación y consultar su resultado mediante `verificationResultsJwt`.
- Conserva las firmas anteriores y los resultados de las integraciones existentes.
- Actualiza el ejemplo y el diagnóstico de rechazos HTTP 403 en la consulta.

Consulta los [ejemplos de configuración y migración](AUTENTICACION.md).

## 1.3.0 — 29 de octubre de 2026

- Agrega la firma de inicialización con JWT obtenido por la integración.
- Conserva la firma anterior con credenciales, marcada como deprecada con indicaciones de migración.
- Mantiene los resultados, callbacks y parámetros opcionales de las integraciones existentes.
- Actualiza el demo para utilizar JWT como ejemplo recomendado.
- Conserva la captura automática de documentos con reconocimiento OCR y la alternativa manual.

Consulta los [ejemplos de ambas firmas y la guía de migración](AUTENTICACION.md).

## Validación facial — 27 de octubre de 2026

- Mejora la compatibilidad del envío de selfies para su validación.
- Respeta el límite de intentos automáticos de validación de la selfie.
- Amplía el diagnóstico opcional para identificar los motivos de repetición de la captura facial.
- Actualiza los textos predeterminados y la aplicación de las personalizaciones del contrato.

Después de reemplazar el archivo de la SDK, compila tu app con las dependencias de la [guía de instalación](INSTALACION.md) y comprueba la verificación completa en un dispositivo físico. La API de inicialización de esta entrega se describe en el [README](README.md).
