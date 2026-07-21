package app.template.patches.ozon.lottery

import app.morphe.patcher.extensions.InstructionExtensions.addInstructions
import app.morphe.patcher.patch.bytecodePatch
import app.template.patches.ozon.shared.Constants.COMPATIBILITY_OZON_CURRENT

@Suppress("unused")
val disableLotteryPopupPatch = bytecodePatch(
    name = "Disable lottery popup",
    description = "Prevents the lottery onboarding popup from opening.",
    default = true,
) {
    compatibleWith(COMPATIBILITY_OZON_CURRENT)

    execute {
        LotteryStartOnboardingFingerprint.method.addInstructions(0, "return-void")
    }
}
