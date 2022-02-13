package com.checkmooney.naeats.data

import com.checkmooney.naeats.models.Food
import com.checkmooney.naeats.models.User
import javax.inject.Inject
import javax.inject.Singleton


/*
유저 정보를 처리하는 저장소.
 */
@Singleton
class UserRepository @Inject constructor(
    private val userDataSource: UserRemoteDataSource,
    private val userLocalDataSource: UserLocalDataSource
) {
    private var _user = User.NoUserLoggedIn
    val user: User
        get() = _user
}
