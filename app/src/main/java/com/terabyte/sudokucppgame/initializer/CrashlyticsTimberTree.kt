package com.terabyte.sudokucppgame.initializer

import android.util.Log
import com.google.firebase.crashlytics.FirebaseCrashlytics
import timber.log.Timber

class CrashlyticsTimberTree : Timber.Tree() {

    // Firebase crashlytics automatically reports for ANR and app crashes
    //
    // but with this code Firebase Crashlytics will also report all the Timber.e() messages
    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        if (priority == Log.ERROR || priority == Log.WARN) {
            if (t != null) {
                FirebaseCrashlytics.getInstance().recordException(t)
            } else {
                FirebaseCrashlytics.getInstance().log("[$tag] $message")
            }
        }
    }
}
