package app.template.patches.rustore.gaming

import app.morphe.patcher.extensions.InstructionExtensions.addInstructions
import app.morphe.patcher.patch.BytecodePatchContext

context(_: BytecodePatchContext)
internal fun disableGamingProfile() {
    MineV2ViewModelOpenGameCenterFingerprint.method.addInstructions(0, "return-void")
    GameCenterV2ButtonComposableFingerprint.method.addInstructions(0, "return-void")
    GameCenterV1ButtonComposableFingerprint.method.addInstructions(0, "return-void")
}
