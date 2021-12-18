package com.lexcru.lexcruapp.DTOs.NetworkDTOs.login


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LoginResponse(

    @SerializedName("message")
    var message: String,
    @SerializedName("success")
    var success: Boolean,
    @SerializedName("code")
    var code: Int,
    @SerializedName("data")
    var data: LoginResponseData? = null
): Parcelable