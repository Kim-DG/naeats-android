package com.checkmooney.naeats.service

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("TODO")
    fun getMenuList(@Body string: String): Call<String> //TODO: 테스트 용도로 추가
}
