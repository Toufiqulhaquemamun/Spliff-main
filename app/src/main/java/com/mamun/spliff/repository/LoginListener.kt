package com.mamun.spliff.repository

import com.mamun.spliff.model.LoginResponse
import com.mamun.spliff.model.Success
import com.mamun.spliff.model.User

interface LoginListener {
    fun getLoginInfo(userinfo: LoginResponse?, error: String?)
}