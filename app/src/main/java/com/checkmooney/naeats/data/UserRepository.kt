package com.checkmooney.naeats.data

import com.checkmooney.naeats.models.UserInfo
import kotlinx.coroutines.*
import javax.inject.Inject

/**
유저 정보를 처리하는 저장소.
 */
class UserRepository @Inject constructor(
    private val userRemoteDataSource: UserRemoteDataSource,
    private val loginLocalDataSource: LoginLocalDataSource
) {
    private var user: UserInfo? = null

    suspend fun getUserProfile(): UserInfo? {
        user?.let { return it }

        val profile = userRemoteDataSource.getUserProfile()
        profile?.let {
            user = UserInfo(
                id = it.id,
                email = it.email,
                username = it.username,
                profileImg = it.profileImg
            )
        }
        return user
    }

    suspend fun logout(): Boolean {
        val res = userRemoteDataSource.logout()
        if (res.isSuccess()) {
            userRemoteDataSource.signOutFromGoogle()
            loginLocalDataSource.deleteAllToken()
        }

        return res.isSuccess()
    }
}
