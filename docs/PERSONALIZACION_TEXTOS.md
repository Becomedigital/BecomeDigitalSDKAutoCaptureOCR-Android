# Localización y personalización de textos — Android

## Configuración

1. Copia las claves necesarias del ejemplo en [español](../examples/custom-texts/res/values/strings.xml) o [inglés](../examples/custom-texts/res/values-b+en/strings.xml).
2. Usa `res/values/become_strings.xml` de la app y variantes por idioma como `values-en/become_strings.xml`.
3. Conserva cada `name`, edita los valores y recompila la app.
4. Mantén un solo elemento `<resources>` por archivo XML y evita claves duplicadas para un mismo idioma.

Las claves nativas definidas por la app tienen prioridad para esas frases específicas. Las claves que falten conservan el texto asignado al contrato o el valor predeterminado de la SDK. Personaliza cada idioma admitido. Cambiar los recursos de texto no requiere recompilar el binario de la SDK.

## Claves por pantalla

Una clave compartida cambia todas las pantallas que la usan. Copia los nombres completos de la plantilla; `*` indica un prefijo, no una clave literal.

| Pantalla o uso | Claves |
| --- | --- |
| Introducción | `text_tittle_general_intro`, `text_sub_tittle_general_intro`, `text_start_btn`, `and_intro_general`, `text_video_intro_general`, `text_document_intr_general` |
| Introducción y análisis facial | `text_video_intro`, `text_tittle_selfie`, `btn_text_recording_video`, `text_loader_selfie`, `text_progress_inteligence` |
| Selección de país y documento | `text_tittle_selec_document`, `text_dni_selec_document`, `text_license`, `text_passport`, `select_text`, `text_body_country`, `text_msn_selec_document` |
| Captura y galería | `text_tittle_intro_doc_front`, `text_tittle_intro_doc`, `text_btn_introduction_doc`, `text_btn_intro_doc_galery`, `text_error_path_image_pick` |
| Vista previa | `text_info_preview`, `text_confirm_preview`, `text_retry_previe` |
| Carga y envío | `text_loader_init`, `text_loader`, `text_varification_title`, `text_varification_body`, `text_finish_upload`, `text_info_upload`, `text_document_validation` |
| Consulta del resultado | `text_progress_result`, `text_progress_delay_result`, `text_progress_delay_finish_result` |
| Error documental y reintento | `text_title_document_error`, `text_sub_title_document_error`, `text_error_capture_document`, `text_title_button_retry` |
| Creación y resultados: cierre facial, nueva captura y reintento | Claves `identity_error_*` de los ejemplos. |
| Error general o validación fallida | `text_varification_title_error`, `text_varification_body_error`, `text_varification_title_compliance_error`, `text_varification_body_compliance_error`, `text_error_compliance_not_allowed`, `unknown_error`, `general_error`, `text_empty_balance` |
| Error de conexión | `timeout_error`, `no_internet_error`, `connection_lost_error` |
| Resultado final | `text_finish`, `text_sub_tittle_finish` |
| Confirmación de salida | `text_undo`, `text_sub_tittle_undo`, `text_tittle_undo`, `terminate_text`, `text_cancel` |
| Face Liveness: preparación y fotosensibilidad | `amplify_ui_liveness_get_ready_a11y_photosensitivity_icon_content_description`, `amplify_ui_liveness_get_ready_begin_check`, `amplify_ui_liveness_get_ready_center_face_label`, `amplify_ui_liveness_get_ready_photosensitivity_description`, `amplify_ui_liveness_get_ready_photosensitivity_dialog_description`, `amplify_ui_liveness_get_ready_photosensitivity_dialog_dismiss`, `amplify_ui_liveness_get_ready_photosensitivity_dialog_title`, `amplify_ui_liveness_get_ready_photosensitivity_title` |
| Face Liveness: instrucciones y captura | `amplify_ui_liveness_challenge_a11y_cancel_content_description`, `amplify_ui_liveness_challenge_connecting`, `amplify_ui_liveness_challenge_instruction_hold_face_during_freshness`, `amplify_ui_liveness_challenge_instruction_move_face`, `amplify_ui_liveness_challenge_instruction_move_face_closer`, `amplify_ui_liveness_challenge_instruction_move_face_further`, `amplify_ui_liveness_challenge_instruction_multiple_faces_detected`, `amplify_ui_liveness_challenge_recording_indicator_label`, `amplify_ui_liveness_challenge_verifying` |

## Instrucciones de captura

BlinkID UX incluye sus propias instrucciones de captura localizadas. Las claves `mbic_*` que se conservan en los ejemplos corresponden a la interfaz anterior de Capture y no personalizan BlinkID UX. Para su interfaz de captura, usa los recursos de la versión fijada de BlinkID UX. Las pantallas de introducción, vista previa y error de Become usan las claves indicadas arriba.

## Formato y verificación

- Conserva el significado de las instrucciones de cámara, accesibilidad y fotosensibilidad.
- Mantén los marcadores de formato y los saltos de línea intencionales. En el XML de Android, escapa `&` como `&amp;`, conserva los apóstrofos escapados y mantén el `app_name` de la app.
- No cambies nombres de claves para corregir su escritura ni dupliques entradas de un mismo idioma.
- Después de recompilar la app, verifica en un dispositivo físico la introducción, captura, reintentos, errores, carga y resultados en cada idioma admitido.
