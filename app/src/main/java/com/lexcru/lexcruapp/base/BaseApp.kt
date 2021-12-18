package com.lexcru.lexcruapp.base

import android.app.Application
import com.lexcru.lexcruapp.dimodule.allModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class BaseApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@BaseApp)
            modules(allModules)
        }
    }
}