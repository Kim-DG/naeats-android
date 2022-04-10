package com.checkmooney.naeats.service

import com.checkmooney.naeats.data.entities.*
import dagger.hilt.android.scopes.FragmentScoped
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

@FragmentScoped
interface MainApiService {
    @POST("auth/logout")
    fun logout(): Call<Unit>

    @GET("users")
    fun getUserProfile(): Call<GetProfileResponse>

    @GET("foods/all")
    fun getAllFoods(): Call<GetFoodsResponse>

    //쿼리 추가 예정
    @GET("foods/all")
    fun getRecoCoolTimeFoods(): Call<GetFoodsResponse>

    @GET("foods/all")
    fun getRecoRandomFoods(): Call<GetFoodsResponse>

    @GET("foods/all")
    fun getRecoFavoriteFoods(): Call<GetFoodsResponse>

    @GET("foods/like")
    fun getLikeFoods(): Call<GetFavoriteFoodsResponse> //

    @GET("foods/category")
    fun getCategories(): Call<CategoryListResponse>

    @GET("foods/all")
    fun getCategorizedFoods(@Query("categories") category: String): Call<GetFoodsResponse>

    @POST("eat-logs")
    fun postEatLog(@Body request: EatLogRequest): Call<BaseResponse>
}
