package com.lexcru.lexcruapp.DTOs.NetworkDTOs.forgotpassword


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ForgotPasswordResponseData(
    @SerializedName("otp")
    var otp: Long? = null
): Parcelable