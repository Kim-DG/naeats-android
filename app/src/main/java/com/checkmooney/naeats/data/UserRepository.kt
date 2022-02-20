package com.checkmooney.naeats.data

import com.checkmooney.naeats.models.User
import javax.inject.Inject
import javax.inject.Singleton

/*
유저 정보를 처리하는 저장소.
 */
@Singleton
class UserRepository @Inject constructor(
    private val userRemoteDataSource: UserRemoteDataSource,
    private val userLocalDataSource: UserLocalDataSource
) {
    private var _user = User.NoUserLoggedIn
    val user: User
        get() = _user

    fun getRefreshToken() = userLocalDataSource.refreshToken

    fun saveTokenData(accessToken: String? = null, refreshToken: String? = null) {
        accessToken?.let { userLocalDataSource.accessToken = it }
        refreshToken?.let { userLocalDataSource.updateRefreshToken(it) }
    }
}
