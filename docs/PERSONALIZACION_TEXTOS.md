# Idioma y textos locales opcionales — Android

## Integración habitual

Los textos de las pantallas Compose de Become se aplican automáticamente según el contrato. **No necesitas copiar los ejemplos de `strings.xml` para integrar la SDK o recibir cambios de textos del contrato.**

Esta guía se conserva para overrides puntuales y textos que todavía usan recursos nativos. Los ejemplos en [español](../examples/custom-texts/res/values/strings.xml) e [inglés](../examples/custom-texts/res/values-b+en/strings.xml) son referencias de compatibilidad. Incluyen claves históricas que no representan todas las frases de las pantallas actuales.

## Alcance de los overrides

| Texto | Comportamiento actual |
| --- | --- |
| Pantallas Compose de Become | Usan el texto del contrato para el locale actual, su idioma base, español y finalmente el valor predeterminado de la SDK. |
| Introducción | Consulta primero las claves nativas compatibles `intro_*`, como `intro_title`, `intro_body` e `intro_cta`. |
| Tratamiento de datos personales | Consulta primero las claves compatibles `atdp_*`, como `atdp_title`, `atdp_markdown`, `atdp_checkbox_label` y `atdp_accept`. |
| Carga y errores que leen recursos Android | Admiten overrides del recurso que consume esa etiqueta, como `text_loader_init`, `identity_error_documentfront` o `timeout_error`. |
| Instrucciones internas de captura y detector facial | Usan los recursos compatibles del componente nativo. |

En introducción, los puntos de la clave se convierten en guiones bajos: `header.reminder` corresponde a `intro_header_reminder`. En ATDP, las claves camel case se convierten a snake case: `checkboxLabel` corresponde a `atdp_checkbox_label`.

El resto de las etiquetas Compose no tiene un mapeo general desde todos los namespaces del contrato a `strings.xml`. Las claves antiguas de introducción o documento, como `text_tittle_general_intro` o `text_dni_selec_document`, no sustituyen por sí solas esas etiquetas actuales. `BDIVConfig` no expone un selector de idioma ni una tabla de traducciones; el locale del contrato se deriva del entorno Android.

## Ejemplo opcional

Agrega **solo las entradas que quieras fijar desde la app** a `app/src/main/res/values/become_strings.xml`:

```xml
<resources>
    <string name="intro_cta">Empezar verificación</string>
    <string name="atdp_accept">Acepto y continúo</string>
    <string name="text_loader_init">Cargando…</string>
    <string name="identity_error_documentfront">Vuelve a capturar el frente completo y enfocado.</string>
</resources>
```

Un override tiene prioridad en la ruta que consulta ese recurso y puede ocultar cambios posteriores del contrato. Si copiaste la plantilla completa, retira las entradas que no quieras mantener como overrides. Usa los textos del contrato para personalizar de forma habitual el recorrido.

## Componentes nativos

BlinkID UX incluye sus propias instrucciones localizadas y admite las claves compatibles de su versión. Las entradas `mbic_*` de las plantillas corresponden a Capture legacy y no personalizan BlinkID UX.

Amplify Face Liveness usa las claves nativas compatibles `amplify_ui_liveness_*`. La introducción facial de Become y las instrucciones internas del detector se personalizan de forma independiente; el texto del contrato no se mapea automáticamente a todas las frases internas del componente.

## Instalación y verificación de overrides

1. Agrega únicamente las claves elegidas, conserva sus nombres exactos y un solo elemento `<resources>` por archivo.
2. Usa variantes como `values-en` para los idiomas que personalices. `values-b+en` también es válido; evita duplicar la misma clave en dos ubicaciones equivalentes del mismo idioma.
3. Conserva marcadores de formato y saltos de línea. Escapa `&` como `&amp;` y los apóstrofos según las reglas de Android.
4. Recompila la app; no necesitas regenerar el AAR.
5. Valida en dispositivo físico los recursos modificados y confirma que las frases sin override siguen el contrato o sus valores predeterminados.

Los nombres de recursos no son intercambiables entre plataformas: Android usa `identity_error_documentfront`; iOS usa `identity_error_documentFront`.
