package com.checkmooney.naeats.data

import androidx.lifecycle.MutableLiveData
import com.checkmooney.naeats.service.GoogleService
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

@ActivityRetainedScoped
class LoginRepository @Inject constructor(
    private val googleService: GoogleService,
    private val loginDataSource: LoginDataSource,
    private val userRepository: UserRepository
) {
    suspend fun signInAsGoogle() : Boolean {
        return true
    }
}
