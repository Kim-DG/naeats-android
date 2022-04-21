package com.checkmooney.naeats.data

import com.checkmooney.naeats.data.entities.*
import com.checkmooney.naeats.service.MainApiService
import com.checkmooney.naeats.util.getResponse
import javax.inject.Inject

class MenuRemoteDataSource @Inject constructor(private val apiService: MainApiService) {
    suspend fun getAllFood(): GetFoodsResponse? {
        return apiService.getAllFoods()
            .getResponse()
    }

    suspend fun getRecoCoolTimeFood(): GetFoodsResponse? {
        return apiService.getRecoCoolTimeFoods()
            .getResponse()
    }

    suspend fun getRecoRandomFood(): GetFoodsResponse? {
        return apiService.getRecoRandomFoods()
            .getResponse()
    }

    suspend fun getRecoFavoriteFood(): GetFoodsResponse? {
        return apiService.getRecoFavoriteFoods()
            .getResponse()
    }

    suspend fun getCategoryList(): CategoryListResponse? {
        return apiService.getCategories()
            .getResponse()
    }

    suspend fun getCategorizedFoodList(category: String): GetFoodsResponse? {
        return apiService.getCategorizedFoods(category)
            .getResponse()
    }

    suspend fun addEatFoodLog(id: String, date: String): EatLogResponse? {
        return apiService.postEatLog(EatLogRequest(eatDate = date, foodId = id))
            .getResponse()
    }

    suspend fun updatePreference(id: String, isDislike: Boolean):PreferenceResponse?{
        return apiService.postPreference(id,PreferenceRequest(isDislike = isDislike))
            .getResponse()
    }
}
