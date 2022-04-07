package com.checkmooney.naeats.service

import com.checkmooney.naeats.data.entities.*
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

    @GET("foods/all")
    fun getAllFoods(): Call<GetFoodsResponse>

    //쿼리 추가 예정
    @GET("foods")
    fun getRecoCoolTimeFoods(): Call<GetFoodsResponse>

    @GET("foods")
    fun getRecoRandomFoods(): Call<GetFoodsResponse>

    @GET("foods")
    fun getRecoFavoriteFoods(): Call<GetFoodsResponse>

    @GET("foods/like")
    fun getLikeFoods(): Call<GetFavoriteFoodsResponse> //
}
