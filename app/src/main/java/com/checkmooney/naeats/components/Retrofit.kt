package com.checkmooney.naeats.components

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Retrofit {
    private val interceptor = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }

    private val client: OkHttpClient = OkHttpClient.Builder() .addInterceptor(interceptor) .build()

    private var retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("TODO")
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val api = retrofit.create(API::class.java)
}

interface API{

}