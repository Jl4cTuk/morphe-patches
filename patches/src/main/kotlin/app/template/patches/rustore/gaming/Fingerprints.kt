package app.template.patches.rustore.gaming

import app.morphe.patcher.Fingerprint

/** Matches `MineV2ViewModel.openGameCenter()`. */
object MineV2ViewModelOpenGameCenterFingerprint : Fingerprint(
    definingClass = "Llj1/a9;",
    name = "q0",
    returnType = "V",
    parameters = emptyList(),
)

/** Matches the Game Center button rendered by the V2/V3 Mine screen. */
object GameCenterV2ButtonComposableFingerprint : Fingerprint(
    definingClass = "Loc1/r0;",
    name = "d",
    returnType = "V",
    parameters = listOf(
        "Lkotlin/jvm/functions/Function0;",
        "Landroidx/compose/ui/Modifier;",
        "Loc1/v;",
        "Landroidx/compose/runtime/a;",
        "I",
    ),
)

/** Matches the Game Center statistics card rendered by the classic Mine screen. */
object GameCenterV1ButtonComposableFingerprint : Fingerprint(
    definingClass = "Loc1/t;",
    name = "e",
    returnType = "V",
    parameters = listOf(
        "Lkotlin/jvm/functions/Function0;",
        "Landroidx/compose/ui/Modifier;",
        "Loc1/v;",
        "Landroidx/compose/runtime/a;",
        "I",
    ),
)
