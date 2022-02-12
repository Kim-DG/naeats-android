package com.checkmooney.naeats.data

import com.checkmooney.naeats.models.Food

/*
네트워크 혹은 로컬에서 데이터를 가지고 오는데 사용.
 */
interface MenuDataSource {
    suspend fun getAllMenu(): List<Food>
}
