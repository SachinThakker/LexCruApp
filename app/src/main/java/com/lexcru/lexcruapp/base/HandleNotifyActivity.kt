package com.astronea.mybugg.base


import android.app.Activity
import android.content.Intent
import android.os.Bundle


/**
 * @see handleIntent: handel intent which comes from pending intent
 * @see doFinished : finish this activity*/
class HandleNotifyActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onNewIntent(intent)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        handleIntent(intent.extras)
    }

    override fun onBackPressed() {
        doFinished()
    }

    /**
     * @param bundle: contains notification data*/
    private fun handleIntent(bundle: Bundle?) {
        if (bundle != null) {
//            startActivity(Intent(this, NotificationActivity::class.java).putExtras(bundle))
         //  startActivity(Intent(this, SplashActivity::class.java).putExtras(bundle))
            finishAffinity()
        }
    }

    private fun doFinished() {
        finish()
        overridePendingTransition(0, 0)
    }

}

