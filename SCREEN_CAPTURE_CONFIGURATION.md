# Screen-capture protection — Android

This guide applies to the AAR in this repository. Constructors that omit `preventScreenCapture` default to `false` in Debug and Release. Earlier distributions defaulted to `true`.

| Value | SDK behavior |
| --- | --- |
| `false` (default) | The SDK removes `WindowManager.LayoutParams.FLAG_SECURE` from its activity. |
| `true` | The SDK applies `FLAG_SECURE` to protect its activity from screenshots and recording. |

The option controls the SDK activity. It does not modify other host activities or override protections applied independently by Android or other components.

## Enable explicitly

```java
BDIVConfig config = new BDIVConfig(
    clientId, clientSecret, contractId, documentTypes,
    true, // allowLibraryLoading
    userId
);
config.setPreventScreenCapture(true);
BecomeResponseManager.getInstance().startAuthentication(this, config);
```

Kotlin can also call `config.setPreventScreenCapture(true)`. The full constructor accepts `preventScreenCapture` after `debugLogsEnabled`. Omit the setting or pass `false` to allow capture. Both onboarding and authentication support the option.

## Upgrade

Review calls that previously omitted this parameter. Call `setPreventScreenCapture(true)` where protection must remain enabled. Explicit values retain their requested behavior. Rebuild the host app with the AAR and dependencies in the [README](README.md).

On a physical device, verify screenshots and recording during document capture, facial verification, cancellation, and return to the host. Test both settings if the host supports both modes.
