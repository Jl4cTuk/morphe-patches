package app.template.patches.rustore.kaspersky

import app.morphe.patcher.Fingerprint

/**
 * Matches `KasperskyScannerDto.isPeriodicScanEnabled()`, the persisted flag
 * used by the initializer and security settings to control automatic scans.
 */
object KasperskyScannerDtoIsPeriodicScanEnabledFingerprint : Fingerprint(
    definingClass = "Lru/vk/store/feature/kaspersky/impl/data/KasperskyScannerDto;",
    name = "isPeriodicScanEnabled",
    returnType = "Z",
    parameters = emptyList(),
)

/**
 * Matches `KasperskyScannerWorker.Companion.enqueuePeriodic()`, which creates
 * the daily `PeriodicKasperskyScanner` WorkManager task.
 */
object KasperskyScannerWorkerEnqueuePeriodicFingerprint : Fingerprint(
    definingClass =
        "Lru/vk/store/feature/kaspersky/impl/presentation/KasperskyScannerWorker\$a;",
    name = "a",
    returnType = "Ljava/lang/Object;",
    parameters = listOf("Lmb/k0;", "Lpq0/c;"),
)
