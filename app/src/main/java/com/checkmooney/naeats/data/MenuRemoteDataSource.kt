package com.checkmooney.naeats.data

import com.checkmooney.naeats.di.AppModule
import com.checkmooney.naeats.models.Food
import com.checkmooney.naeats.service.ApiService
import javax.inject.Inject

class MenuRemoteDataSource @Inject constructor(private val apiService: ApiService): MenuDataSource {
    override suspend fun getAllMenu(): List<Food> {
        TODO("Not yet implemented")
    }

}
