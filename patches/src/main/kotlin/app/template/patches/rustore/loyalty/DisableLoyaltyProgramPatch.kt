package app.template.patches.rustore.loyalty

import app.morphe.patcher.extensions.InstructionExtensions.addInstructionsWithLabels
import app.morphe.patcher.extensions.InstructionExtensions.replaceInstruction
import app.morphe.patcher.patch.BytecodePatchContext
import app.morphe.patcher.util.smali.ExternalLabel
import com.android.tools.smali.dexlib2.Opcode
import com.android.tools.smali.dexlib2.iface.instruction.OneRegisterInstruction
import com.android.tools.smali.dexlib2.iface.instruction.ReferenceInstruction
import com.android.tools.smali.dexlib2.iface.reference.MethodReference
import com.android.tools.smali.dexlib2.iface.reference.StringReference

private const val DISABLED_LOYALTY_FLAG_KEY =
    "featureLoyaltyDisabledByPatch"

context(_: BytecodePatchContext)
internal fun disableLoyaltyProgram() {
    val featureMethod = LoyaltyFeatureRegistryFingerprint.method
    val featureInstructions = featureMethod.implementation!!.instructions
    val flagConstants = featureInstructions.withIndex().filter { (_, instruction) ->
        val string =
            (instruction as? ReferenceInstruction)?.reference as? StringReference
        instruction.opcode == Opcode.CONST_STRING &&
            string?.string == LOYALTY_FLAG_KEY
    }
    require(flagConstants.size == 1) {
        "Expected one loyalty feature key, found ${flagConstants.size}"
    }

    val (flagIndex, flagInstruction) = flagConstants.single()
    val flagRegister =
        (flagInstruction as? OneRegisterInstruction)?.registerA
    require(flagRegister != null) {
        "Loyalty feature key instruction has an unexpected format"
    }
    featureMethod.replaceInstruction(
        flagIndex,
        "const-string v$flagRegister, \"$DISABLED_LOYALTY_FLAG_KEY\"",
    )

    val rulesMethod = InterestingPositionRulesRepositoryFingerprint.method
    val rulesInstructions = rulesMethod.implementation!!.instructions
    val getUpperCalls = rulesInstructions.withIndex().filter { (_, instruction) ->
        val method =
            (instruction as? ReferenceInstruction)?.reference as? MethodReference
        instruction.opcode == Opcode.INVOKE_VIRTUAL &&
            method?.definingClass == LOYALTY_BANNERS_DTO &&
            method.name == "getUpper" &&
            method.parameterTypes.isEmpty() &&
            method.returnType == "Z"
    }
    require(getUpperCalls.size == 1) {
        "Expected one BANNERS upper-property read, found ${getUpperCalls.size}"
    }

    val upperResultIndex = getUpperCalls.single().index + 1
    val upperResultInstruction = rulesInstructions[upperResultIndex]
    require(upperResultInstruction.opcode == Opcode.MOVE_RESULT) {
        "BANNERS upper-property read is not followed by move-result"
    }
    val upperRegister =
        (upperResultInstruction as? OneRegisterInstruction)?.registerA
    require(upperRegister != null) {
        "BANNERS upper-property result has an unexpected format"
    }

    val iteratorCalls = rulesInstructions.withIndex().filter { (index, instruction) ->
        val method =
            (instruction as? ReferenceInstruction)?.reference as? MethodReference
        index < getUpperCalls.single().index &&
            instruction.opcode == Opcode.INVOKE_INTERFACE &&
            method?.definingClass == "Ljava/util/Iterator;" &&
            method.name == "hasNext" &&
            method.parameterTypes.isEmpty() &&
            method.returnType == "Z"
    }
    require(iteratorCalls.size == 1) {
        "Expected one position-rule iterator, found ${iteratorCalls.size}"
    }

    rulesMethod.addInstructionsWithLabels(
        upperResultIndex + 1,
        "if-nez v$upperRegister, :skip_upper_loyalty_banner",
        ExternalLabel(
            "skip_upper_loyalty_banner",
            iteratorCalls.single().value,
        ),
    )
}
