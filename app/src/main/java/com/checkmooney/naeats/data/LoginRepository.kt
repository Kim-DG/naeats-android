package com.checkmooney.naeats.data

import com.checkmooney.naeats.data.entities.GoogleAuthRequest
import dagger.hilt.android.scopes.ActivityRetainedScoped
import dagger.hilt.android.scopes.FragmentScoped
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoginRepository @Inject constructor(
    private val loginRemoteDataSource: LoginRemoteDataSource,
    private val loginLocalDataSource: LoginLocalDataSource
) {
    suspend fun signInAsGoogle(): Boolean {
        val idToken = loginRemoteDataSource.getGoogleIdToken()
        val res = loginRemoteDataSource.getGoogleLoginData(GoogleAuthRequest(idToken = idToken))
        res?.let {
            loginLocalDataSource.accessToken = it.accessToken
            loginLocalDataSource.updateRefreshToken(it.refreshToken)
        }

        return res?.isSuccess() == true
    }

    suspend fun verifyAccessToken(): Boolean {
        val res = loginRemoteDataSource.refreshAccessToken(loginLocalDataSource.refreshToken)
        res?.let { if (it.isSuccess()) loginLocalDataSource.accessToken = it.accessToken }

        return res?.isSuccess() == true
    }

    fun getRefreshToken() = loginLocalDataSource.refreshToken
}
