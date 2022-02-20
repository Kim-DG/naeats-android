package com.checkmooney.naeats.data

import com.checkmooney.naeats.data.entities.AuthTokenResponse
import com.checkmooney.naeats.data.entities.BaseResponse
import com.checkmooney.naeats.data.entities.GoogleAuthRequest
import com.checkmooney.naeats.service.ApiService
import com.checkmooney.naeats.util.callback
import dagger.hilt.android.scopes.ActivityRetainedScoped
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

@ActivityRetainedScoped
class LoginDataSource @Inject constructor(private val service: ApiService) {
    suspend fun getGoogleLoginData(request: GoogleAuthRequest): AuthTokenResponse? {
        return service.login(request)
            .callback()
    }

    suspend fun refreshAccessToken(): AuthTokenResponse? {
        return service.refreshAccessToken().callback()
    }

}
