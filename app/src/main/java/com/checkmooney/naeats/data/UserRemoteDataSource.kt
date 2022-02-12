package com.checkmooney.naeats.data

import com.checkmooney.naeats.service.ApiService
import javax.inject.Inject

class UserRemoteDataSource @Inject constructor(
    private val service: ApiService
) {

}
