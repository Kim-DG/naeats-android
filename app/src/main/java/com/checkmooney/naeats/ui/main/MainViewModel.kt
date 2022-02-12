package com.checkmooney.naeats.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.checkmooney.naeats.data.MenuRepository
import com.checkmooney.naeats.ui.components.NavigationItem
import com.checkmooney.naeats.data.UserRepository
import com.checkmooney.naeats.models.Category
import com.checkmooney.naeats.models.Food
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val menuRepository: MenuRepository,
    private val userRepository: UserRepository
) : ViewModel() {
    var viewItem = MutableLiveData<NavigationItem>(NavigationItem.Recommend)

    fun updateViewItem(item: NavigationItem) {
        viewItem.value = item
        when (item) {
            NavigationItem.TodayEats -> getAllMenuList()
        }
    }

    private val _menuList = MutableLiveData<List<Food>>()
    val menuList: LiveData<List<Food>>
        get() = _menuList

    private fun getAllMenuList() {
        viewModelScope.launch {
            val list = menuRepository.getAllMenu()
            _menuList.value = list
        }
    }

    fun filterMenuByCategory(category: Category) =
        _menuList.value?.filter {
            if (category == Category.All) true else it.category == category
        }
}

