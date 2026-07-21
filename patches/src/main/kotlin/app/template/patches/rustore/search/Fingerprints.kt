package app.template.patches.rustore.search

import app.morphe.patcher.Fingerprint

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
