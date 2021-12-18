package com.lexcru.lexcruapp.DTOs.NetworkDTOs.resetpassword


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResetPasswordResponse(
    @SerializedName("message")
    var message: String,
    @SerializedName("success")
    var success: Boolean,
    @SerializedName("code")
    var code: Int,
    @SerializedName("data")
    var data: ResetPasswordResponseData? = null
): Parcelable