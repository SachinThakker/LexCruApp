package com.lexcru.lexcruapp.viewmodels

import android.app.Application
import androidx.lifecycle.MediatorLiveData
import com.lexcru.lexcruapp.R
import com.lexcru.lexcruapp.base.BaseViewModel
import com.lexcru.lexcruapp.dimodule.repositories.AuthRepository
import com.lexcru.lexcruapp.network.Resource
import com.lexcru.lexcruapp.utils.CommonUtils.checkInternetConnected
import com.lexcru.lexcruapp.views.NoInternetActivity

class AuthViewModel(
    application: Application,
    private val authRepository: AuthRepository,
) : BaseViewModel(application) {

    val mContext = application.applicationContext


    val loginStatus: MediatorLiveData<Any> by lazy {
        MediatorLiveData<Any>()
    }


    fun loginUser(
        email: String,
        password: String,
        deviceType: String,
        deviceInfo: String
    ) {
        when {
            !mContext.checkInternetConnected() -> {
                loginStatus.value =
                    Resource.NoInternetError1<String>(R.string.default_internet_message)
                NoInternetActivity.startActivity(mContext)
            }
            else -> {
                loginStatus.addSource(
                    authRepository.loginUser(email = email, password = password, dType = deviceType,dInfo = deviceInfo)
                ) {
                    loginStatus.value = it
                }
            }
        }
    }

}