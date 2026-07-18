package app.template.patches.rustore.ads

import app.morphe.patcher.Fingerprint

/**
 * Matches `RawAdvertisementRepositoryImpl.get()`, the central entry point
 * used to load SSP, MyTarget, and VKR advertisements.
 */
object RawAdvertisementRepoGetFingerprint : Fingerprint(
    definingClass = "Lt41/l0;",
    name = "a",
    returnType = "Ljava/lang/Object;",
    custom = { method, _ -> method.parameters.size == 7 },
)
