package com.lexcru.lexcruapp.DTOs.NetworkDTOs.login


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.lexcru.lexcruapp.DTOs.NetworkDTOs.UserResponseResult
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LoginResponseData(
    @SerializedName("token")
    var token: String,
    @SerializedName("user")
    var user: UserResponseResult? = null,

    ): Parcelable