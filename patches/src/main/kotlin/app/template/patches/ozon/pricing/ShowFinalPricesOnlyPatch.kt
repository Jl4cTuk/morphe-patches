package app.template.patches.ozon.pricing

import app.morphe.patcher.extensions.InstructionExtensions.addInstructions
import app.morphe.patcher.patch.bytecodePatch
import app.template.patches.ozon.shared.Constants.COMPATIBILITY_OZON_CURRENT

@Suppress("unused")
val showFinalPricesOnlyPatch = bytecodePatch(
    name = "Show final prices only",
    description = "Hides crossed-out prices and discount percentages while preserving the current price details.",
    default = true,
) {
    compatibleWith(COMPATIBILITY_OZON_CURRENT)

    execute {
        PriceComponentTextFingerprint.method.addInstructions(
            0,
            """
                iget-object v0, p0, Lru/ozon/uni/atoms/data/price/PriceDTO${'$'}Component;->textStyle:Lru/ozon/uni/atoms/data/price/PriceDTO${'$'}Component${'$'}TextStyle;
                if-eqz v0, :show_component
                invoke-virtual {v0}, Ljava/lang/Enum;->ordinal()I
                move-result v0
                add-int/lit8 v0, v0, -0x4
                if-eqz v0, :hide_component

                :show_component
                iget-object v0, p0, Lru/ozon/uni/atoms/data/price/PriceDTO${'$'}Component;->text:Ljava/lang/String;
                return-object v0

                :hide_component
                const/4 v0, 0x0
                return-object v0
            """.trimIndent(),
        )

        PriceDiscountFingerprint.method.addInstructions(
            0,
            """
                const/4 v0, 0x0
                return-object v0
            """.trimIndent(),
        )
    }
}
