package app.template.patches.rustore.search

import app.morphe.patcher.extensions.InstructionExtensions.addInstructions
import app.morphe.patcher.patch.bytecodePatch
import app.template.patches.rustore.shared.Constants.COMPATIBILITY_RUSTORE

@Suppress("unused")
val disableSearchRecommendationsPatch = bytecodePatch(
    name = "Disable search recommendations",
    description = "Removes the Trending, Games, and Frequently searched sections from search.",
    default = true,
) {
    compatibleWith(COMPATIBILITY_RUSTORE)

    execute {
        SearchZeroContentConstructorFingerprint.method.addInstructions(
            0,
            """
                const/4 p1, 0x0
                const/4 p2, 0x0
                const/4 p4, 0x0
            """,
        )
    }
}
