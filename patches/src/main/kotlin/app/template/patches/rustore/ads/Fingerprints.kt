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

/**
 * Matches `SearchZeroState.Content`, whose first, second, and fourth fields
 * contain recommendations shown before a search query is entered.
 */
object SearchZeroContentConstructorFingerprint : Fingerprint(
    definingClass = "Lhb2/d\$a;",
    name = "<init>",
    returnType = "V",
    parameters = listOf(
        "Lhb2/b;",
        "Ljava/util/List;",
        "Loa2/b;",
        "Lrb2/d\$g;",
        "Ljava/util/Map;",
        "Ljava/util/Map;",
    ),
)
