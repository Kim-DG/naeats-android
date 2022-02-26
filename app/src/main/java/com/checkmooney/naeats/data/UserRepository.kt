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
    var user: UserInfo = UserInfo()
        private set

    init {
        CoroutineScope(Dispatchers.IO).launch {
            val profile = userRemoteDataSource.getUserProfile()
            profile?.let {
                user = UserInfo(
                    id = it.id,
                    email = it.email,
                    username = it.username,
                    profileImg = it.profileImg
                )
            }
        }
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
