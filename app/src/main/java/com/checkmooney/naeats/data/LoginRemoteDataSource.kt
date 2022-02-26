package com.checkmooney.naeats.data

import com.checkmooney.naeats.data.entities.AuthTokenResponse
import com.checkmooney.naeats.data.entities.GoogleAuthRequest
import com.checkmooney.naeats.data.entities.RefreshAccessTokenResponse
import com.checkmooney.naeats.service.GoogleService
import com.checkmooney.naeats.service.LoginApiService
import com.checkmooney.naeats.util.getResponse
import javax.inject.Inject

class LoginRemoteDataSource @Inject constructor(
    private val loginApiService: LoginApiService,
    private val googleService: GoogleService,
) {
    suspend fun getGoogleIdToken(): String {
        return googleService.getAuthToken()
    }

    suspend fun getGoogleLoginData(request: GoogleAuthRequest): AuthTokenResponse? {
        return loginApiService.login(request)
            .getResponse()
    }

    suspend fun refreshAccessToken(refreshToken: String): RefreshAccessTokenResponse? {
        return loginApiService.refreshAccessToken("Bearer $refreshToken")
            .getResponse()
    }
}
