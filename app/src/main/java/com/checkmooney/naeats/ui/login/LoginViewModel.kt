package com.checkmooney.naeats.ui.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.mapSaver
import androidx.compose.runtime.setValue
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

    }

    fun signInAsGoogle() {
        viewModelScope.launch {
            val loginResult = loginRepository.signInAsGoogle()
            _uiState.value = uiState.value?.copy(loggedIn = loginResult)
        }
    }

    fun setNextAction(action: WelcomeAction) {
        _uiState.value = uiState.value?.copy(action = action)
    }
}
