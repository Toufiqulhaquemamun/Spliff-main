package com.mamun.spliff.repository

import com.mamun.spliff.model.Success

interface RegListener {
    fun getRegInfo(userinfo: Success?, error: String?)
}