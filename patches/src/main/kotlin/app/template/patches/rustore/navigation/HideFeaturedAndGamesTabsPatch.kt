package app.template.patches.rustore.navigation

import app.morphe.patcher.extensions.InstructionExtensions.addInstructions
import app.morphe.patcher.patch.bytecodePatch
import app.template.patches.rustore.shared.Constants.COMPATIBILITY_RUSTORE
import com.android.tools.smali.dexlib2.Opcode
import com.android.tools.smali.dexlib2.iface.instruction.ReferenceInstruction
import com.android.tools.smali.dexlib2.iface.reference.StringReference

@Suppress("unused")
val hideFeaturedAndGamesTabsPatch = bytecodePatch(
    name = "Hide Featured and Games tabs",
    description = "Removes the Featured and Games tabs and opens Apps by default.",
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

        val rootNavHostMethod = RootNavHostFingerprint.method
        val instructions = rootNavHostMethod.implementation!!.instructions
        val rootGraphRouteIndex = instructions.indexOfFirst { instruction ->
            val string = (instruction as? ReferenceInstruction)?.reference as? StringReference

            instruction.opcode in setOf(Opcode.CONST_STRING, Opcode.CONST_STRING_JUMBO) &&
                string?.string == "ROOT_GRAPH_ROUTE"
        }
        require(rootGraphRouteIndex >= 0) {
            "Could not find the root navigation graph route"
        }

        val navHostCallOffset = instructions
            .subList(rootGraphRouteIndex + 1, instructions.size)
            .indexOfFirst { it.opcode == Opcode.INVOKE_STATIC_RANGE }
        require(navHostCallOffset >= 0) {
            "Could not find the root NavHost call"
        }

        val navHostCallIndex = rootGraphRouteIndex + 1 + navHostCallOffset
        rootNavHostMethod.addInstructions(
            navHostCallIndex,
            """
                sget-object v3, Ltj1/t${'$'}d;->b:Ltj1/t${'$'}d;
                iget-object v5, v3, Ltj1/t;->a:Ljava/lang/String;
            """,
        )
    }
}
