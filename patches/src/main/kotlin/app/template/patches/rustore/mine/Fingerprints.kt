package app.template.patches.rustore.mine

import app.morphe.patcher.Fingerprint

/**
 * Matches the `MainActivity` composable that passes
 * `MainViewState.featureMineRedesignEnabled` to `MineDestination`.
 */
object MineDestinationRedesignFlagFingerprint : Fingerprint(
    definingClass = "Lru/vk/store/app/m;",
    name = "invoke",
    returnType = "Ljava/lang/Object;",
)
