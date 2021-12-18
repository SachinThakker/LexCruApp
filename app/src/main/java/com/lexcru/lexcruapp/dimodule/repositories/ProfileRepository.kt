package com.lexcru.lexcruapp.dimodule.repositories

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.example.ferrybookingapp.DTOs.NetworkDTOs.login.ProfileResponse
import com.lexcru.lexcruapp.R
import com.lexcru.lexcruapp.network.ApiService
import com.lexcru.lexcruapp.network.Resource
import com.lexcru.lexcruapp.network.RetrofitClientInstance
import retrofit2.Call
import retrofit2.Response

class ProfileRepository(mContext: Context) {

    val authClient: ApiService by lazy {
        RetrofitClientInstance.createService(ApiService::class.java)
    }

    var mContext = mContext

    fun getUserProfile(
        user_id: String
    ): MutableLiveData<Any> {
        val data = MutableLiveData<Any>()
        val authClientCall =
            authClient.callGetProfileAPI(uId = user_id)
        data.value = Resource.Loading<Boolean>(true)

        authClientCall.enqueue(object : retrofit2.Callback<ProfileResponse> {
            override fun onResponse(
                call: Call<ProfileResponse>,
                response: Response<ProfileResponse>
            ) {

                //JLog.e("TAG", "Loading: false")
                data.value = Resource.Loading<Boolean>(false)

                if (response.isSuccessful) {
                    val mBean: ProfileResponse? = response.body()
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

            override fun onFailure(call: Call<ProfileResponse>, t: Throwable) {
                t.printStackTrace()
                //JLog.e("TAG", "Loading:onFailure false")
                data.value = Resource.Loading<Boolean>(false)
                data.value = Resource.Error<String>(mContext.getString(R.string.lbl_no_internet_connection))
            }
        })
        return data
    }

//    fun updateProfile(
//        user_id: String
//    ): MutableLiveData<Any> {
//        val data = MutableLiveData<Any>()
//        val authClientCall =
//            authClient.callUpdateProfileAPI(mNo = user_id)
//        data.value = Resource.Loading<Boolean>(true)
//
//        authClientCall.enqueue(object : retrofit2.Callback<ProfileResponse> {
//            override fun onResponse(
//                call: Call<ProfileResponse>,
//                response: Response<ProfileResponse>
//            ) {
//
//                //JLog.e("TAG", "Loading: false")
//                data.value = Resource.Loading<Boolean>(false)
//
//                if (response.isSuccessful) {
//                    val mBean: ProfileResponse? = response.body()
//                    /*when (mBean?.responseCode) {
//                        HTTP_SUCCESS -> {
//                            data.value = Resource.Success(mBean)
//                        }
//                        UNAUTHORISED, HTTP_TOKEN_INVALID -> {
//                            data.value = Resource.UnAuthorisedRequest<String>(mBean?.data?.message!!)
//                        }
//                        else -> {
//                            data.value = Resource.Error<String>(mBean?.data?.message!!)
//                        }
//                    }*/
//                    if (mBean?.success!!) {
//                        data.value = Resource.Success(mBean)
//                    } else {
//                        data.value = Resource.Error<String>(mBean?.message!!)
//                    }
//                }
//            }
//
//            override fun onFailure(call: Call<ProfileResponse>, t: Throwable) {
//                t.printStackTrace()
//                //JLog.e("TAG", "Loading:onFailure false")
//                data.value = Resource.Loading<Boolean>(false)
//                data.value = Resource.Error<String>(mContext.getString(R.string.lbl_no_internet_connection))
//            }
//        })
//        return data
//    }
}