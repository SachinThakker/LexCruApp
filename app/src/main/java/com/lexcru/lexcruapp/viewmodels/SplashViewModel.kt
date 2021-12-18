package com.lexcru.lexcruapp.viewmodels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.lexcru.lexcruapp.base.BaseViewModel
import kotlinx.coroutines.*
import java.lang.Exception
import kotlin.coroutines.CoroutineContext

class SplashViewModel(application: Application) : BaseViewModel(application),CoroutineScope {
    var splashScreenTimer : MutableLiveData<Boolean> = MutableLiveData()

    fun startSplashScreenTimer(){
        try{
            launch(Dispatchers.Main){
                delay(2000)
                splashScreenTimer.postValue(true)
            }
        }catch (e:Exception){
            splashScreenTimer.postValue(false)
        }
    }
    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main
}