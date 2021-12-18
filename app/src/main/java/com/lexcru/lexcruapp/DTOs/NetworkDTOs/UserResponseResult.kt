package com.lexcru.lexcruapp.DTOs.NetworkDTOs


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserResponseResult(
    @SerializedName("id")
    var id: Int,
    @SerializedName("first_name")
    var first_name: String,
    @SerializedName("last_name")
    var last_name: String,
    @SerializedName("name")
    var name: String,
    @SerializedName("email")
    var email: String,
    @SerializedName("mobile_no")
    var mobile_no: String,
    @SerializedName("dob")
    var dob: String ?= null,
    @SerializedName("gender")
    var gender: String ? = null,
    @SerializedName("device_type")
    var device_type: String,
    @SerializedName("device_id")
    var device_id: String,
    @SerializedName("profile_photo_path")
    var profile_photo_path: String,
    @SerializedName("image")
    var image: String,
    @SerializedName("type")
    var type: String,
    @SerializedName("status")
    var status: String,
): Parcelable