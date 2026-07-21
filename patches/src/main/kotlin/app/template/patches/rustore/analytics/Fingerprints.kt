package app.template.patches.rustore.analytics

import app.morphe.patcher.Fingerprint

/**
 * Matches `AltCraftAnalyticsImpl.send(String, Map, String, boolean, li2.f)`,
 * the entry point used for all AltCraft events in RuStore 1.105.0.2.
 */
object AltCraftSendFingerprint : Fingerprint(
    definingClass = "Lri2/b;",
    name = "a",
    returnType = "V",
    custom = { method, _ ->
        method.parameterTypes.size == 5 &&
            method.parameterTypes[0] == "Ljava/lang/String;" &&
            method.parameterTypes[1] == "Ljava/util/Map;" &&
            method.parameterTypes[2] == "Ljava/lang/String;" &&
            method.parameterTypes[3] == "Z" &&
            method.implementation != null
    },
)

/** Matches the coroutine implementation of `RadarFlushSnapshotWorker.doWork()`. */
object RadarDoWorkFingerprint : Fingerprint(
    definingClass =
        "Lru/vk/store/lib/analytics/system/radar/presentation/RadarFlushSnapshotWorker;",
    name = "b",
    returnType = "Ljava/lang/Object;",
    custom = { method, _ ->
        method.parameterTypes.size == 1 && method.implementation != null
    },
)
