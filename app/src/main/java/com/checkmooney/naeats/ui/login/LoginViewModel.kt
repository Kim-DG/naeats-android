package com.checkmooney.naeats.ui.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.checkmooney.naeats.data.LoginRepository
import com.checkmooney.naeats.data.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepository: LoginRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    private var _uiState = MutableLiveData(WelcomeUiState())
    val uiState: LiveData<WelcomeUiState>
        get() = _uiState


    private fun tryAutoLogin() {
        val refreshToken = userRepository.getRefreshToken()
        setNextAction(
            if (refreshToken.isNotEmpty()) WelcomeAction.VerifyToken(refreshToken)
            else WelcomeAction.TryLogin
        )
    }

    fun signInAsGoogle() {
        viewModelScope.launch {
            val loginResult = loginRepository.signInAsGoogle()
            _uiState.value = uiState.value?.copy(loggedIn = loginResult)
        }
    }

    private fun verifyRefreshToken(value: String) {
        viewModelScope.launch {
            if (loginRepository.verifyAccessToken()) {
                setNextAction(WelcomeAction.Finished)
            } else {
                setNextAction(WelcomeAction.TryLogin)
            }
        }
    }

    fun setNextAction(action: WelcomeAction) {
        _uiState.value = uiState.value?.copy(action = action)
        Log.d("LoginViewModel", "Next Action is $action")
        when (action) {
            is WelcomeAction.TryAutoLogin -> tryAutoLogin()
            is WelcomeAction.VerifyToken -> verifyRefreshToken(action.token)
        }
    }
}
