# Localization and text overrides — Android

## Setup

1. Copy needed keys from the [Spanish](../examples/custom-texts/res/values/strings.xml) or [English](../examples/custom-texts/res/values-b+en/strings.xml) example.
2. Use the host app's `res/values/become_strings.xml` and localized variants such as `values-en/become_strings.xml`.
3. Preserve each `name`, edit the values, and rebuild the host app.
4. Keep one `<resources>` element per XML file and avoid duplicate keys for a locale.

Host-defined native keys take priority for those individual phrases. Missing keys retain the contract-assigned phrase or the SDK's default. Customize each supported locale. Updating text resources does not require rebuilding the SDK binary.

## Keys by screen

A shared key affects every screen that uses it. Copy full key names from the template; `*` below denotes a prefix, not a literal key.

| Screen or purpose | Keys |
| --- | --- |
| Introduction | `text_tittle_general_intro`, `text_sub_tittle_general_intro`, `text_start_btn`, `and_intro_general`, `text_video_intro_general`, `text_document_intr_general` |
| Facial introduction and analysis | `text_video_intro`, `text_tittle_selfie`, `btn_text_recording_video`, `text_loader_selfie`, `text_progress_inteligence` |
| Country and document selection | `text_tittle_selec_document`, `text_dni_selec_document`, `text_license`, `text_passport`, `select_text`, `text_body_country`, `text_msn_selec_document` |
| Capture and gallery | `text_tittle_intro_doc_front`, `text_tittle_intro_doc`, `text_btn_introduction_doc`, `text_btn_intro_doc_galery`, `text_error_path_image_pick` |
| Preview | `text_info_preview`, `text_confirm_preview`, `text_retry_previe` |
| Loading and upload | `text_loader_init`, `text_loader`, `text_varification_title`, `text_varification_body`, `text_finish_upload`, `text_info_upload`, `text_document_validation` |
| Result checking | `text_progress_result`, `text_progress_delay_result`, `text_progress_delay_finish_result` |
| Document error and retry | `text_title_document_error`, `text_sub_title_document_error`, `text_error_capture_document`, `text_title_button_retry` |
| Creation and results: facial exit, recapture, retry | `identity_error_*` keys in the examples. |
| General error or failed validation | `text_varification_title_error`, `text_varification_body_error`, `text_varification_title_compliance_error`, `text_varification_body_compliance_error`, `text_error_compliance_not_allowed`, `unknown_error`, `general_error`, `text_empty_balance` |
| Connection error | `timeout_error`, `no_internet_error`, `connection_lost_error` |
| Final result | `text_finish`, `text_sub_tittle_finish` |
| Exit confirmation | `text_undo`, `text_sub_tittle_undo`, `text_tittle_undo`, `terminate_text`, `text_cancel` |
| Face Liveness: preparation and photosensitivity | `amplify_ui_liveness_get_ready_a11y_photosensitivity_icon_content_description`, `amplify_ui_liveness_get_ready_begin_check`, `amplify_ui_liveness_get_ready_center_face_label`, `amplify_ui_liveness_get_ready_photosensitivity_description`, `amplify_ui_liveness_get_ready_photosensitivity_dialog_description`, `amplify_ui_liveness_get_ready_photosensitivity_dialog_dismiss`, `amplify_ui_liveness_get_ready_photosensitivity_dialog_title`, `amplify_ui_liveness_get_ready_photosensitivity_title` |
| Face Liveness: instructions and capture | `amplify_ui_liveness_challenge_a11y_cancel_content_description`, `amplify_ui_liveness_challenge_connecting`, `amplify_ui_liveness_challenge_instruction_hold_face_during_freshness`, `amplify_ui_liveness_challenge_instruction_move_face`, `amplify_ui_liveness_challenge_instruction_move_face_closer`, `amplify_ui_liveness_challenge_instruction_move_face_further`, `amplify_ui_liveness_challenge_instruction_multiple_faces_detected`, `amplify_ui_liveness_challenge_recording_indicator_label`, `amplify_ui_liveness_challenge_verifying` |

## Capture guidance

BlinkID UX supplies its own localized capture guidance. The `mbic_*` entries retained in the examples belong to the earlier Capture UI and do not customize BlinkID UX. Use the resources supplied by the pinned BlinkID UX dependency for its capture UI. Become's introduction, preview, and error screens use the keys above.

## Formatting and verification

- Preserve camera, accessibility, and photosensitivity instructions.
- Keep formatting placeholders and intentional line breaks. In the iOS template, preserve `%d%%` for upload progress and `%@` in facial error messages.
- In Android XML, escape `&` as `&amp;` and preserve escaped apostrophes; keep the host app's `app_name`.
- Do not rename keys to correct existing spelling or duplicate entries for a locale.
- After rebuilding the host app, verify introduction, capture, retry/error, loading, and results in every supported language on a physical device.
