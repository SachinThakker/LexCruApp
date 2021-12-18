package com.example.ferrybookingapp.DTOs.NetworkDTOs.login


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.lexcru.lexcruapp.DTOs.NetworkDTOs.UserResponseResult
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ProfileResponseData(
    @SerializedName("token")
    var token: String,
    @SerializedName("user")
    var user: UserResponseResult? = null,

    ): Parcelable