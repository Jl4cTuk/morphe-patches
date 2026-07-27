package app.template.patches.ozon.checkout

import app.morphe.patcher.extensions.InstructionExtensions.addInstructions
import app.morphe.patcher.patch.bytecodePatch
import app.template.patches.ozon.shared.Constants.COMPATIBILITY_OZON_CURRENT

private val returnEmptyList = """
    invoke-static {}, Ljava/util/Collections;->emptyList()Ljava/util/List;
    move-result-object v0
    return-object v0
""".trimIndent()

private val removePvzTipId = """
    invoke-virtual {p1}, Lru/ozon/composer/state/ComposerRequestState;->f()Ljava/util/concurrent/ConcurrentHashMap;
    move-result-object v0
    const-string v1, "pvzTipID"
    invoke-virtual {v0, v1}, Ljava/util/concurrent/ConcurrentHashMap;->remove(Ljava/lang/Object;)Ljava/lang/Object;
""".trimIndent()

@Suppress("unused")
val disableCheckoutTipsPatch = bytecodePatch(
    name = "Disable checkout tips",
    description = "Removes courier tips UI and prevents tip IDs or tip API calls from being submitted.",
    default = true,
) {
    compatibleWith(COMPATIBILITY_OZON_CURRENT)

    execute {
        FreshCourierTipsMapperFingerprint.method.addInstructions(0, returnEmptyList)
        OrderDoneCourierTipsMapperFingerprint.method.addInstructions(0, returnEmptyList)

        FreshSendCourierTipsFingerprint.method.addInstructions(0, "return-void")
        OrderDoneSendCourierTipsFingerprint.method.addInstructions(0, "return-void")

        // Prevent deeplinks from introducing a tip ID.
        AddPvzTipIdFingerprint.method.addInstructions(0, "return-void")

        // Clear tip state both for normal requests and for server-driven checkout actions.
        AddCheckoutLocationParamsFingerprint.method.addInstructions(0, removePvzTipId)
        CheckoutInterceptStateFingerprint.method.addInstructions(0, removePvzTipId)
    }
}
