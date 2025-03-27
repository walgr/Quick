package com.wpf.tools.quickbluetooth.permisssion

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.content.ContextCompat

object RequestPermissionHelper {

    private val permissions = mutableListOf(
        Manifest.permission.BLUETOOTH,
        Manifest.permission.BLUETOOTH_ADMIN,
        Manifest.permission.ACCESS_FINE_LOCATION
    )

    init {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            permissions.add(Manifest.permission.BLUETOOTH_CONNECT)
            permissions.add(Manifest.permission.BLUETOOTH_SCAN)
        }
    }

    private const val REQUEST_PERMISSIONS_CODE = 6666

    private var doNext: (() -> Unit) = {}
    private var doNoPermissions: ((permissions: Array<String>) -> Unit) = {}

    fun request(context: Activity, doNoPermissions: ((permissions: Array<String>) -> Unit) = {}, doNext: (() -> Unit) = {}) {
        this.doNoPermissions = doNoPermissions
        this.doNext = doNext
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkPermissions(context, permissions).isNotEmpty()) {
                context.requestPermissions(permissions.toTypedArray(), REQUEST_PERMISSIONS_CODE)
            } else {
                doNext()
            }
        } else {
            doNext()
        }
    }

    fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUEST_PERMISSIONS_CODE) {
            val noPermissions =
                permissions.takeIf {
                    return grantResults.mapIndexed { index, it -> it != PackageManager.PERMISSION_GRANTED; index }
                        .forEach {
                            permissions[it]
                        }
                } ?: emptyArray()
            if (noPermissions.isEmpty()) {
                doNext()
            } else {
                doNoPermissions(noPermissions)
            }
        }
    }

    private fun checkPermissions(context: Context, permissions: Collection<String>): List<String> {
        return permissions.filter {
            ContextCompat.checkSelfPermission(
                context,
                it
            ) != PackageManager.PERMISSION_GRANTED
        }
    }
}