package com.checkmooney.naeats.service

import com.checkmooney.naeats.data.entities.*
import retrofit2.Call
import retrofit2.http.*

interface LoginApiService {
    @POST("auth/google")
    fun login(@Body data: GoogleAuthRequest): Call<AuthTokenResponse>

    @GET("auth/refresh")
    fun refreshAccessToken(@Header("Authorization") refreshToken: String): Call<RefreshAccessTokenResponse>
}
