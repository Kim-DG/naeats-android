package com.checkmooney.naeats.data

import com.checkmooney.naeats.models.Food
import javax.inject.Inject


/*
유저 정보를 처리하는 저장소.
 */
class UserRepository @Inject constructor(
    private val userDataSource: UserRemoteDataSource,
    private val userLocalDataSource: UserLocalDataSource
) {

}
