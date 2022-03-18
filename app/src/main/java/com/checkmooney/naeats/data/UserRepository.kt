package com.checkmooney.naeats.data

import com.checkmooney.naeats.data.entities.FoodData
import com.checkmooney.naeats.data.entities.UserProfile
import javax.inject.Inject

/**
유저 정보를 처리하는 저장소.
 */
class UserRepository @Inject constructor(
    private val userRemoteDataSource: UserRemoteDataSource,
    private val loginLocalDataSource: LoginLocalDataSource
) {
    private var user: UserProfile? = null

    suspend fun getUserProfile(): UserProfile? {
        user?.let { return it }

        val profile = userRemoteDataSource.getUserProfile()
        profile?.let {
            user = it.profile
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

    suspend fun getFavoriteFoodList(): List<FoodData> {
        val res = userRemoteDataSource.getFavoriteFood()

        return res?.foods ?: listOf()
    }
}
