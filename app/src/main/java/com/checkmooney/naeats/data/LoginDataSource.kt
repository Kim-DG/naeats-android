package com.checkmooney.naeats.data

import com.checkmooney.naeats.data.entities.AuthTokenResponse
import com.checkmooney.naeats.data.entities.GoogleAuthRequest
import com.checkmooney.naeats.data.entities.RefreshAccessTokenResponse
import com.checkmooney.naeats.service.ApiService
import com.checkmooney.naeats.util.callback
import com.checkmooney.naeats.util.setResponse
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

@ActivityRetainedScoped
class LoginDataSource @Inject constructor(private val service: ApiService) {
    suspend fun getGoogleLoginData(request: GoogleAuthRequest): AuthTokenResponse? {
        return service.login(request)
            .callback()?.setResponse(AuthTokenResponse())
    }

    suspend fun refreshAccessToken(): RefreshAccessTokenResponse? {
        return service.refreshAccessToken()
            .callback()?.setResponse(RefreshAccessTokenResponse())
    }
}
