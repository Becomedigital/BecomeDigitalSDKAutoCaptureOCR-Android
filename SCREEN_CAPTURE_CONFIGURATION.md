# Protección contra capturas de pantalla — Android

Esta guía corresponde al AAR incluido en este repositorio. Los constructores que omiten `preventScreenCapture` usan `false` en Debug y Release. En distribuciones anteriores, el valor predeterminado era `true`.

| Valor | Comportamiento de la SDK |
| --- | --- |
| `false` (predeterminado) | La SDK quita `WindowManager.LayoutParams.FLAG_SECURE` de su actividad. |
| `true` | La SDK aplica `FLAG_SECURE` para proteger su actividad contra capturas y grabaciones. |

La opción controla la actividad de la SDK. No modifica otras actividades de la app ni las protecciones que Android u otros componentes apliquen por separado.

## Habilitar la protección

```java
BDIVConfig config = new BDIVConfig(
    clientId, clientSecret, contractId, documentTypes,
    true, // allowLibraryLoading
    userId
);
config.setPreventScreenCapture(true);
BecomeResponseManager.getInstance().startAuthentication(this, config);
```

En Kotlin también puedes llamar a `config.setPreventScreenCapture(true)`. El constructor completo acepta `preventScreenCapture` después de `debugLogsEnabled`. Omite la opción o usa `false` para permitir capturas. La opción está disponible en onboarding y autenticación.

## Actualización

Revisa las llamadas que antes omitían el parámetro. Llama a `setPreventScreenCapture(true)` donde deba mantenerse la protección. Los valores explícitos conservan el comportamiento solicitado. Recompila la app con el AAR y las dependencias indicadas en el [README](README.md).

En un dispositivo físico, verifica las capturas y la grabación durante la captura documental, la validación facial, la cancelación y el regreso a la app. Prueba ambos valores si la app admite los dos modos.
