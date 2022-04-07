package com.checkmooney.naeats.data

import com.checkmooney.naeats.data.entities.GetAllFoodsResponse
import com.checkmooney.naeats.data.entities.GetRecoCoolTimeFoodsResponse
import com.checkmooney.naeats.data.entities.GetRecoFavoriteFoodsResponse
import com.checkmooney.naeats.data.entities.GetRecoRandomFoodsResponse
import com.checkmooney.naeats.models.Food
import com.checkmooney.naeats.service.MainApiService
import com.checkmooney.naeats.util.getResponse
import javax.inject.Inject

class MenuRemoteDataSource @Inject constructor(private val apiService: MainApiService): MenuDataSource {
    override suspend fun getAllMenu(): List<Food> {
        return listOf()
    }

    suspend fun getAllFood(): GetAllFoodsResponse? {
        return apiService.getAllFoods()
            .getResponse()
    }

    suspend fun getRecoCoolTimeFood(): GetRecoCoolTimeFoodsResponse? {
        return apiService.getRecoCoolTimeFoods()
            .getResponse()
    }

    suspend fun getRecoRandomFood(): GetRecoRandomFoodsResponse? {
        return apiService.getRecoRandomFoods()
            .getResponse()
    }

    suspend fun getRecoFavoriteFood(): GetRecoFavoriteFoodsResponse? {
        return apiService.getRecoFavoriteFoods()
            .getResponse()
    }

}
