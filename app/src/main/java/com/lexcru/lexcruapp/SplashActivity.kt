package com.lexcru.lexcruapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.provider.SyncStateContract
import android.view.View
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.lexcru.lexcruapp.viewmodels.SplashViewModel
import androidx.lifecycle.ViewModelProvider
import com.lexcru.lexcruapp.utils.*
var splash_img: ImageView? = null
public class SplashActivity : AppCompatActivity()  {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        Constant.Orientation(this@SplashActivity);
        splash_img = findViewById<View>(R.id.splash_img) as ImageView
        val model: SplashViewModel by viewModels()
        model.startSplashScreenTimer();

    }
}