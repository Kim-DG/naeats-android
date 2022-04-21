package com.checkmooney.naeats.data

import com.checkmooney.naeats.data.entities.FoodData
import com.checkmooney.naeats.data.entities.RecommendFood
import java.text.SimpleDateFormat
import javax.inject.Inject

class MenuRepository @Inject constructor(
    private val menuRemoteDataSource: MenuRemoteDataSource
) {

    suspend fun getAllFoodList(): MutableList<FoodData> {
        val res = menuRemoteDataSource.getAllFood()

        return res?.foods ?: mutableListOf()
    }

    suspend fun testGetRecoCoolTimes(): MutableList<RecommendFood> {
        val res = menuRemoteDataSource.testGetRecoCoolTimeFoodList()

        return res?.recommends ?: mutableListOf()
    }

    suspend fun getRecoCoolTimeFoodList(): MutableList<FoodData> {
        val res = menuRemoteDataSource.getRecoCoolTimeFood()

        return res?.foods ?: mutableListOf()
    }

    suspend fun getRecoRandomFoodList(): MutableList<FoodData> {
        val res = menuRemoteDataSource.getRecoRandomFood()

        return res?.foods ?: mutableListOf()
    }

    suspend fun getRecoFavoriteFoodList(): MutableList<FoodData> {
        val res = menuRemoteDataSource.getRecoFavoriteFood()

        return res?.foods ?: mutableListOf()
    }

    suspend fun getCategories(): List<String> {
        val defaultList = listOf("전체")
        val res = menuRemoteDataSource.getCategoryList()
        println(defaultList + (res?.categories ?: listOf()))
        return defaultList + (res?.categories ?: listOf())
    }

    suspend fun getFoodListByCategory(category: String): List<FoodData> {
        val res = menuRemoteDataSource.getCategorizedFoodList(category)

        return res?.foods ?: listOf()
    }

    suspend fun addTodayEatLog(foodId: String) {
        val date = SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSS'Z'", java.util.Locale.getDefault())
        val res =
            menuRemoteDataSource.addEatFoodLog(foodId, date.format(System.currentTimeMillis()))

        println(res?.isSuccess())
    }

    suspend fun updateMyFavor(id: String, isDislike: Boolean): Boolean {
        val res =
            menuRemoteDataSource.updatePreference(id, isDislike)

        println(res?.isSuccess())
        return res?.isSuccess() ?: false
    }
}
