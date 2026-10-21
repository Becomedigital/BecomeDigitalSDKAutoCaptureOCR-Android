# Diagnostic logging — Android

Enable the setting before starting the SDK for an investigation:

```kotlin
config.setDebugLogsEnabled(true)
BecomeResponseManager.getInstance().startAuthentication(activity, config)
```

Java uses the same `setDebugLogsEnabled(true)` method. The default is `false`; disable logging when the investigation ends.

In Logcat, filter with `tag:BecomeSDK level:DEBUG`. From a terminal:

```sh
adb logcat -v threadtime 'BecomeSDK:D' '*:S'
```

For support, include the SDK version, device, visible step, and relevant `BecomeSDK` lines. Review logs before sharing and exclude credentials, license contents, images, and personal data.
