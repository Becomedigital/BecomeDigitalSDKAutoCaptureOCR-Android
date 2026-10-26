# Textos de componentes nativos — Android

## Textos del recorrido

Los títulos, instrucciones, consentimiento, selección de país y documento, introducciones, revisión y resultados de Become se administran desde la configuración del contrato. No copies archivos de traducción para configurar ese recorrido. Las claves faltantes conservan los valores predeterminados.

Esta guía y sus ejemplos incluyen únicamente claves de componentes nativos y etiquetas técnicas locales. Retira de los archivos de la app las antiguas claves del flujo si esas frases deben seguir las actualizaciones del contrato. Un override local compatible puede fijar una frase y ocultar su actualización; no copies la plantilla completa.

## Etiquetas técnicas de la SDK

Estas etiquetas todavía consultan recursos locales y no se cambian mediante los textos del contrato. Los ejemplos conservan únicamente las claves verificadas: carga inicial `text_loader_init`; errores de red `timeout_error`, `no_internet_error` y `connection_lost_error`; progreso `text_info_upload`, `text_progress_result`, `text_progress_delay_result` y `text_progress_delay_finish_result` (la SDK agrega el porcentaje al mensaje de envío).

No son un catálogo de títulos o acciones del recorrido.

## Componentes admitidos

| Componente | Versión verificada | Claves locales |
| --- | --- | --- |
| BlinkID UX | 8002.0.0 | `mb_blinkid_*` |
| Amplify Face Liveness | 1.10.0 | `amplify_ui_liveness_*` |

Las claves `mbic_*` pertenecen a Capture Legacy y no personalizan BlinkID UX. Los nombres difieren entre iOS y Android; conserva los de esta distribución.

## Personalización opcional

Agrega solo las entradas que necesitas a `app/src/main/res/values/become_strings.xml`:

```xml
<resources>
    <string name="mb_blinkid_camera_flip_document">Voltea el documento</string>
    <string name="amplify_ui_liveness_challenge_connecting">Conectando…</string>
</resources>
```

Android combina los recursos de la app con los de los componentes: un recurso definido por la app sustituye el mismo nombre. Algunas instrucciones equivalentes de Amplify pueden recibir texto asignado al contrato; el recurso nativo se usa cuando esa instrucción no tiene un texto configurado. Los textos que controla el contrato se actualizan desde esa configuración, no copiando claves del flujo a `strings.xml`.

Usa variantes como `values-es` y `values-en`; evita duplicados con `values-b+en`. Consulta los ejemplos opcionales en [español](../examples/custom-texts/res/values/strings.xml) e [inglés](../examples/custom-texts/res/values-b+en/strings.xml).

## Verificación

Conserva los nombres, mayúsculas, marcadores de formato y el significado de las instrucciones de seguridad y fotosensibilidad. Modifica únicamente los valores necesarios. Recompila la app después de cambiar recursos; no necesitas recompilar la SDK. Verifica los idiomas personalizados en un dispositivo físico. `BDIVConfig` no selecciona una tabla de traducciones ni configura los textos del recorrido.
