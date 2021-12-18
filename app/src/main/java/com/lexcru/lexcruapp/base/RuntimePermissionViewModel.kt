package com.lexcru.lexcruapp.base

import android.app.Activity
import android.app.AlertDialog
import android.app.Application
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.text.Html
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import com.lexcru.lexcruapp.R
import com.lexcru.lexcruapp.dimodule.ReliableViewModel


enum class PermissionState {

    GRANTED,
    DENIED
}

abstract class RuntimePermissionViewModel(application: Application):AndroidViewModel(application),ReliableViewModel{


    private var activity: Activity? = null
    private var callback: ((Boolean) -> Unit)? = null

    fun requestPermission(
        activity: Activity,
        permissions: Array<String>,
        callback: (isGranted: Boolean) -> Unit
    ) {
        this.activity = activity
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkPermissions(activity, permissions)) {
                callback(true)
            } else {
                this.callback = callback
                activity.requestPermissions(permissions, Integer.MAX_VALUE)
            }
        } else callback(true)
    }

    fun checkPermissions(context: Context, permissions: Array<String>): Boolean {
        var granted = true
        for (permission in permissions) {
            if (ContextCompat.checkSelfPermission(
                    context,
                    permission
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                granted = false
                break
            }
        }
        return granted
    }

    fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == Integer.MAX_VALUE) {
            var granted = true
            for (element in grantResults) {
                if (element != PackageManager.PERMISSION_GRANTED) {
                    granted = false
                    break
                }
            }
            if (granted)
                callback?.invoke(true)
            else onDenied()
        }
    }

    private fun onDenied() {
        callback?.invoke(false)
        if (activity != null)
            setAlertMessage(activity!!)
    }

    private fun setAlertMessage(activity: Activity) {
        val adb = AlertDialog.Builder(activity, android.R.style.Theme_Material_Dialog_Alert)

        adb.setTitle(activity.getString(R.string.app_name))
        val msg = "<p>Dear User, </p>" +
                "<p>Seems like you have <b>\"Denied\"</b> the minimum requirement permission to access more features of application.</p>" +
                "<p>You must have to <b>\"Allow\"</b> all permission. We will not share your data with anyone else.</p>" +
                "<p>Do you want to enable all requirement permission ?</p>" +
                "<p>Go To : Settings >> App > " + activity.getString(R.string.app_name) + " Permission : Allow ALL</p>"

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            adb.setMessage(Html.fromHtml(msg, Html.FROM_HTML_MODE_LEGACY))
        else
            adb.setMessage(Html.fromHtml(msg))
        adb.setPositiveButton("Allow All") { dialog, _ ->
            dialog.dismiss()
            val intent = Intent()
            intent.action = Settings.ACTION_LOCATION_SOURCE_SETTINGS
            val uri = Uri.fromParts("package", activity.packageName, null)
            //intent.data = uri
            activity.startActivity(intent)
        }

        adb.setNegativeButton("Remind Me Later") { dialog, _ -> dialog.dismiss() }

        /*try {
            ThreadUtils.runOnUiThread {
                val alertDialog: AlertDialog = adb.create()

                alertDialog.setOnShowListener {
                    val negButton: Button = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE)

                    val params: LinearLayout.LayoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    )
                    params.setMargins(0, 0, 50, 0)
                    negButton.layoutParams = params
                }

                if (!activity.isFinishing)
                    alertDialog.show()
            }
        } catch (ex: Exception) {
            ex.stackTrace
        }*/
    }

}