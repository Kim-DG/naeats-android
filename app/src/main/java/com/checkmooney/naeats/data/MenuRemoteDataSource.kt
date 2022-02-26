package com.checkmooney.naeats.data

import com.checkmooney.naeats.models.Food
import com.checkmooney.naeats.service.MainApiService
import javax.inject.Inject

class MenuRemoteDataSource @Inject constructor(private val apiService: MainApiService): MenuDataSource {
    override suspend fun getAllMenu(): List<Food> {
        TODO("Not yet implemented")
    }

}
