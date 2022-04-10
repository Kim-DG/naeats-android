package com.checkmooney.naeats.data

import com.checkmooney.naeats.data.entities.BaseResponse
import com.checkmooney.naeats.data.entities.EatLog
import com.checkmooney.naeats.data.entities.GetFoodsResponse
import com.checkmooney.naeats.data.entities.postEatLogsResponse
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

    suspend fun postEatLogs(eatLogs: EatLog): postEatLogsResponse? {
        return apiService.postEatLogs(eatLogs = eatLogs)
            .getResponse()
    }

}
