# Localización de la SDK para Android

## Textos del recorrido

Los títulos, instrucciones, consentimiento, selección de país y documento, introducciones y resultados se administran en la configuración de tu contrato. No necesitas copiar archivos de traducción para recibirlos. Cuando falta un texto, se conserva el valor predeterminado de la SDK.

Los archivos locales son opcionales para las instrucciones de captura, prueba de vida y mensajes de carga o conexión que se describen aquí. Agrega únicamente las claves que quieras personalizar. Si conservas personalizaciones locales antiguas del recorrido, revísalas para evitar que impidan aplicar cambios posteriores del contrato.

## Claves admitidas

| Componente | Versión de esta distribución | Prefijo de las claves |
| --- | --- | --- |
| BlinkID UX | 8002.0.0 | `mb_blinkid_*` |
| Amplify Face Liveness | 1.10.0 | `amplify_ui_liveness_*` |

Usa el [ejemplo en español](../examples/custom-texts/res/values/strings.xml) y el [ejemplo en inglés](../examples/custom-texts/res/values-b+en/strings.xml) para consultar los nombres exactos y sus valores de referencia. Los nombres difieren entre plataformas y distribuciones; usa solo las claves que corresponden a esta entrega.

También puedes adaptar estos mensajes locales:

| Mensaje | Claves |
| --- | --- |
| Carga inicial | `text_loader_init` |
| Conexión | `timeout_error`, `no_internet_error`, `connection_lost_error` |
| Progreso | `text_info_upload`, `text_progress_result`, `text_progress_delay_result` y `text_progress_delay_finish_result`. La SDK agrega el porcentaje al mensaje de envío. |

## Personalizar los textos

```xml
<resources>
    <string name="mb_blinkid_camera_flip_document">Voltea el documento</string>
    <string name="amplify_ui_liveness_challenge_connecting">Conectando…</string>
</resources>
```

Agrega las entradas a `app/src/main/res/values/become_strings.xml`. Para otros idiomas utiliza carpetas como `values-es` y `values-en`; evita definir el mismo idioma simultáneamente en `values-en` y `values-b+en`. Algunas instrucciones de Amplify pueden estar asignadas al contrato; el valor local se utiliza cuando esa instrucción no tiene un texto configurado.

## Comprobar la personalización

Conserva los nombres exactos, las mayúsculas y los marcadores de formato. Mantén el significado de las instrucciones de seguridad y fotosensibilidad. Compila de nuevo tu app tras cambiar los recursos y comprueba cada idioma en un dispositivo físico. No necesitas reemplazar la SDK para cambiar estos valores locales.

Vuelve a la [guía de integración](../README.md).

## Catálogo de claves

Copia solo las entradas que vayas a personalizar. Conserva los marcadores de formato y los saltos de línea.

### Carga, progreso y conexión

| Clave | Valor de referencia en español |
| --- | --- |
| `text_loader_init` | Iniciando… |
| `timeout_error` | La conexión ha tardado demasiado. Inténtalo otra vez. |
| `no_internet_error` | No tienes conexión a Internet. Verifica tu red. |
| `connection_lost_error` | Se perdió la conexión a Internet. Inténtalo nuevamente. |
| `text_info_upload` | Estamos subiendo tu información... |
| `text_progress_result` | Verificando el resultado... |
| `text_progress_delay_result` | Estamos por finalizar |
| `text_progress_delay_finish_result` | Estamos tardando un poco más de lo normal |

### Captura de documentos

