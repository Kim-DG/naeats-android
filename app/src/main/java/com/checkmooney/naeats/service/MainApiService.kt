package com.checkmooney.naeats.service

import com.checkmooney.naeats.data.entities.GetProfileResponse
import dagger.hilt.android.scopes.FragmentScoped
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST

@FragmentScoped
interface MainApiService {
    @POST("auth/logout")
    fun logout(): Call<Unit>

    @GET("users")
    fun getUserProfile(): Call<GetProfileResponse>
}
