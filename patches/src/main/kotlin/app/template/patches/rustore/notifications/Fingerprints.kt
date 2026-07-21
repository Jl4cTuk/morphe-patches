package app.template.patches.rustore.notifications

import app.morphe.patcher.Fingerprint

/** Matches the shared `NotificationCenterIconButtonWidget` composable. */
object NotificationCenterIconButtonWidgetFingerprint : Fingerprint(
    returnType = "V",
    parameters = listOf(
        "Lvo2/h;",
        "Lws2/b;",
        "Landroidx/compose/runtime/a;",
        "I",
        "I",
    ),
    custom = { method, classDef ->
        classDef.sourceFile == "NotificationCenterIconButtonWidget.kt" &&
            method.implementation != null
    },
)
