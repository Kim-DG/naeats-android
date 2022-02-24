package com.checkmooney.naeats.service

import com.checkmooney.naeats.data.entities.*
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @POST("TODO")
    fun getMenuList(@Body string: String): Call<String> //TODO: 테스트 용도로 추가

    @POST("auth/google")
    fun login(@Body data: GoogleAuthRequest): Call<AuthTokenResponse>

    @GET("auth/refresh")
    fun refreshAccessToken(): Call<RefreshAccessTokenResponse>

}
