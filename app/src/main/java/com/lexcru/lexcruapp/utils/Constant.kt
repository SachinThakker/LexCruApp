package com.lexcru.lexcruapp.utils

import android.app.Activity
import android.app.ProgressDialog
import android.content.pm.ActivityInfo
import android.os.Build

object Constant {
    const val IS_APPUPDATE = "isappupdate"
    const val TODAY_DATE = "todayDate"
    const val TODAY_DATE_VIDEO = "todayDateVideo"
    const val TODAY_CONTENT_VIDEO = "todayContentVideo"
    const val BALANCE_POINTS = "balancePoint"
    var AS_TECHNICIAN = "technician"
    var AS_DEALER = "dealer"
    var AS_RETAILER = "retailer"
    const val FOLDER_NAME_TEMP = "/Lexcru/TEMP"
    private const val GST_PATTERN =
        "[0-9]{2}[a-zA-Z]{5}[0-9]{4}[a-zA-Z]{1}[1-9A-Za-z]{1}[0-9a-zA-Z]{2}"
    private var progressDialog: ProgressDialog? = null
    var FCM_TOKEN = "fcm_token"
//    fun ShowAlertDialog(context: Context?, title: String?, Msg: String?) {
//        val npDialog = Dialog(context!!, R.style.MyDialog)
//        npDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
//        npDialog.setContentView(R.layout.dialog_alert)
//        npDialog.setCancelable(true)
//        npDialog.window!!.setLayout(
//            LinearLayout.LayoutParams.FILL_PARENT,
//            LinearLayout.LayoutParams.FILL_PARENT
//        )
//        npDialog.window!!.setGravity(Gravity.CENTER)
//        //  npDialog.getWindow().getDecorView().setBackgroundResource(android.R.color.transparent);
//        npDialog.window!!.setDimAmount(0.0f)
//        npDialog.window!!.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
//        val tvalert_title: TextView = npDialog.findViewById<View>(R.id.tvalert_title) as TextView
//        tvalert_title.setText(title)
//        val tv_msg: TextView = npDialog.findViewById<View>(R.id.tvalert_msg) as TextView
//        tv_msg.setText(Msg)
//        val tv_ok: TextView = npDialog.findViewById<View>(R.id.tv_OK) as TextView
//        tv_ok.setText("ok")
//        tv_ok.setOnClickListener(View.OnClickListener { if (npDialog != null && npDialog.isShowing) npDialog.dismiss() })
//        npDialog.show()
//    }
//
//    fun getVideoId(videoUrl: String): String? {
//        val reg =
//            "(?:youtube(?:-nocookie)?\\.com\\/(?:[^\\/\\n\\s]+\\/\\S+\\/|(?:v|e(?:mbed)?)\\/|\\S*?[?&]v=)|youtu\\.be\\/)([a-zA-Z0-9_-]{11})"
//        val pattern = Pattern.compile(reg, Pattern.CASE_INSENSITIVE)
//        val matcher = pattern.matcher(videoUrl)
//        return if (matcher.find()) matcher.group(1) else null
//    }
//
//    fun encodeTobase64PNG(image: Bitmap): String {
//        val immagex: Bitmap = image
//        val baos = ByteArrayOutputStream()
//        immagex.compress(Bitmap.CompressFormat.PNG, 100, baos)
//        val b = baos.toByteArray()
//        return Base64.encodeToString(b, Base64.DEFAULT)
//    }
//
//    fun strdatem(strdate: String?): String {
//        var strdatemain = ""
//        val format1 = SimpleDateFormat("dd-MM-yyyy")
//        val format2 = SimpleDateFormat("yyyy-MM-dd")
//        var date: Date? = null
//        try {
//            date = format1.parse(strdate)
//            strdatemain = format2.format(date)
//        } catch (e: ParseException) {
//            e.printStackTrace()
//        }
//        return strdatemain
//    }
//
//    fun encodeTobase64JPG(image: Bitmap): String {
//        val immagex: Bitmap = image
//        val baos = ByteArrayOutputStream()
//        immagex.compress(Bitmap.CompressFormat.JPEG, 100, baos)
//        val b = baos.toByteArray()
//        return Base64.encodeToString(b, Base64.DEFAULT)
//    }
//
//    fun GST_validation(context: Context?, gst_number: String) {
//        val pattern = Pattern.compile(GST_PATTERN)
//        val matcher = pattern.matcher(gst_number)
//        if (matcher.find()) {
//            val gst_number1 = gst_number.substring(matcher.start(), matcher.end())
//        } else {
//            ShowAlertDialog(context, "Alert!", "Invalid GST Number")
//        }
//    }
//
//    fun getNullAsEmptyString(jsonElement: JsonElement): String {
//        return if (jsonElement.isJsonNull()) "" else jsonElement.getAsString()
//    }
//
//    fun Validatation(context: Context?, s: String) {
//        if (s.length > 0) {
//            val first = s[0]
//            if (first == '0') {
//                // setEnabledfalse();
//                ShowAlertDialog(context, "Alert!", "0 as first char of number not accepted.")
//            } else if (s.length > 10) {
//                // setEnabledfalse();
//                ShowAlertDialog(context, "Alert!", "Please Enter  10 digit Value..")
//            } else if (s == "+") {
//                // setEnabledfalse();
//                ShowAlertDialog(context, "Alert!", " + sign not accepted..")
//            } else if (s == "+91") {
//                //  setEnabledfalse();
//                ShowAlertDialog(context, "Alert!", " +91 not accepted..")
//            }
//        }
//    }
//
//    fun ShowProgressDialog(context: Context?) {
//        progressDialog = ProgressDialog(context)
//        progressDialog.setMessage("Please Wait...")
//        progressDialog.show()
//    }
//
//    fun HideProgressDialog(context: Context?) {
//        if (progressDialog != null && progressDialog.isShowing()) progressDialog.dismiss()
//    }

