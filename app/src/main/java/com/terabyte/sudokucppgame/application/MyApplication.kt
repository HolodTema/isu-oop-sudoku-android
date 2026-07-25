package com.terabyte.sudokucppgame.application

import android.app.Application
import com.terabyte.sudokucppgame.BuildConfig
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        else {
            // do nothing
            // no logs in release version
        }
    }
}