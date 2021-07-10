package com.mamun.spliff.utils

import android.app.Activity
import android.view.WindowManager

object UserInteraction {
    fun setFlag(activity: Activity) {
        activity.window.setFlags(
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
        )
    }

    fun clearFlag(activity: Activity) {
        activity.window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
    }
}