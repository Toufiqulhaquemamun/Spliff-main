package com.mamun.spliff.network


import com.mamun.spliff.model.LoginResponse
import com.mamun.spliff.model.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface Api {

    @POST("login")
    fun userLogin(@Body user: User?): Call<LoginResponse?>?

    @POST("register")
    fun userReg(@Body user: User?): Call<User?>?
}