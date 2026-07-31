package app.template.patches.ozonbank.warnings

import app.morphe.patcher.Fingerprint

/** Matches the native bridge method that reports VPN state to Ozon Bank web pages. */
object WebViewVpnStateFingerprint : Fingerprint(
    definingClass =
        "Lru/ozon/fintech/features/finwebview/domain/nativebridge/groups/network/" +
            "GetTypeNetworkBridgeInterface;",
    name = "isVpnConnected",
    returnType = "Z",
    parameters = listOf("Landroid/net/ConnectivityManager;"),
)
