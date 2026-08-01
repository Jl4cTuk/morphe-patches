package app.template.patches.rustore.notifications

import app.morphe.patcher.extensions.InstructionExtensions.addInstructions
import app.morphe.patcher.patch.BytecodePatchContext

context(_: BytecodePatchContext)
internal fun hideNotificationButtons() {
    NotificationCenterIconButtonWidgetFingerprint.method.addInstructions(0, "return-void")
}
