package com.checkmooney.naeats

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.checkmooney.naeats.models.UserInfo

class MainViewModel(val userInfo: UserInfo): ViewModel() {
    private val _navigateTo = MutableLiveData<Screen>()
    val navigateTo: LiveData<Screen>
        get() = _navigateTo
}
