package com.lexcru.lexcruapp.dimodule.repositories

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.lexcru.lexcruapp.R
import com.lexcru.lexcruapp.network.ApiService
import com.lexcru.lexcruapp.network.Resource
import com.lexcru.lexcruapp.network.RetrofitClientInstance
import retrofit2.Call
import retrofit2.Response

class AuthRepository(mContext: Context) {

    val authClient: ApiService by lazy {
        RetrofitClientInstance.createService(ApiService::class.java)
    }

    var mContext = mContext

    fun loginUser(
        email: String,
        password: String,
        dType: String,
        dInfo: String
    ): MutableLiveData<Any> {
        val data = MutableLiveData<Any>()
        val authClientCall =
            authClient.callLoginAPI(email = email, password = password, dType = dType,dInfo = dInfo)
        data.value = Resource.Loading<Boolean>(true)

        authClientCall.enqueue(object : retrofit2.Callback<com.lexcru.lexcruapp.DTOs.NetworkDTOs.login.LoginResponse> {
            override fun onResponse(
                call: Call<com.lexcru.lexcruapp.DTOs.NetworkDTOs.login.LoginResponse>,
                response: Response<com.lexcru.lexcruapp.DTOs.NetworkDTOs.login.LoginResponse>
            ) {

                //JLog.e("TAG", "Loading: false")
                data.value = Resource.Loading<Boolean>(false)

                if (response.isSuccessful) {
                    val mBean: com.lexcru.lexcruapp.DTOs.NetworkDTOs.login.LoginResponse? = response.body()
                    /*when (mBean?.responseCode) {
                        HTTP_SUCCESS -> {
                            data.value = Resource.Success(mBean)
                        }
                        UNAUTHORISED, HTTP_TOKEN_INVALID -> {
                            data.value = Resource.UnAuthorisedRequest<String>(mBean?.data?.message!!)
                        }
                        else -> {
                            data.value = Resource.Error<String>(mBean?.data?.message!!)
                        }
                    }*/
                    if (mBean?.success!!) {
                        data.value = Resource.Success(mBean)
                    } else {
                        data.value = Resource.Error<String>(mBean?.message!!)
                    }
                }
            }

            override fun onFailure(call: Call<com.lexcru.lexcruapp.DTOs.NetworkDTOs.login.LoginResponse>, t: Throwable) {
                t.printStackTrace()
                //JLog.e("TAG", "Loading:onFailure false")
                data.value = Resource.Loading<Boolean>(false)
                data.value = Resource.Error<String>(mContext.getString(R.string.lbl_no_internet_connection))
            }
        })
        return data
    }

    fun signupUser(
        fName: String,
        lName: String,
        email: String,
        mobile_no: String,
        password: String,
        dType: String,
        dInfo: String
    ): MutableLiveData<Any> {
        val data = MutableLiveData<Any>()
        val authClientCall =
            authClient.callSignupAPI(fName= fName,lName= lName,email = email,mNo = mobile_no,
                password = password, dType = dType,dInfo = dInfo)
        data.value = Resource.Loading<Boolean>(true)

        authClientCall.enqueue(object : retrofit2.Callback<com.lexcru.lexcruapp.DTOs.NetworkDTOs.signup.SignupResponse> {
            override fun onResponse(
                call: Call<com.lexcru.lexcruapp.DTOs.NetworkDTOs.signup.SignupResponse>,
                response: Response<com.lexcru.lexcruapp.DTOs.NetworkDTOs.signup.SignupResponse>
            ) {

                //JLog.e("TAG", "Loading: false")
                data.value = Resource.Loading<Boolean>(false)

                if (response.isSuccessful) {
                    val mBean: com.lexcru.lexcruapp.DTOs.NetworkDTOs.signup.SignupResponse? = response.body()
                    /*when (mBean?.responseCode) {
                        HTTP_SUCCESS -> {
                            data.value = Resource.Success(mBean)
                        }
                        UNAUTHORISED, HTTP_TOKEN_INVALID -> {
                            data.value = Resource.UnAuthorisedRequest<String>(mBean?.data?.message!!)
                        }
                        else -> {
                            data.value = Resource.Error<String>(mBean?.data?.message!!)
                        }
                    }*/
                    if (mBean?.success!!) {
                        data.value = Resource.Success(mBean)
                    } else {
                        data.value = Resource.Error<String>(mBean?.message!!)
                    }
                }
            }

            override fun onFailure(call: Call<com.lexcru.lexcruapp.DTOs.NetworkDTOs.signup.SignupResponse>, t: Throwable) {
                t.printStackTrace()
                //JLog.e("TAG", "Loading:onFailure false")
                data.value = Resource.Loading<Boolean>(false)
                data.value = Resource.Error<String>(mContext.getString(R.string.lbl_no_internet_connection))
            }
        })
        return data
    }

