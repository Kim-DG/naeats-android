package com.checkmooney.naeats.data

import android.content.Context
import com.checkmooney.naeats.service.SharedPrefService
import javax.inject.Inject

/*
기기 내에 저장되어 있는 유저 정보를 가지고 오는 데이터 소스 클래스
 */
class UserLocalDataSource @Inject constructor(private val sharedPrefService: SharedPrefService) {
    var accessToken: String = ""

    private val refreshTokenKey = "refresh_token"
    var refreshToken: String
        get() {
            return sharedPrefService.getString(refreshTokenKey) ?: ""
        }
        private set(value) {
            sharedPrefService.setString(refreshTokenKey, value)
        }

    fun updateRefreshToken(value: String) {
        refreshToken = value
    }
}
