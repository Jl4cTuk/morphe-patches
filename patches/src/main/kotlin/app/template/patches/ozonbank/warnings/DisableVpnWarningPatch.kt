package app.template.patches.ozonbank.warnings

import app.morphe.patcher.extensions.InstructionExtensions.addInstructions
import app.morphe.patcher.patch.bytecodePatch
import app.template.patches.ozonbank.shared.Constants.COMPATIBILITY_OZON_BANK_CURRENT

@Suppress("unused")
val disableOzonBankVpnWarningPatch = bytecodePatch(
    name = "Disable Ozon Bank VPN warning",
    description = "Removes the warning shown when Ozon Bank detects an active VPN connection.",
    default = true,
) {
    compatibleWith(COMPATIBILITY_OZON_BANK_CURRENT)

    execute {
        WebViewVpnStateFingerprint.method.addInstructions(
            0,
            """
                const/4 v0, 0x0
                return v0
            """.trimIndent(),
        )
    }
}
