package com.checkmooney.naeats.data

import com.checkmooney.naeats.data.entities.GetProfileResponse
import com.checkmooney.naeats.data.entities.LogoutResponse
import com.checkmooney.naeats.service.GoogleService
import com.checkmooney.naeats.service.MainApiService
import com.checkmooney.naeats.util.getResponse
import javax.inject.Inject

/**
유저의 선호 메뉴 리스트 같은 유저의 정보들을 서버로부터 가져오는 경우에 사용되는 데이터 소스 클래스.
 */
class UserRemoteDataSource @Inject constructor(
    private val apiService: MainApiService,
    private val googleService: GoogleService
) {
    suspend fun signOutFromGoogle() {
        googleService.signOut()
    }

    suspend fun logout(): LogoutResponse {
        return apiService.logout()
            .getResponse(LogoutResponse())
    }

    suspend fun getUserProfile():GetProfileResponse? {
        return apiService.getUserProfile()
            .getResponse()
    }
}