| Clave | Valor de referencia en español |
| --- | --- |
| `mb_blinkid_accessibility_exit_scanning` | Salir del escaneo |
| `mb_blinkid_accessibility_flashlight_off` | Linterna apagada |
| `mb_blinkid_accessibility_flashlight_on` | Linterna encendida |
| `mb_blinkid_accessibility_next_page` | Página siguiente |
| `mb_blinkid_accessibility_previous_page` | Página anterior |
| `mb_blinkid_accessibility_show_help_screens` | Mostrar pantallas de ayuda |
| `mb_blinkid_accessibility_success_card_number_side_scanned` | ¡Perfecto! Lado con el número de la tarjeta escaneado |
| `mb_blinkid_accessibility_success_card_scanned` | ¡Perfecto! Tarjeta escaneada |
| `mb_blinkid_accessibility_success_document_scanned` | ¡Bien! Documento escaneado |
| `mb_blinkid_accessibility_success_first_side_scanned` | ¡Anverso escaneado! |
| `mb_blinkid_accessibility_turn_flashlight_off` | Apagar linterna |
| `mb_blinkid_accessibility_turn_flashlight_on` | Encender linterna |
| `mb_blinkid_back_instructions` | Escanee el reverso de un documento |
| `mb_blinkid_barcode_id_instructions` | Escanea el reverso del documento con el código de barras |
| `mb_blinkid_barcode_instructions` | Escanea el código de barras |
| `mb_blinkid_blur_detected` | Mantén el documento y el teléfono quietos |
| `mb_blinkid_camera_flip_document` | Dele la vuelta al documento |
| `mb_blinkid_camera_permission_required` | Se requiere permiso de cámara. |
| `mb_blinkid_close` | Cerrar |
| `mb_blinkid_consent_description` | Almacenar y/o acceder a información personal para verificar su identidad. |
| `mb_blinkid_consent_no` | No dar consentimiento |
| `mb_blinkid_consent_privacy_link` | Aviso de privacidad |
| `mb_blinkid_consent_privacy_notice` | Sus decisiones nos ayudan a proteger su información personal y mejorar nuestra tecnología, manteniendo los más altos estándares de privacidad de datos, como se describe en el %1$s. |
| `mb_blinkid_consent_privacy_switch` | Doy mi consentimiento para que el proveedor use mis datos personales con el fin de optimizar su tecnología. |
| `mb_blinkid_consent_title` | Necesitamos su consentimiento para: |
| `mb_blinkid_consent_yes` | Dar consentimiento |
| `mb_blinkid_decrease_lighting_intensity` | Muévase a un lugar con menos luz |
| `mb_blinkid_dialog_back_button` | Atrás |
| `mb_blinkid_dialog_done_button` | Hecho |
| `mb_blinkid_dialog_next_button` | Siguiente |
| `mb_blinkid_dialog_skip_button` | Omitir |
| `mb_blinkid_document_class_filtered_dialog_message` | Intente escanear otro documento. |
| `mb_blinkid_document_class_filtered_dialog_title` | Documento no aceptado |
| `mb_blinkid_document_not_fully_visible` | Mantenga el documento visible en su totalidad |
| `mb_blinkid_enable_camera` | Activar cámara |
| `mb_blinkid_enable_permission_help` | Ahora se le redirigirá a la configuración de permisos, donde tendrá que otorgar a la aplicación permiso de cámara. |
| `mb_blinkid_enable_permission_help_instant_app` | Dele permiso a la cámara en la configuración de permisos de la aplicación. |
| `mb_blinkid_face_photo_not_fully_visible` | Mantén la foto del rostro completamente visible |
| `mb_blinkid_flashlight_warning_message` | Tenga cuidado con el resplandor de la linterna.<br>Mueva suavemente su ID a su alrededor para evitarlo. |
| `mb_blinkid_front_instructions` | Escanee el anverso de un documento |
| `mb_blinkid_glare_detected` | Inclina o mueve el documento para eliminar el reflejo |
| `mb_blinkid_help_screen_barcode_msg1` | Asegúrate de no cubrir partes del código de barras con el dedo. También ten cuidado con los reflejos que puedan cubrirlo y hacerlo ilegible. |
| `mb_blinkid_help_screen_barcode_msg2` | Evita la luz directa fuerte, ya que puede generar reflejos en el código de barras y dificultar el escaneo. Si no ves el código de barras con claridad, es posible que la cámara tampoco pueda leerlo. |
| `mb_blinkid_help_screen_barcode_msg3` | Intenta mantener el teléfono y el código de barras estables mientras escaneas. Si se mueve alguno de los dos, la imagen puede salir borrosa y el código de barras podría no leerse correctamente. |
| `mb_blinkid_help_screen_barcode_title1` | Mantén el código de barras visible |
| `mb_blinkid_help_screen_mrz_msg1` | Asegúrate de no cubrir partes del MRZ con el dedo. También ten cuidado con los reflejos que puedan cubrirlo y hacerlo ilegible. |
| `mb_blinkid_help_screen_mrz_title1` | Mantén el MRZ visible |
| `mb_blinkid_help_screen_msg1` | Asegúrese de que no está cubriendo partes del documento con el dedo, incluidas las líneas inferiores. Además, tenga cuidado con los reflejos de los hologramas que sobrepasan los campos del documento. |
| `mb_blinkid_help_screen_msg2` | Evite la luz intensa directa porque se refleja en el documento y puede hacer que algunas partes del documento sean ilegibles. Si no puede leer los datos del documento, tampoco serán visibles para la cámara. |
| `mb_blinkid_help_screen_msg3` | Intente mantener el teléfono y el documento quietos mientras escanea. Mover cualquiera de los dos puede difuminar la imagen y hacer que los datos del documento sean ilegibles. |
| `mb_blinkid_help_screen_title1` | Mantenga todos los campos visibles |
| `mb_blinkid_help_screen_title2` | Cuidado con la luz intensa |
| `mb_blinkid_help_screen_title3` | No se mueva mientras escanea |
| `mb_blinkid_increase_lighting_intensity` | Muévase a un lugar con más luz |
| `mb_blinkid_instructions_scan_barcode_last_page` | Escanee el código de barras de la última página |
| `mb_blinkid_instructions_turn_page_left` | Ir a la página de la izquierda |
| `mb_blinkid_instructions_turn_page_right` | Vaya a la página de la derecha. |
| `mb_blinkid_instructions_turn_page_top` | Ir a la página superior |
| `mb_blinkid_keep_document_parallel` | Mantén el documento paralelo al teléfono |
| `mb_blinkid_left_page_instructions` | Escanear la página de la izquierda |
| `mb_blinkid_license_locked` | Escaneo no disponible |
| `mb_blinkid_loading` | Cargando… |
| `mb_blinkid_move_closer` | Acercarse |
| `mb_blinkid_move_farther` | Alejarse |
| `mb_blinkid_mrz_id_instructions` | Escanea el lado del MRZ de un documento |
| `mb_blinkid_need_help_tooltip` | ¿Necesitas ayuda? |
| `mb_blinkid_ok` | OK |
| `mb_blinkid_onboarding_dialog_barcode_id_message` | Cada tipo de documento puede tener un formato y una ubicación del código de barras diferente. Busca el código de barras tanto en la parte frontal como en el reverso del documento. |
| `mb_blinkid_onboarding_dialog_barcode_id_title` | Localiza el código de barras en el documento |
| `mb_blinkid_onboarding_dialog_barcode_message` | Busca un código de barras (una serie de líneas negras o un código cuadrado). Asegúrate de que la lente de la cámara esté limpia y de que el código de barras esté bien iluminado. |
| `mb_blinkid_onboarding_dialog_barcode_title` | Busca y escanea el código de barras |
| `mb_blinkid_onboarding_dialog_message` | Asegúrese de mantener el documento bien iluminado. Todos los campos del documento deben ser visibles en la pantalla de la cámara. |
| `mb_blinkid_onboarding_dialog_mrz_id_message` | En la parte inferior de la parte frontal o del reverso del documento encontrarás una larga cadena de caracteres dividida en 2 o 3 líneas y separada por flechas (&lt;&lt; o &gt;&gt;). |
| `mb_blinkid_onboarding_dialog_mrz_id_title` | Localiza el MRZ en el documento |
| `mb_blinkid_onboarding_dialog_title` | Mantenga todos los detalles visibles |
| `mb_blinkid_optional` | Opcional |
| `mb_blinkid_passport_help_open_passport_subtitle` | Abra el pasaporte en la página con su foto y datos personales. En esta página aparecen su nombre, fecha de nacimiento, número de pasaporte y otros datos identificativos. |
| `mb_blinkid_passport_help_open_passport_title` | Abra el pasaporte por la página de datos |
| `mb_blinkid_passport_onboarding_subtitle` | Abra el pasaporte por la página que contiene su foto y sus datos personales. Asegúrese de que el pasaporte esté bien iluminado. Todos los campos deben verse por completo. |
| `mb_blinkid_passport_onboarding_title` | Abra el pasaporte |
| `mb_blinkid_passport_scan_data_page_instructions` | Escanea la página de datos del documento. |
| `mb_blinkid_privacy_notice` | Aviso de privacidad |
| `mb_blinkid_recognition_timeout_dialog_message` | Asegúrate de que el documento esté bien iluminado, se vea completamente y no haya reflejos. |
| `mb_blinkid_recognition_timeout_dialog_retry_button` | Reintentar |
| `mb_blinkid_recognition_timeout_dialog_title` | No se ha podido leer el documento |
| `mb_blinkid_retry` | Reintentar |
| `mb_blinkid_right_page_instructions` | Escanear la página de la derecha |
| `mb_blinkid_scanning_wrong_page_left` | Ir a la página de la izquierda |
| `mb_blinkid_scanning_wrong_page_right` | Ir a la página de la derecha |
| `mb_blinkid_scanning_wrong_page_top` | Ir a la página superior |
| `mb_blinkid_scanning_wrong_side` | Dar la vuelta al documento |
| `mb_blinkid_screen_detected` | Coloca el documento sobre un fondo liso |
| `mb_blinkid_top_page_instructions` | Escanear la página superior |
| `mb_blinkid_unsupported_document_message` | Escanee la parte frontal de un documento compatible. |
| `mb_blinkid_unsupported_document_title` | Documento no reconocido |
| `mb_blinkid_warning` | Advertencia |

