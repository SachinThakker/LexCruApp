package com.lexcru.lexcruapp.base

import android.app.Application
import android.util.Log
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.lexcru.lexcruapp.utils.PreferenceHelper
import com.lexcru.lexcruapp.utils.nullSafe

abstract class BaseViewModel(application: Application) : RuntimePermissionViewModel(application) {

    private val TAG = BaseViewModel::class.java.simpleName

    fun generateFcmToken() {

        FirebaseMessaging.getInstance().token
            .addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.e(TAG, "getInstanceId failed ${task.exception}")
                    return@OnCompleteListener
                }
                // Get new Instance ID token
                val token = task.result
                Log.d(TAG, "FirebaseMessagingService BaseViewModel: Token $token")
                //prefs[PREF_FCM_TOKEN] = token
                PreferenceHelper.saveFCMToken(token.nullSafe())
            })
    }
}