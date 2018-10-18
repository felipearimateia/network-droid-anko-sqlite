package org.androidbh.networkdroid

import android.app.Application
import com.facebook.stetho.Stetho

class NetWorkApp : Application() {

    override fun onCreate() {
        super.onCreate()
        Stetho.initializeWithDefaults(this);
    }
}