### Captura facial

| Clave | Valor de referencia en español |
| --- | --- |
| `amplify_ui_liveness_challenge_a11y_cancel_content_description` | Cancelar desafío |
| `amplify_ui_liveness_challenge_connecting` | Conectando |
| `amplify_ui_liveness_challenge_instruction_hold_face_during_freshness` | No te muevas |
| `amplify_ui_liveness_challenge_instruction_move_face` | Mueve tu rostro frente a la cámara |
| `amplify_ui_liveness_challenge_instruction_move_face_closer` | Acércate |
| `amplify_ui_liveness_challenge_instruction_move_face_further` | Aléjate |
| `amplify_ui_liveness_challenge_instruction_multiple_faces_detected` | Solo un rostro por verificación |
| `amplify_ui_liveness_challenge_recording_indicator_label` | REC |
| `amplify_ui_liveness_challenge_verifying` | Verificando |
| `amplify_ui_liveness_get_ready_a11y_photosensitivity_icon_content_description` | Información sobre fotosensibilidad |
| `amplify_ui_liveness_get_ready_begin_check` | Iniciar verificación de video |
| `amplify_ui_liveness_get_ready_center_face_label` | Centra tu rostro |
| `amplify_ui_liveness_get_ready_photosensitivity_description` | Esta verificación parpadea con diferentes colores. Usa precaución si eres fotosensible. |
| `amplify_ui_liveness_get_ready_photosensitivity_dialog_description` | Algunas personas pueden experimentar convulsiones epilépticas al exponerse a luces de colores. Usa precaución si tú o alguien de tu familia tiene una condición epiléptica. |
| `amplify_ui_liveness_get_ready_photosensitivity_dialog_dismiss` | Entendido |
| `amplify_ui_liveness_get_ready_photosensitivity_dialog_title` | Advertencia de fotosensibilidad |
| `amplify_ui_liveness_get_ready_photosensitivity_title` | Advertencia de fotosensibilidad |
