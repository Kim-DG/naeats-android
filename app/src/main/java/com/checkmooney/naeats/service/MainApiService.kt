package com.checkmooney.naeats.service

import com.checkmooney.naeats.data.entities.GetFoods
import com.checkmooney.naeats.data.entities.GetFoodsPagination
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

    @GET("foods")
    fun getFoods(): Call<GetFoodsPagination>

    @GET("foods/all")
    fun getAllFoods(): Call<GetFoods>

    @GET("foods/like")
    fun getLikeFoods(): Call<GetFoods>
}
