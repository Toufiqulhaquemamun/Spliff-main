package com.mamun.spliff.utils

import android.app.Application
import android.content.Context

class ApplicationContext: Application() {
    override fun onCreate() {
        super.onCreate()
        context = this
    }

    companion object {
        var context: ApplicationContext? = null
        @Synchronized
        fun getContext(): Context? {
            return context
        }
    }
}