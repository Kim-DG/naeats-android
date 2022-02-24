package com.checkmooney.naeats.data

import androidx.lifecycle.MutableLiveData
import com.checkmooney.naeats.data.entities.GoogleAuthRequest
import com.checkmooney.naeats.service.GoogleService
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

@ActivityRetainedScoped
class LoginRepository @Inject constructor(
    private val googleService: GoogleService,
    private val loginDataSource: LoginDataSource,
    private val userRepository: UserRepository
) {
    suspend fun signInAsGoogle(): Boolean {
        val idToken = googleService.getAuthToken()
        val res = loginDataSource.getGoogleLoginData(GoogleAuthRequest(idToken = idToken))
        res?.let { userRepository.saveTokenData(it.accessToken, it.refreshToken) }

        return res?.isSuccess() == true
    }

    suspend fun verifyAccessToken(): Boolean {
        val res = loginDataSource.refreshAccessToken()
        res?.let { if (it.errorCode == 0) userRepository.saveTokenData(accessToken = it.accessToken) }

        return res?.isSuccess() == true
    }
}
