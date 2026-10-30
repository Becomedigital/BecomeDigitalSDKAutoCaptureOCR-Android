# Cambios de la SDK

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
