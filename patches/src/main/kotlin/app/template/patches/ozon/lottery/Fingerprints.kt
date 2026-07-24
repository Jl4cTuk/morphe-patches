package app.template.patches.ozon.lottery

import app.morphe.patcher.Fingerprint

/** Matches `LotteryOnboardingManager.startOnboarding()`, which opens `MorkovskHintDialog`. */
object LotteryStartOnboardingFingerprint : Fingerprint(
    definingClass =
        "Lru/ozon/app/android/regulardraw/onboarding/lottery/LotteryOnboardingManager;",
    name = "startOnboarding",
    returnType = "V",
    parameters = listOf(
        "Lru/ozon/app/android/regulardraw/onboarding/LotteryOnboardingModel;",
        "Z",
        "Lkotlin/jvm/functions/Function1;",
    ),
)
