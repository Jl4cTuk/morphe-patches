package app.template.patches.rustore.navigation

import app.morphe.patcher.Fingerprint

/** Matches `BottomNavigationContainer`, which receives the visible navigation tabs. */
object BottomNavigationContainerFingerprint : Fingerprint(
    returnType = "V",
    parameters = listOf(
        "Ljava/util/List;",
        "Ljava/util/Map;",
        "Ljava/lang/String;",
        "Lyq0/o;",
        "Lkotlin/jvm/functions/Function1;",
        "Lkotlin/jvm/functions/Function1;",
        "Lb2/k;",
        "Lb2/k;",
        "Landroidx/compose/runtime/a;",
        "I",
    ),
    custom = { method, classDef ->
        classDef.sourceFile == "BottomNavigationContainer.kt" && method.implementation != null
    },
)

/** Matches `RootNavHost`, which selects the app's initial navigation route. */
object RootNavHostFingerprint : Fingerprint(
    returnType = "V",
    parameters = listOf(
        "Lru/vk/store/feature/navigation/startDestination/api/domain/StartDestination;",
        "Z",
        "Landroidx/compose/runtime/a;",
        "I",
    ),
    custom = { method, classDef ->
        classDef.sourceFile == "RootNavHost.kt" && method.implementation != null
    },
)
