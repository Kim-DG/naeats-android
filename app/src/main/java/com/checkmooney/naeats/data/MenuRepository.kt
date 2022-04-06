package com.checkmooney.naeats.data

import com.checkmooney.naeats.data.entities.FoodData
import javax.inject.Inject

class MenuRepository @Inject constructor(
    private val menuDataSource: MenuDataSource,
    private val menuRemoteDataSource: MenuRemoteDataSource
    ) {
    suspend fun getAllMenu() = menuDataSource.getAllMenu()

    suspend fun getAllFoodList(): MutableList<FoodData> {
        val res = menuRemoteDataSource.getAllFood()

        return res?.foods ?: mutableListOf()
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

}