    fun Orientation(context: Activity) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            context.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
        }
    }

//    fun isDeviceOnline(context: Context): Boolean {
//        val connMgr: ConnectivityManager =
//            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
//        val networkInfo: NetworkInfo = connMgr.getActiveNetworkInfo()
//        return networkInfo != null && networkInfo.isConnected()
//    }
//
//    fun encodeTobase64(image: Bitmap): String {
//        val immagex: Bitmap = image
//        val baos = ByteArrayOutputStream()
//        immagex.compress(Bitmap.CompressFormat.PNG, 100, baos)
//        val b = baos.toByteArray()
//        return Base64.encodeToString(b, Base64.DEFAULT)
//    }
//
//    fun strdate2(strdate: String?): String {
//        var strdatemain = ""
//        val format1 = SimpleDateFormat("yyyy-MM-dd")
//        val format2 = SimpleDateFormat("dd-MM-yyyy")
//        var date: Date? = null
//        try {
//            date = format1.parse(strdate)
//            strdatemain = format2.format(date)
//        } catch (e: ParseException) {
//            e.printStackTrace()
//        }
//        return strdatemain
//    }
//
//    fun strdate12(strdate: String?): String {
//        var strdatemain = ""
//        val format1 = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
//        val format2 = SimpleDateFormat("dd-MM-yyyy hh:mm a")
//        var date: Date? = null
//        try {
//            date = format1.parse(strdate)
//            strdatemain = format2.format(date)
//        } catch (e: ParseException) {
//            e.printStackTrace()
//        }
//        return strdatemain
//    }
//
//    fun strdate(strdate: String?): String {
//        var strdatemain = ""
//        val format1 = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
//        val format2 = SimpleDateFormat("dd-MM-yyyy HH:mm:ss")
//        var date: Date? = null
//        try {
//            date = format1.parse(strdate)
//            strdatemain = format2.format(date)
//        } catch (e: ParseException) {
//            e.printStackTrace()
//        }
//        return strdatemain
//    }
//
//    @RequiresApi(api = Build.VERSION_CODES.ICE_CREAM_SANDWICH)
//    fun ShowExitDialog(context: Context, title: String?, Msg: String?) {
//        val npDialog = Dialog(context, R.style.MyDialog)
//        npDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
//        npDialog.setContentView(R.layout.dialog_logout)
//        npDialog.window!!.setLayout(
//            LinearLayout.LayoutParams.FILL_PARENT,
//            LinearLayout.LayoutParams.FILL_PARENT
//        )
//        npDialog.window!!.setGravity(Gravity.CENTER)
//        //  npDialog.getWindow().getDecorView().setBackgroundResource(android.R.color.transparent);
//        npDialog.window!!.setDimAmount(0.0f)
//        npDialog.window!!.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
//        val tvalert_title: TextView = npDialog.findViewById<View>(R.id.tvalert_title) as TextView
//        tvalert_title.setText(title)
//        val tv_msg: TextView = npDialog.findViewById<View>(R.id.tvalert_msg) as TextView
//        tv_msg.setText(Msg)
//        val tv_yes: TextView = npDialog.findViewById<View>(R.id.tv_yes) as TextView
//        val tv_No: TextView = npDialog.findViewById<View>(R.id.tv_No) as TextView
//        tv_yes.setOnClickListener(View.OnClickListener {
//            npDialog.dismiss()
//            val intent = Intent(Intent.ACTION_MAIN)
//            intent.addCategory(Intent.CATEGORY_HOME)
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//            context.startActivity(intent)
//            //System.exit(0);
//        })
//        tv_No.setOnClickListener(View.OnClickListener { if (npDialog != null && npDialog.isShowing) npDialog.dismiss() })
//        npDialog.show()
//    }
//
//    fun Maintannace(context: Context?, userid: String?, page: String?): Maintanance? {
//        val maintanance: Array<Maintanance?> = arrayOfNulls<Maintanance>(1)
//        val jsonObject = JsonObject()
//        jsonObject.addProperty("user_id", userid)
//        jsonObject.addProperty("pagename", page)
//        Ion.with(context)
//            .load(BASE_URL.MAINTAINANCE)
//            .setJsonObjectBody(jsonObject)
//            .`as`(Maintanance::class.java)
//            .setCallback(object : FutureCallback<Maintanance?>() {
//                fun onCompleted(e: Exception?, result: Maintanance?) {
//                    if (e != null && result == null) return
//                    if (result != null) {
//                        maintanance[0] = result
//                    }
//                }
//            })
//        return maintanance[0]
//    }
}