    fun forgotPassword(
        email: String
    ): MutableLiveData<Any> {
        val data = MutableLiveData<Any>()
        val authClientCall =
            authClient.callForgotPasswordAPI(email = email)
        data.value = Resource.Loading<Boolean>(true)

        authClientCall.enqueue(object : retrofit2.Callback<com.lexcru.lexcruapp.DTOs.NetworkDTOs.forgotpassword.ForgotPasswordResponse> {
            override fun onResponse(
                call: Call<com.lexcru.lexcruapp.DTOs.NetworkDTOs.forgotpassword.ForgotPasswordResponse>,
                response: Response<com.lexcru.lexcruapp.DTOs.NetworkDTOs.forgotpassword.ForgotPasswordResponse>
            ) {

                //JLog.e("TAG", "Loading: false")
                data.value = Resource.Loading<Boolean>(false)

                if (response.isSuccessful) {
                    val mBean: com.lexcru.lexcruapp.DTOs.NetworkDTOs.forgotpassword.ForgotPasswordResponse? = response.body()
                    /*when (mBean?.responseCode) {
                        HTTP_SUCCESS -> {
                            data.value = Resource.Success(mBean)
                        }
                        UNAUTHORISED, HTTP_TOKEN_INVALID -> {
                            data.value = Resource.UnAuthorisedRequest<String>(mBean?.data?.message!!)
                        }
                        else -> {
                            data.value = Resource.Error<String>(mBean?.data?.message!!)
                        }
                    }*/
                    if (mBean?.success!!) {
                        data.value = Resource.Success(mBean)
                    } else {
                        data.value = Resource.Error<String>(mBean?.message!!)
                    }
                }
            }

            override fun onFailure(call: Call<com.lexcru.lexcruapp.DTOs.NetworkDTOs.forgotpassword.ForgotPasswordResponse>, t: Throwable) {
                t.printStackTrace()
                //JLog.e("TAG", "Loading:onFailure false")
                data.value = Resource.Loading<Boolean>(false)
                data.value = Resource.Error<String>(mContext.getString(R.string.lbl_no_internet_connection))
            }
        })
        return data
    }

    fun forgotPasswordOtpVerification(
        email: String,
        otp: String
    ): MutableLiveData<Any> {
        val data = MutableLiveData<Any>()
        val authClientCall =
            authClient.callForgotPassOTPAPI(email = email,otp = otp)
        data.value = Resource.Loading<Boolean>(true)

        authClientCall.enqueue(object : retrofit2.Callback<com.lexcru.lexcruapp.DTOs.NetworkDTOs.forgotpassword.ForgotPasswordResponse> {
            override fun onResponse(
                call: Call<com.lexcru.lexcruapp.DTOs.NetworkDTOs.forgotpassword.ForgotPasswordResponse>,
                response: Response<com.lexcru.lexcruapp.DTOs.NetworkDTOs.forgotpassword.ForgotPasswordResponse>
            ) {

                //JLog.e("TAG", "Loading: false")
                data.value = Resource.Loading<Boolean>(false)

                if (response.isSuccessful) {
                    val mBean: com.lexcru.lexcruapp.DTOs.NetworkDTOs.forgotpassword.ForgotPasswordResponse? = response.body()
                    /*when (mBean?.responseCode) {
                        HTTP_SUCCESS -> {
                            data.value = Resource.Success(mBean)
                        }
                        UNAUTHORISED, HTTP_TOKEN_INVALID -> {
                            data.value = Resource.UnAuthorisedRequest<String>(mBean?.data?.message!!)
                        }
                        else -> {
                            data.value = Resource.Error<String>(mBean?.data?.message!!)
                        }
                    }*/
                    if (mBean?.success!!) {
                        data.value = Resource.Success(mBean)
                    } else {
                        data.value = Resource.Error<String>(mBean?.message!!)
                    }
                }
            }

            override fun onFailure(call: Call<com.lexcru.lexcruapp.DTOs.NetworkDTOs.forgotpassword.ForgotPasswordResponse>, t: Throwable) {
                t.printStackTrace()
                //JLog.e("TAG", "Loading:onFailure false")
                data.value = Resource.Loading<Boolean>(false)
                data.value = Resource.Error<String>(mContext.getString(R.string.lbl_no_internet_connection))
            }
        })
        return data
    }

    fun resetPassword(
        email: String,
        nPassword: String
    ): MutableLiveData<Any> {
        val data = MutableLiveData<Any>()
        val authClientCall =
            authClient.callResetPasswordAPI(email = email,nPassword = nPassword)
        data.value = Resource.Loading<Boolean>(true)

        authClientCall.enqueue(object : retrofit2.Callback<com.lexcru.lexcruapp.DTOs.NetworkDTOs.resetpassword.ResetPasswordResponse> {
            override fun onResponse(
                call: Call<com.lexcru.lexcruapp.DTOs.NetworkDTOs.resetpassword.ResetPasswordResponse>,
                response: Response<com.lexcru.lexcruapp.DTOs.NetworkDTOs.resetpassword.ResetPasswordResponse>
            ) {

                //JLog.e("TAG", "Loading: false")
                data.value = Resource.Loading<Boolean>(false)

                if (response.isSuccessful) {
                    val mBean: com.lexcru.lexcruapp.DTOs.NetworkDTOs.resetpassword.ResetPasswordResponse? = response.body()
                    /*when (mBean?.responseCode) {
                        HTTP_SUCCESS -> {
                            data.value = Resource.Success(mBean)
                        }
                        UNAUTHORISED, HTTP_TOKEN_INVALID -> {
                            data.value = Resource.UnAuthorisedRequest<String>(mBean?.data?.message!!)
                        }
                        else -> {
                            data.value = Resource.Error<String>(mBean?.data?.message!!)
                        }
                    }*/
                    if (mBean?.success!!) {
                        data.value = Resource.Success(mBean)
                    } else {
                        data.value = Resource.Error<String>(mBean?.message!!)
                    }
                }
            }

            override fun onFailure(call: Call<com.lexcru.lexcruapp.DTOs.NetworkDTOs.resetpassword.ResetPasswordResponse>, t: Throwable) {
                t.printStackTrace()
                //JLog.e("TAG", "Loading:onFailure false")
                data.value = Resource.Loading<Boolean>(false)
                data.value = Resource.Error<String>(mContext.getString(R.string.lbl_no_internet_connection))
            }
        })
        return data
    }
}