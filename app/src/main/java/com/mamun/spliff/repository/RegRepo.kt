package com.mamun.spliff.repository

import android.util.Log
import com.mamun.spliff.model.LoginResponse
import com.mamun.spliff.model.Success
import com.mamun.spliff.model.User
import com.mamun.spliff.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.ConnectException
import java.net.SocketTimeoutException

object RegRepo {
    private val TAG = RegRepo::class.java.simpleName

    fun userReg(info: User?, regListener: RegListener) {
        val api = RetrofitClient.apiInterface
        api.userReg(info)?.enqueue(object : Callback<Success?> {
            override fun onResponse(call: Call<Success?>, response: Response<Success?>) {
                if (response.isSuccessful && response.body() != null) {
                    regListener.getRegInfo(response.body(), "")
                } else if (response.code() == 401) {
                    regListener.getRegInfo(null, "Unauthorized")
                } else {
                    regListener.getRegInfo(null, response.code().toString())
                }
            }

            override fun onFailure(call: Call<Success?>, t: Throwable) {
                Log.e(TAG, "onFailure: " + call.timeout().toString())
                if (t is SocketTimeoutException) {
                    val message = "Connection Time out. Please try again."
                    regListener.getRegInfo(null, message)
                } else if (t is ConnectException) {
                    regListener.getRegInfo(null, "Failed to connect server")
                } else {
                    regListener.getRegInfo(null, t.message)
                }
            }
        })
    }
}