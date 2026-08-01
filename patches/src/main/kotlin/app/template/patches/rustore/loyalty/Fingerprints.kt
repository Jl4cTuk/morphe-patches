package app.template.patches.rustore.loyalty

import app.morphe.patcher.Fingerprint
import com.android.tools.smali.dexlib2.iface.instruction.ReferenceInstruction
import com.android.tools.smali.dexlib2.iface.reference.TypeReference

internal const val LOYALTY_FLAG_KEY = "featureLoyaltyEnabled"
internal const val LOYALTY_BANNERS_DTO =
    "Lru/vk/store/feature/interesting/impl/data/InterestingPositionRuleDto\$Banners;"

/** Matches the feature registry initializer that declares the loyalty flag. */
object LoyaltyFeatureRegistryFingerprint : Fingerprint(
    name = "<clinit>",
    returnType = "V",
    parameters = emptyList(),
    strings = listOf(LOYALTY_FLAG_KEY),
    custom = { method, classDef ->
        classDef.sourceFile == "Features.kt" &&
            method.implementation != null
    },
)

/**
 * Matches the repository that maps server-provided Interesting screen
 * position rules into UI rules, including the independent `BANNERS` block.
 */
object InterestingPositionRulesRepositoryFingerprint : Fingerprint(
    returnType = "Ljava/io/Serializable;",
    parameters = listOf("L"),
    strings = listOf("dto"),
    custom = { method, classDef ->
        classDef.sourceFile == "InterestingPositionRulesRepository.kt" &&
            method.implementation?.instructions?.any { instruction ->
                val type =
                    (instruction as? ReferenceInstruction)?.reference as? TypeReference
                type?.type == LOYALTY_BANNERS_DTO
            } == true
    },
)
