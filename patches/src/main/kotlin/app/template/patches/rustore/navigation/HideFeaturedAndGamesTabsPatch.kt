package app.template.patches.rustore.navigation

import app.morphe.patcher.extensions.InstructionExtensions.addInstructions
import app.morphe.patcher.patch.bytecodePatch
import app.template.patches.rustore.shared.Constants.COMPATIBILITY_RUSTORE

@Suppress("unused")
val hideFeaturedAndGamesTabsPatch = bytecodePatch(
    name = "Hide Featured and Games tabs",
    description = "Removes the Featured and Games tabs from the bottom navigation bar.",
    default = true,
) {
    compatibleWith(COMPATIBILITY_RUSTORE)

    execute {
        BottomNavigationContainerFingerprint.method.addInstructions(
            0,
            """
                new-instance v0, Ljava/util/ArrayList;
                invoke-direct {v0, p0}, Ljava/util/ArrayList;-><init>(Ljava/util/Collection;)V

                sget-object v1, Ltj1/t${'$'}b;->b:Ltj1/t${'$'}b;
                invoke-virtual {v0, v1}, Ljava/util/ArrayList;->remove(Ljava/lang/Object;)Z

                new-instance v1, Ltj1/t${'$'}e;
                const/4 v2, 0x0
                invoke-direct {v1, v2}, Ltj1/t${'$'}e;-><init>(Z)V
                invoke-virtual {v0, v1}, Ljava/util/ArrayList;->remove(Ljava/lang/Object;)Z

                new-instance v1, Ltj1/t${'$'}e;
                const/4 v2, 0x1
                invoke-direct {v1, v2}, Ltj1/t${'$'}e;-><init>(Z)V
                invoke-virtual {v0, v1}, Ljava/util/ArrayList;->remove(Ljava/lang/Object;)Z

                move-object p0, v0
            """,
        )
    }
}
