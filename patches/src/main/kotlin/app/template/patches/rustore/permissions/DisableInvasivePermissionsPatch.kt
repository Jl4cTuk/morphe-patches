package app.template.patches.rustore.permissions

import app.morphe.patcher.patch.PatchException
import app.morphe.patcher.patch.resourcePatch
import app.template.patches.all.analytics.childrenNamed
import app.template.patches.all.analytics.removeChildren
import app.template.patches.rustore.shared.Constants.COMPATIBILITY_RUSTORE
import java.util.logging.Logger

private val logger = Logger.getLogger("DisableRuStoreInvasivePermissions")

private val removedPermissions = setOf(
    "android.permission.INSTALL_PACKAGES",
    "android.permission.ACCESS_FINE_LOCATION",
    "android.permission.ACCESS_COARSE_LOCATION",
    "com.android.vending.BILLING",
    "android.permission.USB_HOST",
    "android.permission.WRITE_EXTERNAL_STORAGE",
    "com.huawei.appmarket.service.commondata.permission.GET_COMMON_DATA",
)

private val preservedUpdatePermissions = setOf(
    "android.permission.REQUEST_INSTALL_PACKAGES",
    "android.permission.QUERY_ALL_PACKAGES",
    "com.android.permission.GET_INSTALLED_APPS",
    "android.permission.POST_NOTIFICATIONS",
    "android.permission.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS",
    "android.permission.UPDATE_PACKAGES_WITHOUT_USER_ACTION",
    "android.permission.ENFORCE_UPDATE_OWNERSHIP",
    "android.permission.REQUEST_DELETE_PACKAGES",
)

@Suppress("unused")
val disableInvasivePermissionsPatch = resourcePatch(
    name = "Disable invasive permissions",
    description =
        "Removes privileged install, location, storage, billing, USB, and vendor data access.",
    default = true,
) {
    compatibleWith(COMPATIBILITY_RUSTORE)

    execute {
        document("AndroidManifest.xml").use { document ->
            val manifest = document.documentElement
            val permissionNodes = manifest.childrenNamed(
                "uses-permission",
                "uses-permission-sdk-23",
            )
            val sourcePermissions = permissionNodes
                .map { it.getAttribute("android:name") }

            val invalidCounts = removedPermissions.associateWith { permission ->
                sourcePermissions.count { it == permission }
            }.filterValues { count -> count != 1 }
            if (invalidCounts.isNotEmpty()) {
                throw PatchException(
                    "Unexpected invasive permission inventory: $invalidCounts",
                )
            }

            val missingUpdatePermissions = preservedUpdatePermissions - sourcePermissions.toSet()
            if (missingUpdatePermissions.isNotEmpty()) {
                throw PatchException(
                    "Required update permissions are missing: " +
                        missingUpdatePermissions.sorted(),
                )
            }

            val removedNodes = permissionNodes.filter { permission ->
                permission.getAttribute("android:name") in removedPermissions
            }
            manifest.removeChildren(removedNodes)

            val remainingPermissions = manifest.childrenNamed(
                "uses-permission",
                "uses-permission-sdk-23",
            ).mapTo(mutableSetOf()) { it.getAttribute("android:name") }
            val permissionsStillPresent = removedPermissions intersect remainingPermissions
            if (permissionsStillPresent.isNotEmpty()) {
                throw PatchException(
                    "Invasive permissions remain: ${permissionsStillPresent.sorted()}",
                )
            }
            val updatePermissionsRemoved = preservedUpdatePermissions - remainingPermissions
            if (updatePermissionsRemoved.isNotEmpty()) {
                throw PatchException(
                    "Update permissions were removed: ${updatePermissionsRemoved.sorted()}",
                )
            }

            logger.info(
                "Removed ${removedNodes.size} invasive permissions and preserved " +
                    "${preservedUpdatePermissions.size} update permissions",
            )
        }
    }
}
