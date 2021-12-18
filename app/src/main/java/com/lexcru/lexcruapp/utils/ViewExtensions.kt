package com.lexcru.lexcruapp.utils

import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager.NameNotFoundException
import android.media.AudioManager
import android.text.Spannable
import android.text.SpannableString
import android.text.style.TextAppearanceSpan
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import com.lexcru.lexcruapp.R
import com.bumptech.glide.Glide
import com.makeramen.roundedimageview.RoundedImageView
import java.io.File


fun String?.nullSafe(defaultValue: String = ""): String {
    return this ?: defaultValue
}

fun Int?.nullSafe(defaultValue: Int = 0): Int {
    return this ?: defaultValue
}

fun Long?.nullSafe(defaultValue: Long = 0L): Long {
    return this ?: defaultValue
}

fun Double?.nullSafe(defaultValue: Double = 0.0): Double {
    return this ?: defaultValue
}

fun Boolean?.nullSafe(defaultValue: Boolean = false): Boolean {
    return this ?: defaultValue
}

fun File?.nullSafe(): File? {
    return this
}

fun View.showView() {
    visibility = View.VISIBLE
}

fun View.hideView() {
    visibility = View.GONE
}

fun View.disableView(){
    isEnabled = false
}

fun View.enableView(){
    isEnabled = true
}

fun Any.showToast(context: Context, message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(context, message, duration).apply { show() }
}

fun AppCompatTextView.setLoginSignUpText() {
    val stringValue = context.getString(R.string.lbl_dont_have_account)
    val spannable = SpannableString(stringValue)

    spannable.setSpan(
        TextAppearanceSpan(context, R.style.gotham_medium),
        0,
        23,
        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
    )
    spannable.setSpan(
        TextAppearanceSpan(context, R.style.gotham_bold),
        24,
        31,
        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
    )
    setText(spannable, TextView.BufferType.SPANNABLE)
}

fun AppCompatTextView.setSignUpLoginText() {
    val stringValue = context.getString(R.string.lbl_already_have_account)
    val spannable = SpannableString(stringValue)

    spannable.setSpan(
        TextAppearanceSpan(context, R.style.gotham_medium),
        0,
        24,
        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
    )
    spannable.setSpan(
        TextAppearanceSpan(context, R.style.gotham_bold),
        25,
        stringValue.length,
        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
    )
    setText(spannable, TextView.BufferType.SPANNABLE)
}


fun AppCompatTextView.setFormattedTextForHomeScreen(stringValue: String, orangeStartValue: Int) {
    val spannable = SpannableString(stringValue)

    spannable.setSpan(
        TextAppearanceSpan(context, R.style.gotham_medium),
        0,
        orangeStartValue,
        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
    )
    spannable.setSpan(
        TextAppearanceSpan(context, R.style.gotham_medium),
        orangeStartValue,
        stringValue.length,
        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
    )
    setText(spannable, TextView.BufferType.SPANNABLE)
}

fun AppCompatTextView.setFormattedTextForCategories(stringValue: String, orangeStartValue: Int) {
    val spannable = SpannableString(stringValue)
    text = stringValue
    spannable.setSpan(
        TextAppearanceSpan(context, R.style.gotham_bold),
        0,
        orangeStartValue,
        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
    )
    spannable.setSpan(
        TextAppearanceSpan(context, R.style.gotham_bold),
        orangeStartValue,
        stringValue.length,
        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
    )
    setText(spannable, TextView.BufferType.SPANNABLE)
}

fun AppCompatTextView.setFormattedTextForSplash() {
    val stringValue = context.getString(R.string.lbl_welcome)
    val spannable = SpannableString(stringValue)
    text = stringValue
    spannable.setSpan(
        TextAppearanceSpan(context, R.style.gotham_bold),
        0,
        10,
        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
    )
    spannable.setSpan(
        TextAppearanceSpan(context, R.style.gotham_medium),
        10,
        stringValue.length,
        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
    )
    setText(spannable, TextView.BufferType.SPANNABLE)
}


fun AppCompatEditText.getValue(): String {
    return text.toString().trim()
}

fun AppCompatEditText.setErrorAndFocus(errorValue: String) {
    //error = errorValue
    showToast(context, errorValue)
    requestFocus()
}

fun loadImageFromUrlUsingGlide(
    imageView: AppCompatImageView,
    url: String?,
    placeHolder: Int = R.drawable.ic_place_holder_profile
) {
    if (!url.isNullOrEmpty()) {
        Glide.with(imageView.context)
            .load(url).centerCrop()
            .placeholder(placeHolder)
            .into(imageView)
    } else {
        Glide.with(imageView.context).load(placeHolder).into(imageView)
    }
}

fun loadImageFromUrlUsingGlide(
    imageView: RoundedImageView,
    url: String?,
    placeHolder: Int = R.drawable.ic_place_holder_profile
) {
    if (!url.isNullOrEmpty()) {
        Glide.with(imageView.context)
            .load(url).centerCrop()
            .placeholder(placeHolder)
            .into(imageView)
    } else {
        Glide.with(imageView.context).load(placeHolder).into(imageView)
    }
}
fun AppCompatEditText.clearEditText() {
    setText("")
}

fun Context.getSystemVolume(audioManager: AudioManager): Int {
    val currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)
    val maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
    return 100 * currentVolume / maxVolume
}

fun AppCompatTextView.setPrice(price: String){
    text = "$price"
    /*text = "$$price"*/
}

fun Context.getAppVersionName(context: Context):String{
    var version = ""
    try {
        val pInfo: PackageInfo =
            context.getPackageManager().getPackageInfo(context.getPackageName(), 0)
        version = pInfo.versionName
    } catch (e: NameNotFoundException) {
        e.printStackTrace()
        version = ""
    }
    return version
}

fun RoundedImageView.setFavoriteIcon(isFavorite:String){
    if(isFavorite == "1") {
        setImageResource(R.drawable.ic_orange_heart)
    }else{
        setImageResource(R.drawable.ic_orange_heart_outline)
    }
}

fun AppCompatTextView.removeAllDrawable() {
    setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
}

fun AppCompatTextView.setDrawableStart(icon:Int) {
    setCompoundDrawablesWithIntrinsicBounds(icon, 0, 0, 0)
}

fun AppCompatTextView.setDrawableEnd(icon:Int) {
    setCompoundDrawablesWithIntrinsicBounds(0, 0, icon, 0)
}

fun AppCompatEditText.setPasswordMaskingtoAsterisk(){
    transformationMethod = com.lexcru.lexcruapp.utils.AsteriskPasswordTransformationMethod()
}