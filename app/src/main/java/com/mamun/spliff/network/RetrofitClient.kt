package com.mamun.spliff.network


import com.mamun.spliff.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitClient {
    private const val MainServer = "http://sandbox.emdexapi.com/api/v1/"

    private val retrofitClient: Retrofit.Builder by lazy {

        val levelType: HttpLoggingInterceptor.Level = if (BuildConfig.BUILD_TYPE.contentEquals("debug"))
            HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE

        val logging = HttpLoggingInterceptor()
        logging.setLevel(levelType)

        val okhttpClient = OkHttpClient.Builder()
        okhttpClient.addInterceptor(logging)

        Retrofit.Builder()
            .baseUrl(MainServer)
            .client(okhttpClient.build())
            .addConverterFactory(GsonConverterFactory.create())
    }

    val apiInterface: Api by lazy {
        retrofitClient
            .build()
            .create(Api::class.java)
    }
}