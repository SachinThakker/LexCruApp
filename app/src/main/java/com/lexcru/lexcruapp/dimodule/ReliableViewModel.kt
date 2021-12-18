package com.lexcru.lexcruapp.dimodule

import android.os.Bundle
import androidx.lifecycle.LifecycleObserver

interface ReliableViewModel : LifecycleObserver {
    fun writeTo(bundle: Bundle?){

    }
    fun readFrom(bundle: Bundle?){

    }
}