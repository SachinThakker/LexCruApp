package com.example.ferrybookingapp.DTOs.NetworkDTOs.login


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ProfileResponse(

    @SerializedName("message")
    var message: String,
    @SerializedName("success")
    var success: Boolean,
    @SerializedName("code")
    var code: Int,
    @SerializedName("data")
    var data: ProfileResponseData? = null
): Parcelable