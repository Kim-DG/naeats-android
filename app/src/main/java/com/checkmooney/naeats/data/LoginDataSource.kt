package com.checkmooney.naeats.data

import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

@ActivityRetainedScoped
class LoginDataSource @Inject constructor() {
    suspend fun getGoogleLoginData() {

    }

}
