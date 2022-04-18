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

    @GET("foods/recommends")
    fun testGetRecoCoolTimeFoods(
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 1000,
        @Query("day") day: Int = 0,
        @Query("isEat") isEat: Boolean = false,
        @Query("orderBy") order: String = "DESC"
    ): Call<RecommendFoodsResponse>

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
    fun postEatLog(@Body request: EatLogRequest): Call<EatLogResponse>

    @POST("foods/like/{id}")
    fun postPreference(
        @Path("id") id: String,
        @Body request: PreferenceRequest
    ): Call<PreferenceResponse>
}
