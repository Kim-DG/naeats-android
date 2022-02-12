package com.checkmooney.naeats.data

import com.checkmooney.naeats.di.AppModule
import javax.inject.Inject

class MenuRepository @Inject constructor(
    private val menuDataSource: MenuDataSource
    ) {
    suspend fun getAllMenu() = menuDataSource.getAllMenu()

}
