package com.checkmooney.naeats.data

import javax.inject.Inject

class MenuRepository @Inject constructor(
    private val menuDataSource: MenuDataSource
    ) {
    suspend fun getAllMenu() = menuDataSource.getAllMenu()

}
