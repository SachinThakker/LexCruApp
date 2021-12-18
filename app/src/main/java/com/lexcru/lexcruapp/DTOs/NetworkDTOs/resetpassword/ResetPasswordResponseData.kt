package com.lexcru.lexcruapp.DTOs.NetworkDTOs.resetpassword


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.lexcru.lexcruapp.DTOs.NetworkDTOs.UserResponseResult
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResetPasswordResponseData(
    @SerializedName("user")
    var user: UserResponseResult? = null
): Parcelable