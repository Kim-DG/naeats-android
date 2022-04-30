package com.checkmooney.naeats.service

import com.checkmooney.naeats.data.entities.*
import dagger.hilt.android.scopes.FragmentScoped
import retrofit2.Call
import retrofit2.http.*

@FragmentScoped
interface MainApiService {
    @POST("auth/logout")
    fun logout(): Call<Unit>

    @GET("users")
    fun getUserProfile(): Call<GetProfileResponse>

    @GET("foods/all")
    fun getAllFoods(): Call<GetFoodsResponse>

    @GET("recommends")
    fun getRecommendFoods(
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int,
        @Query("day") day: Int,
        @Query("isEat") isEat: Boolean,
        @Query("orderBy") order: String,
        @Query("isLike") isLike:Boolean
    ): Call<RecommendFoodsResponse>

    @GET("foods/like")
    fun getLikeFoods(): Call<GetFavoriteFoodsResponse> //

    @GET("foods/dislike")
    fun getDislikeFoods(): Call<GetFavoriteFoodsResponse>

    @GET("foods/category")
    fun getCategories(): Call<CategoryListResponse>

    @GET("foods/all")
    fun getCategorizedFoods(@Query("categories") category: String): Call<GetFoodsResponse>

    @POST("eat-logs")
    fun postEatLog(@Body request: EatLogRequest): Call<EatLogResponse>

    @POST("foods/like/{id}")
    fun postPreference(
        @Path("id") id: String,
        @Body request: PreferenceRequest
    ): Call<PreferenceResponse>
}
