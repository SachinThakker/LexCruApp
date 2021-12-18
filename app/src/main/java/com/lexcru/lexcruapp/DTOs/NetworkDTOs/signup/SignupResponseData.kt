package com.lexcru.lexcruapp.DTOs.NetworkDTOs.signup


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.lexcru.lexcruapp.DTOs.NetworkDTOs.UserResponseResult
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SignupResponseData(
    @SerializedName("user")
    var user: UserResponseResult? = null
): Parcelable