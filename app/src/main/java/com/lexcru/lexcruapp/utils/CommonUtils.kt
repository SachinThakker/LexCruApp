/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2019 https://www.spaceotechnologies.com
 *
 * Permissions is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without
 * limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the
 * Software, and to permit persons to whom the Software is furnished to do so, subject to the following
 * conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions
 * of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
 * PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT
 * OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 *
 */

package com.lexcru.lexcruapp.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.Settings
import android.text.Html
import android.text.InputFilter
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.method.HideReturnsTransformationMethod
import android.text.method.LinkMovementMethod
import android.util.DisplayMetrics
import android.view.MotionEvent
import android.view.View
import android.view.ViewTreeObserver
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.VisibleForTesting
import androidx.appcompat.app.AlertDialog
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.FileProvider
import com.lexcru.lexcruapp.BuildConfig
import com.lexcru.lexcruapp.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.google.android.material.snackbar.Snackbar
import java.io.File
import java.io.IOException
import java.net.NetworkInterface
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import java.util.regex.Matcher
import java.util.regex.Pattern
import kotlin.math.roundToInt

//commit
object CommonUtils {

    /**
     * old method but working perfactly (https://stackoverflow.com/questions/3407256/height-of-status-bar-in-android)
     */
    fun Activity.getStatusBarHeight(): Int {
        var result = 0
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = resources.getDimensionPixelSize(resourceId)
        }
        return result
    }

    fun displayCount(count: Int): String {
        var numberString: String = "";
        if (Math.abs(count / 1000000) >= 1) {
            numberString = (count / 1000000).toString() + "m";
        } else if (Math.abs(count / 1000) >= 1) {
            numberString = (count / 1000).toString() + "k";
        } else {
            numberString = count.toString();
        }

        return numberString
    }

    /*fun getThirdSizeOfScreen(context: Context): Double {

        val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = wm.defaultDisplay
        val metrics = DisplayMetrics()
        display.getMetrics(metrics)
        val height =
            metrics.heightPixels - dpToPx(
                context,
                context.resources.getDimension(R.dimen.dimen_48dp).toInt()
            ).toDouble()

        return height
    }*/

    fun Context.checkInternetConnected(): Boolean {
        var isConnected = false
        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            cm?.run {
                cm.getNetworkCapabilities(cm.activeNetwork)?.run {
                    isConnected = when {
                        hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                        hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                        hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                        else -> false
                    }
                }
            }
        } else {
            cm?.run {
                @Suppress("DEPRECATION")
                cm.activeNetworkInfo?.run {
                    if (type == ConnectivityManager.TYPE_WIFI) {
                        isConnected = true
                    } else if (type == ConnectivityManager.TYPE_MOBILE) {
                        isConnected = true
                    }
                }
            }
        }
        return isConnected
    }


    fun Context.isInternetConnected(isShowDialog: Boolean = false): Boolean {
        var isConnected = false
        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            cm?.run {
                cm.getNetworkCapabilities(cm.activeNetwork)?.run {
                    isConnected = when {
                        hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                        hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                        hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                        else -> false
                    }
                }
            }
        } else {
            cm?.run {
                @Suppress("DEPRECATION")
                cm.activeNetworkInfo?.run {
                    if (type == ConnectivityManager.TYPE_WIFI) {
                        isConnected = true
                    } else if (type == ConnectivityManager.TYPE_MOBILE) {
                        isConnected = true
                    }
                }
            }
        }
        if (!isConnected && isShowDialog) {
            showOkDialog(
                this, getString(R.string.app_name),
                "Internet not available"
            )

        }
        return isConnected
    }

    /*fun showTokenExpiredDialog(message: String, activity: Activity, prefs: SharedPreferences) {
        showOkDialogWithCallback(
            context = activity,
            title = activity.getString(R.string.lbl_session_expired_msg),
            message = message
        ) {
            //clearDataAndLogout(prefs, activity)
            //logOutUser(prefs, activity)
        }
    }*/

    fun showDialogWithPositiveCallback(
        context: Context,
        title: String = context.getString(R.string.app_name),
        message: String?,
        buttonTextPositive: String = context.resources.getString(android.R.string.yes),
        buttonTextNegative: String = context.resources.getString(android.R.string.no),
        callback: () -> Unit
    ) {
        if (null == message) return
        val adb = AlertDialog.Builder(context)
        adb.setTitle(title)
        adb.setMessage(message)
        adb.setCancelable(false)

        adb.setPositiveButton(buttonTextPositive) { dialog, which ->
            dialog.dismiss()
            callback()
        }
        adb.show()
    }

    private fun clearDataAndLogout(prefs: SharedPreferences, activity: Activity) {

//        prefs[AUTH_KEY] = ""
//        prefs[IS_LOGIN] = false
//        activity.startActivity(Intent(activity, LoginSelectionActivity::class.java))
//        activity.finishAffinity()
    }


    fun showOkDialog(
        context: Context,
        title: String = context.getString(R.string.app_name),
        message: String,
        isFinish: Boolean = false
    ) {
        val alertDialog = AlertDialog.Builder(context)
        alertDialog.setTitle(title)
        alertDialog.setMessage(message)
        alertDialog.setPositiveButton("Ok") { dialog, _ ->
            if (isFinish) {
                val activity = context as Activity
                activity.finish()
            } else {
                dialog.dismiss()
            }

        }
        alertDialog.show()
    }

    fun showOkDialogWithCallback(
        context: Context,
        title: String = context.getString(R.string.app_name),
        message: String,
        isFinish: Boolean = false,
        callback: () -> Unit
    ) {
        val alertDialog = AlertDialog.Builder(context)
        alertDialog.setTitle(title)
        alertDialog.setMessage(message)
        alertDialog.setPositiveButton("Ok") { dialog, _ ->
            if (isFinish) {
                val activity = context as Activity
                activity.finish()
            } else {
                dialog.dismiss()
            }
            callback()

        }
        alertDialog.show()
    }

    fun getAppVersion(context: Context): Int {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P)
                return context.packageManager.getPackageInfo(context.packageName, 0)
                    .longVersionCode.toInt()
            else
                return context.packageManager.getPackageInfo(context.packageName, 0).versionCode

        } catch (e: PackageManager.NameNotFoundException) {
            // should never happen
            throw RuntimeException("Could not get package name: $e")
        }
    }

    fun getDeviceId(mContext: Context): String {
        return Settings.Secure.getString(mContext.contentResolver, Settings.Secure.ANDROID_ID)
    }

    val deviceModel: String
        get() {
            val manufacturer = Build.MANUFACTURER
            val model = Build.MODEL
            return if (model.startsWith(manufacturer)) {
                capitalize(model)
            } else {
                capitalize(manufacturer) + " " + model
            }
        }

    private fun capitalize(deviceModel: String): String {
        return deviceModel.substring(0, 1).toUpperCase(Locale.getDefault()) + deviceModel.substring(
            1
        )
    }

    val deviceOSVersion = Build.VERSION.SDK_INT.toString()


    //--- get IMEI number of Device
    /*@RequiresPermission(Manifest.permission.READ_PHONE_STATE)
    fun getIMEINumber(mContext: Context): String? {
        val telephonyManager =
            mContext.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        return if (ActivityCompat.checkSelfPermission(
                mContext,
                Manifest.permission.READ_PHONE_STATE
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            null
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                telephonyManager.imei
            } else {
                telephonyManager.deviceId
            }
        }

    }*/

    //--- get ip address of mobile
    val ipAddress: String?
        @Throws(Exception::class)
        get() {
            val en = NetworkInterface.getNetworkInterfaces()
            while (en.hasMoreElements()) {
                val intf = en.nextElement()
                val enumIpAddr = intf.inetAddresses
                while (enumIpAddr.hasMoreElements()) {
                    val inetAddress = enumIpAddr.nextElement()
                    if (!inetAddress.isLoopbackAddress) {
                        return inetAddress.hostAddress
                    }
                }
            }
            return null
        }


    fun showSettingsSnackBar(
        rootLayout: CoordinatorLayout,
        settingsText: String = "Settings",
        message: String
    ) {
        val snackbar = Snackbar
            .make(rootLayout, message, Snackbar.LENGTH_LONG)
            .setAction("Settings") {
                val intent = Intent()
                intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                val uri = Uri.fromParts("package", rootLayout.context.packageName, null)
                intent.data = uri
                rootLayout.context.startActivity(intent)
            }
        snackbar.show()
    }


    private fun showSettingsDialog(
        mContext: Context,
        settingsText: String? = "Settings",
        cancelText: String? = "Cancel",
        title: String,
        message: String
    ) {
        val alertSettings = AlertDialog.Builder(mContext)
        alertSettings.setTitle(title)
        alertSettings.setMessage(message)
        alertSettings.setPositiveButton("Settings") { _, _ ->
            val intent = Intent()
            intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
            val uri = Uri.fromParts("package", mContext.packageName, null)
            intent.data = uri
            mContext.startActivity(intent)
        }
        alertSettings.setNegativeButton("Cancel") { dialog, _ -> dialog.dismiss() }
        alertSettings.show()
    }

    fun dpToPx(context: Context, dp: Int) = (dp * context.getPixelScaleFactor()).roundToInt()

    fun pxToDp(context: Context, px: Int) = (px / context.getPixelScaleFactor()).roundToInt()

    private fun Context.getPixelScaleFactor(): Float {
        val displayMetrics = resources.displayMetrics
        return displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT
    }


    fun Activity.hideSoftKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
    }

    fun Activity.showSoftKeyboard(mView: View) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInputFromWindow(
            mView.applicationWindowToken,
            InputMethodManager.SHOW_FORCED,
            0
        )

    }

    fun Activity.showToast(message: String) =
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

    fun Activity.showLongToast(message: String) =
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()


    fun showSnakbar(rootView: View, mMessage: String) =
        Snackbar.make(rootView, mMessage, Snackbar.LENGTH_SHORT).show()

    fun showLongSnakbar(rootView: View, mMessage: String) =
        Snackbar.make(rootView, mMessage, Snackbar.LENGTH_LONG).show()

    fun isValidEmail(editText: EditText): Boolean {
        val pattern = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}$")
        val matcher = pattern.matcher(editText.text.toString())
        return matcher.matches()
    }

    @VisibleForTesting
    fun String.isValidEmail(): Boolean {
        //val EMAIL_REGEX = "^[\\w-\\+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$"
        return Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}$").matcher(this)
            .matches()
    }

    @VisibleForTesting
    fun String.isValidPhoneNumber(): Boolean {
        return Pattern.compile("^\\+(?:[0-9] ?){6,14}[0-9]\$").matcher(this)
            .matches()
    }

    fun isValidPasswordLength(editText: String): Boolean {
        return editText.length in 8..15
    }

    fun isValidPassword(value: String): Boolean {

        //        val pattern = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@!#\$%^&*?\"></])(?=\\S+$).{4,}$" )
        val pattern =
            Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$&*_])(?=\\S+$).{4,}$")

        val matcher = pattern.matcher(value)
        return matcher.matches()

    }

    fun isWebUrlIsValid(url: String): Boolean {
        val webUrlPattern = Pattern.compile(
            StringBuilder().append("((?:(http|https|Http|Https|rtsp|Rtsp):")
                .append("\\/\\/(?:(?:[a-zA-Z0-9\\$\\-\\_\\.\\+\\!\\*\\'\\(\\)")
                .append("\\,\\;\\?\\&\\=]|(?:\\%[a-fA-F0-9]{2})){1,64}(?:\\:(?:[a-zA-Z0-9\\$\\-\\_")
                .append("\\.\\+\\!\\*\\'\\(\\)\\,\\;\\?\\&\\=]|(?:\\%[a-fA-F0-9]{2})){1,25})?\\@)?)?")
                .append("((?:(?:[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}\\.)+")   // named host
                .append("(?:")   // plus top level domain
                .append("(?:aero|arpa|asia|a[cdefgilmnoqrstuwxz])")
                .append("|(?:biz|b[abdefghijmnorstvwyz])")
                .append("|(?:cat|com|coop|c[acdfghiklmnoruvxyz])").append("|d[ejkmoz]")
                .append("|(?:edu|e[cegrstu])").append("|f[ijkmor]")
                .append("|(?:gov|g[abdefghilmnpqrstuwy])").append("|h[kmnrtu]")
                .append("|(?:info|int|i[delmnoqrst])").append("|(?:jobs|j[emop])")
                .append("|k[eghimnrwyz]").append("|l[abcikrstuvy]")
                .append("|(?:mil|mobi|museum|m[acdghklmnopqrstuvwxyz])")
                .append("|(?:name|net|n[acefgilopruz])").append("|(?:org|om)")
                .append("|(?:pro|p[aefghklmnrstwy])").append("|qa").append("|r[eouw]")
                .append("|s[abcdeghijklmnortuvyz]").append("|(?:tel|travel|t[cdfghjklmnoprtvwz])")
                .append("|u[agkmsyz]").append("|v[aceginu]").append("|w[fs]").append("|y[etu]")
                .append("|z[amw]))").append("|(?:(?:25[0-5]|2[0-4]") // or ip addressDataItems
                .append("[0-9]|[0-1][0-9]{2}|[1-9][0-9]|[1-9])\\.(?:25[0-5]|2[0-4][0-9]")
                .append("|[0-1][0-9]{2}|[1-9][0-9]|[1-9]|0)\\.(?:25[0-5]|2[0-4][0-9]|[0-1]")
                .append("[0-9]{2}|[1-9][0-9]|[1-9]|0)\\.(?:25[0-5]|2[0-4][0-9]|[0-1][0-9]{2}")
                .append("|[1-9][0-9]|[0-9])))")
                .append("(?:\\:\\d{1,5})?)") // plus option port number
                .append("(\\/(?:(?:[a-zA-Z0-9\\;\\/\\?\\:\\@\\&\\=\\#\\~")  // plus option query params
                .append("\\-\\.\\+\\!\\*\\'\\(\\)\\,\\_])|(?:\\%[a-fA-F0-9]{2}))*)?")
                .append("(?:\\b|$)").toString()
        )

        val m = webUrlPattern.matcher(url) as Matcher
        return m.find()
    }

    fun hrToMillis(time: String): Long {
        val time = time.split(":")
        val hour = time[0].toInt()
        val min = time[1].toInt()
        val seconds: Int = 3600 * hour + 60 * min
        return TimeUnit.SECONDS.toMillis(seconds.toLong())
    }

    fun getPriceAmountInputFilter(): InputFilter {
        return object : InputFilter {
            val maxDigitsBeforeDecimalPoint = 4
            val maxDigitsAfterDecimalPoint = 2


            val mPattern = Pattern.compile("[0-9]*+((\\.[0-9]?)?)||(\\.)?")
            override fun filter(
                source: CharSequence,
                start: Int,
                end: Int,
                dest: Spanned,
                dstart: Int,
                dend: Int
            ): CharSequence? {
                val matcher: Matcher = mPattern.matcher(dest)
                return if (!matcher.matches()) "" else null

            }
        }
    }

    fun getFileNameFromURL(fileURL: String): String {

        val index: Int = fileURL.lastIndexOf('/')
        val fileName = fileURL.subSequence(index + 1, fileURL.length)

        return fileName.toString()
    }

    fun getFileFromURL(url: String, context: Context): File? {

        val fileName = getFileNameFromURL(url)

        val filePath = File(
            context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).toString() + "/" + fileName
        )
        if (filePath.exists())
            return filePath
        else
            return null
    }

    fun getLocalFilePathFromURL(url: String, context: Context): String? {

        val fileName = getFileNameFromURL(url)

        /*val filePath =
            Environment.getExternalStorageDirectory().toString() + "/" + context.getResources()
                .getString(R.string.img_path) + "/" + fileName*/

        val filePath =
            context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).toString() + "/" + fileName

        return filePath

    }

    fun getFileFromStorage(fileName: String, context: Context): File? {

        /*val filePath = File(
            Environment.getExternalStorageDirectory().toString() + "/" + context.getResources()
                .getString(R.string.img_path) + "/" + fileName
        )*/
        val filePath = File(

            context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).toString() + "/" + fileName

        )

        if (filePath.exists())
            return filePath
        else
            return null
    }

    /*fun shareData(context: Context, imgUrl: String, text: String, subject: String){

        Glide.with(context)
            .asBitmap()
            .load(imgUrl)
            .into(object : CustomTarget<Bitmap>(100,100){
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {

                    val shareIntent = Intent()
                    shareIntent.action = Intent.ACTION_SEND
                    shareIntent.type = "image/*"
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, subject)
                    shareIntent.putExtra(Intent.EXTRA_TEXT, text)
        shareIntent.putExtra(Intent.EXTRA_STREAM, getLocalBitmapUri(resource))
//        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                    context.startActivity(Intent.createChooser(shareIntent, "send"))

                }
                override fun onLoadCleared(placeholder: Drawable?) {
                    // this is called when imageView is cleared on lifecycle call or for
                    // some other reason.
                    // if you are referencing the bitmap somewhere else too other than this imageView
                    // clear it here as you can no longer have the bitmap
                }
            })

        *//*val imageUri = Uri.parse(
            "android.resource://" + context.getPackageName()
                .toString() + "/drawable/" + "ic_launcher"
        )*//*

    }*/

    /*fun getLocalBitmapUri(context: Context, bmp: Bitmap): Uri? {
        var bmpUri: Uri? = null
        try {

            val folder = File(
                context.getExternalFilesDir(Environment.DIRECTORY_PICTURES).toString() + "/" + context.getResources()
                    .getString(R.string.img_path)
            )

            val file = File(
                folder.absolutePath + "/share_image_" + System.currentTimeMillis() + ".png"
            )

            if (!folder.exists()) {
                folder.mkdirs()
            }
            if (!file.exists()) {
                file.createNewFile();
            }

            *//*val file = File(
                context.getExternalFilesDir(Environment.DIRECTORY_PICTURES).toString() + "/" + context.getResources()
                    .getString(R.string.img_path) + "/" ,
                "share_image_" + System.currentTimeMillis() + ".png"
            )*//*
            val out = FileOutputStream(file)
            bmp.compress(Bitmap.CompressFormat.PNG, 90, out)
            out.close()
            bmpUri = Uri.fromFile(file)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return bmpUri
    }*/

     */


    fun shareData(imgUrl: String, text: String, subject: String, context: Context) {

        Glide.with(context)
            .asBitmap()
            .load(imgUrl)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(object : CustomTarget<Bitmap>(800, 800) {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {

                    val shareIntent = Intent()
                    shareIntent.action = Intent.ACTION_SEND
                    shareIntent.type = "image/*"
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, subject)
                    shareIntent.putExtra(Intent.EXTRA_TEXT, text)
                    shareIntent.putExtra(Intent.EXTRA_STREAM, getLocalBitmapUri(context, imgUrl))
                    shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                    context.startActivity(Intent.createChooser(shareIntent, "send"))

                }

                override fun onLoadCleared(placeholder: Drawable?) {
                    // this is called when imageView is cleared on lifecycle call or for
                    // some other reason.
                    // if you are referencing the bitmap somewhere else too other than this imageView
                    // clear it here as you can no longer have the bitmap
                }
            })

    }


    fun getLocalBitmapUri(context: Context, imgURL: String): Uri? {

        var bmpUri: Uri? = null
        try {

            var folder = getFileFromURL(imgURL, context)

            if (folder!!.exists()) {

                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
                    return FileProvider.getUriForFile(
                        context,
                        BuildConfig.APPLICATION_ID + ".file_provider",
                        folder!!
                    )
                } else {
                    return Uri.fromFile(folder)
                }
            }

            /*val folder = File(

                getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).toString()
            )
            if (!folder.exists()) folder.mkdirs()

            val file = File(
                folder ,
                "share_image_" + System.currentTimeMillis() + ".png"
            )

            file.createNewFile()

            val out = FileOutputStream(file)
            bmp.compress(Bitmap.CompressFormat.PNG, 90, out)
            out.close()
//            bmpUri = Uri.fromFile(file)

            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
                return FileProvider.getUriForFile(
                    this,
                    BuildConfig.APPLICATION_ID + ".file_provider",
                    file!!
                )
            } else {
                return Uri.fromFile(file)
            }*/


        } catch (e: IOException) {
            e.printStackTrace()
        }
        return bmpUri
    }

    fun getDateddMMMyyyyFormat(date: String): String {

        try {
            val inputFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd")
            val outputFormat: DateFormat = SimpleDateFormat("dd-MMM-yyyy")
//        val inputDateStr = "2013-06-24"
            val date: Date = inputFormat.parse(date)
            return outputFormat.format(date)
        } catch (e: ParseException) {
            e.printStackTrace()
            return date
        }
    }

    fun getDateddMMMyyyyFormatWithoutDash(date: String): String {

        val inputFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd")
        val outputFormat: DateFormat = SimpleDateFormat("dd MMM yyyy")
//        val inputDateStr = "2013-06-24"
        val date: Date = inputFormat.parse(date)
        return outputFormat.format(date)
    }


    private fun addClickablePartTextViewResizable(
        context: Context,
        strSpanned: Spanned, tv: TextView,
        maxLine: Int, spanableText: String, viewMore: Boolean
    ): SpannableStringBuilder? {
        val str = strSpanned.toString()
        val ssb = SpannableStringBuilder(strSpanned)
        if (str.contains(spanableText)) {
            ssb.setSpan(object : com.lexcru.lexcruapp.utils.MySpannable(context, false) {
                override fun onClick(widget: View) {
                    if (viewMore) {
                        tv.layoutParams = tv.layoutParams
                        tv.setText(tv.tag.toString(), TextView.BufferType.SPANNABLE)
                        tv.invalidate()
                        makeTextViewResizable(context, tv, -1, "See Less", false)
                    } else {
                        tv.layoutParams = tv.layoutParams
                        tv.setText(tv.tag.toString(), TextView.BufferType.SPANNABLE)
                        tv.invalidate()
                        makeTextViewResizable(context, tv, READ_MORE_LINES, ".. Read More", true)
                    }
                }
            }, str.indexOf(spanableText), str.indexOf(spanableText) + spanableText.length, 0)
        }
        return ssb
    }

    fun makeTextViewResizable(
        context: Context,
        tv: TextView,
        maxLine: Int,
        expandText: String,
        viewMore: Boolean
    ) {
        if (tv.tag == null) {
            tv.tag = tv.text
        }
        val vto = tv.viewTreeObserver
        vto.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                val obs = tv.viewTreeObserver
                obs.removeGlobalOnLayoutListener(this)
                if (maxLine == 0) {
                    val lineEndIndex = tv.layout.getLineEnd(0)
                    val text = tv.text.subSequence(0, lineEndIndex - expandText.length + 1)
                        .toString() + " " + expandText
                    tv.text = text
                    tv.movementMethod = LinkMovementMethod.getInstance()
                    tv.setText(
                        addClickablePartTextViewResizable(
                            context,
                            Html.fromHtml(tv.text.toString()), tv, maxLine, expandText,
                            viewMore
                        ), TextView.BufferType.SPANNABLE
                    )
                } else if (maxLine > 0 && tv.lineCount >= maxLine) {

                    /*tv.viewTreeObserver.addOnGlobalLayoutListener {
                        val lineEndIndex = tv.layout.getLineEnd(maxLine - 1)
                        val text = tv.text.subSequence(0, lineEndIndex - expandText.length + 1)
                            .toString() + " " + expandText
                        tv.text = text
                        tv.movementMethod = LinkMovementMethod.getInstance()
                        tv.setText(
                            addClickablePartTextViewResizable(
                                context,
                                Html.fromHtml(tv.text.toString()), tv, maxLine, expandText,
                                viewMore
                            ), TextView.BufferType.SPANNABLE
                        )
                    }*/
                    val lineEndIndex = tv.layout.getLineEnd(maxLine - 1)
                    val text = tv.text.subSequence(0, lineEndIndex - expandText.length + 1)
                        .toString() + " " + expandText
                    tv.text = text
                    tv.movementMethod = LinkMovementMethod.getInstance()
                    tv.setText(
                        addClickablePartTextViewResizable(
                            context,
                            Html.fromHtml(tv.text.toString()), tv, maxLine, expandText,
                            viewMore
                        ), TextView.BufferType.SPANNABLE
                    )
                } else {
                    val lineEndIndex = tv.layout.getLineEnd(tv.layout.lineCount - 1)
                    val text = tv.text.subSequence(0, lineEndIndex).toString() + " " + expandText
                    tv.text = text
                    tv.movementMethod = LinkMovementMethod.getInstance()
                    tv.setText(
                        addClickablePartTextViewResizable(
                            context,
                            Html.fromHtml(tv.text.toString()), tv, lineEndIndex, expandText,
                            viewMore
                        ), TextView.BufferType.SPANNABLE
                    )
                }
            }
        })
    }

    fun editTextTouchEvents(context: Context, editText: EditText) {

        var isPasswordShow = false

        editText.setOnTouchListener(View.OnTouchListener { v, event ->
            val DRAWABLE_LEFT = 0
            val DRAWABLE_TOP = 1
            val DRAWABLE_RIGHT = 2
            val DRAWABLE_BOTTOM = 3
            if (event.action == MotionEvent.ACTION_UP) {
                if (event.rawX >= editText.getRight() - editText.getCompoundDrawables()
                        .get(DRAWABLE_RIGHT).getBounds().width()
                ) {
                    // your action here
                    if (isPasswordShow) {
                        isPasswordShow = false
                        editText.transformationMethod = HideReturnsTransformationMethod()
                        val icPassword: Drawable =
                            context.getResources().getDrawable(R.drawable.ic_password_show)
                        editText.setCompoundDrawablesWithIntrinsicBounds(
                            null,
                            null,
                            icPassword,
                            null
                        )

                    } else {
                        isPasswordShow = true
                        editText.transformationMethod =
                            com.lexcru.lexcruapp.utils.AsteriskPasswordTransformationMethod()
                        val icPassword: Drawable =
                            context.getResources().getDrawable(R.drawable.ic_password_not_show)
                        editText.setCompoundDrawablesWithIntrinsicBounds(
                            null,
                            null,
                            icPassword,
                            null
                        )
                    }
                    return@OnTouchListener true
                }
            }
            false
        })
    }
}
