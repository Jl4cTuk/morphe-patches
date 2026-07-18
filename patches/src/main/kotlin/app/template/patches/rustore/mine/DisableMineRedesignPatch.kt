package app.template.patches.rustore.mine

import app.morphe.patcher.extensions.InstructionExtensions.getInstruction
import app.morphe.patcher.extensions.InstructionExtensions.replaceInstruction
import app.morphe.patcher.patch.bytecodePatch
import app.template.patches.rustore.shared.Constants.COMPATIBILITY_RUSTORE
import com.android.tools.smali.dexlib2.Opcode
import com.android.tools.smali.dexlib2.iface.instruction.ReferenceInstruction
import com.android.tools.smali.dexlib2.iface.instruction.TwoRegisterInstruction
import com.android.tools.smali.dexlib2.iface.reference.FieldReference

@Suppress("unused")
val disableMineRedesignPatch = bytecodePatch(
    name = "Disable Mine redesign",
    description = "Reverts the Mine screen to the classic layout, " +
        "disabling the redesigned V2/V3 interface.",
    default = false,
) {
    compatibleWith(COMPATIBILITY_RUSTORE)

    execute {
        val method = MineDestinationRedesignFlagFingerprint.method
        val flagReadIndex = method.implementation!!.instructions.indexOfFirst { instruction ->
            val field = (instruction as? ReferenceInstruction)?.reference as? FieldReference

            instruction.opcode == Opcode.IGET_BOOLEAN &&
                field?.definingClass == "Lm31/x0;" &&
                field.name == "m" &&
                field.type == "Z"
        }
        require(flagReadIndex >= 0) {
            "Could not find MainViewState.featureMineRedesignEnabled read"
        }

        val flagRegister = method
            .getInstruction<TwoRegisterInstruction>(flagReadIndex)
            .registerA

        // MineDestination chooses MineV2Screen for true and MineScreen for false.
        method.replaceInstruction(
            flagReadIndex,
            "const/4 v$flagRegister, 0x0",
        )
    }
}
