package com.lexcru.lexcruapp.views

import android.content.Context
import android.content.Intent
import com.lexcru.lexcruapp.R
import com.lexcru.lexcruapp.viewmodels.SplashViewModel
import kotlin.reflect.KClass

class NoInternetActivity : com.lexcru.lexcruapp.base.BaseActivity<SplashViewModel>() {
    override val modelClass: KClass<SplashViewModel>
        get() = SplashViewModel::class

    override fun getLayout(): Int {
        return R.layout.activity_no_internet
    }

    override fun init() {

    }

    override fun setListeners() {

    }



    /*override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }*/

    companion object{
        fun startActivity(context: Context){
            val intent = Intent(context, NoInternetActivity::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        }
    }
}