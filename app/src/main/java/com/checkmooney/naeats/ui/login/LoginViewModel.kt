package com.checkmooney.naeats.ui.login

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

    var isLoggedIn = MutableLiveData<Boolean>(false)

    fun signInAsGoogle() {
        viewModelScope.launch {
            println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^1\n")
            isLoggedIn.value = loginRepository.signInAsGoogle()
        }
    }
}
