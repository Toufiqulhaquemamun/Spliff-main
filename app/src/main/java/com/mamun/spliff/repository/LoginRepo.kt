package com.mamun.spliff.repository

import android.util.Log
import com.mamun.spliff.model.LoginResponse
import com.mamun.spliff.model.User
import com.mamun.spliff.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.ConnectException
import java.net.SocketTimeoutException

object LoginRepo {
    private val TAG = LoginRepo::class.java.simpleName

    fun userLogin(info: User?, loginListener: LoginListener) {
        val api = RetrofitClient.apiInterface
        api.userLogin(info)?.enqueue(object : Callback<LoginResponse?> {
            override fun onResponse(call: Call<LoginResponse?>, response: Response<LoginResponse?>) {
                if (response.isSuccessful && response.body() != null) {
                    loginListener.getLoginInfo(response.body(), "")
                } else if (response.code() == 401) {
                    loginListener.getLoginInfo(null, "Unauthorized")
                } else {
                    loginListener.getLoginInfo(null, response.code().toString())
                }
            }

            override fun onFailure(call: Call<LoginResponse?>, t: Throwable) {
                Log.e(TAG, "onFailure: " + call.timeout().toString())
                if (t is SocketTimeoutException) {
                    val message = "Connection Time out. Please try again."
                    loginListener.getLoginInfo(null, message)
                } else if (t is ConnectException) {
                    loginListener.getLoginInfo(null, "Failed to connect server")
                } else {
                    loginListener.getLoginInfo(null, t.message)
                }
            }
        })
    }
}