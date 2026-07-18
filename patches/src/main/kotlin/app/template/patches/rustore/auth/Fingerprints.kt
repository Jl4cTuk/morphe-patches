package app.template.patches.rustore.auth

import app.morphe.patcher.Fingerprint

/**
 * Matches `AuthSuggestDelegateImpl.ensureAuthSuggestShown()`, the central
 * authorization check that displays the login prompt before an app update.
 */
object AuthSuggestShownFingerprint : Fingerprint(
    definingClass = "Ll61/e;",
    name = "a",
    returnType = "Ljava/lang/Object;",
    parameters = listOf("Lpq0/c;"),
)
