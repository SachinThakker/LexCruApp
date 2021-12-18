package com.lexcru.lexcruapp.network

import com.example.ferrybookingapp.DTOs.NetworkDTOs.login.ProfileResponse
import com.lexcru.lexcruapp.DTOs.NetworkDTOs.forgotpassword.ForgotPasswordResponse
import com.lexcru.lexcruapp.DTOs.NetworkDTOs.login.LoginResponse
import com.lexcru.lexcruapp.DTOs.NetworkDTOs.resetpassword.ResetPasswordResponse
import com.lexcru.lexcruapp.DTOs.NetworkDTOs.signup.SignupResponse
import retrofit2.Call
import retrofit2.http.POST

public interface ApiService {

    @retrofit2.http.FormUrlEncoded
    @POST("api/login")
    fun callLoginAPI(
        @retrofit2.http.Field("email")email:String,
        @retrofit2.http.Field("password")password:String,
        @retrofit2.http.Field("device_type")dType:String,
        @retrofit2.http.Field("device_info")dInfo:String
    ): Call<LoginResponse>

    @retrofit2.http.FormUrlEncoded
    @POST("api/register")
    fun callSignupAPI(
        @retrofit2.http.Field("first_name")fName:String,
        @retrofit2.http.Field("last_name")lName:String,
        @retrofit2.http.Field("email")email:String,
        @retrofit2.http.Field("mobile_no")mNo:String,
        @retrofit2.http.Field("password")password:String,
        @retrofit2.http.Field("device_type")dType:String,
        @retrofit2.http.Field("device_info")dInfo:String
    ): Call<SignupResponse>

    @retrofit2.http.FormUrlEncoded
    @POST("api/userProfile")
    fun callGetProfileAPI(
        @retrofit2.http.Field("user_id")uId:String
    ): Call<ProfileResponse>

    @retrofit2.http.FormUrlEncoded
    @POST("api/userProfile")
    fun callUpdateProfileAPI(
        @retrofit2.http.Field("first_name")firstname:String,
        @retrofit2.http.Field("last_name")lastname:String,
        @retrofit2.http.Field("mobile_no")mNo:String,
        @retrofit2.http.Field("profile_image")pImage:String
    ): Call<ProfileResponse>

    @retrofit2.http.FormUrlEncoded
    @POST("api/forgotPassword")
    fun callForgotPasswordAPI(
        @retrofit2.http.Field("email")email:String
    ): Call<ForgotPasswordResponse>

    @retrofit2.http.FormUrlEncoded
    @POST("api/forgotPassword")
    fun callForgotPassOTPAPI(
        @retrofit2.http.Field("email")email:String,
        @retrofit2.http.Field("otp")otp:String
    ): Call<ForgotPasswordResponse>

    @retrofit2.http.FormUrlEncoded
    @POST("api/resetPassword")
    fun callResetPasswordAPI(
        @retrofit2.http.Field("email")email:String,
        @retrofit2.http.Field("new_password")nPassword:String
    ): Call<ResetPasswordResponse>

    @retrofit2.http.FormUrlEncoded
    @POST("api/resetPassword")
    fun callLogoutAPI(
        @retrofit2.http.Field("user_id")user_id:String
    ): Call<ResetPasswordResponse>

}