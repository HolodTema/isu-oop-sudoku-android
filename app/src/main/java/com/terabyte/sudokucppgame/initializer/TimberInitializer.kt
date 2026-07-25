package com.terabyte.sudokucppgame.initializer

import android.content.Context
import androidx.startup.Initializer
import com.terabyte.sudokucppgame.BuildConfig
import timber.log.Timber

// Androidx-startup-lib helps reduce time of cold start of the application
// this lib optimizes init-stage of other libs, which are using ContentProvider on their init-stage
//
// so, the main idea is to aggregate all the ContentProviders of all the libs to the one ContentProvider
class TimberInitializer : Initializer<Unit> {

    // here we init Timber lib (like we were doing it in MyApplication class before
    override fun create(p0: Context) {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
            Timber.d("TimberInitializer worked from androidx-startup-library")
        }
        else {
            // in release build variant we will also send all the Timber.e() calls to
            // Firebase Crashlytics
            //
            // PS. Anyway, Firebase Crashlytics also sends all the ANR and app-crashes in any
            // build type
            Timber.plant(CrashlyticsTimberTree())
        }
    }

    override fun dependencies(): List<Class<out Initializer<*>?>?> {
        return emptyList()
    }
}