package com.checkmooney.naeats.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.checkmooney.naeats.data.MenuRepository
import com.checkmooney.naeats.ui.components.NavigationItem
import com.checkmooney.naeats.data.UserRepository
import com.checkmooney.naeats.data.entities.FoodData
import com.checkmooney.naeats.data.entities.RecommendFood
import com.checkmooney.naeats.data.entities.UserProfile
import com.checkmooney.naeats.ui.main.setting.MyFoodUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val menuRepository: MenuRepository,
    private val userRepository: UserRepository,
) : ViewModel() {
    var viewItem = MutableLiveData<NavigationItem>(NavigationItem.Recommend)

    var navigateToMain = MutableLiveData(false)

    private var _userProfile = MutableLiveData(UserProfile())
    val userInfo: LiveData<UserProfile>
        get() = _userProfile

    private var _categories = MutableLiveData<List<String>>(listOf())
    val categories: LiveData<List<String>>
        get() = _categories


    init {
        viewModelScope.launch {
            val profile = userRepository.getUserProfile()
            profile?.let { _userProfile.value = it }
            _categories.value = menuRepository.getCategories()
        }
    }

    fun updateViewItem(item: NavigationItem) {
        viewItem.value = item
        when (item) {
            NavigationItem.TodayEats -> getAllList()
            NavigationItem.Recommend -> {
                viewModelScope.launch {
                    getAllList()
                    getAllRecoRandomList()
                    getAllRecoFavoriteList()
                    getAllRecoCoolTimeList()
                }
            }
            NavigationItem.Setting -> {
                viewModelScope.launch {
                    getAllInfoFavoriteList()
                    getAllInfoHateList()
                }
            }
        }
    }

    fun initCategoryIndex() {
        _categoryIndex.value = 0
    }

    fun updateCategoryIndex(index: Int) {
        _categoryIndex.value = index
    }

    private val _categoryIndex = MutableLiveData(0)
    val categoryIndex: LiveData<Int>
        get() = _categoryIndex

    private val _allList = MutableLiveData<MutableList<FoodData>>()
    val allList: LiveData<MutableList<FoodData>>
        get() = _allList

    private val _categorizedFoodList = MutableLiveData<MutableList<FoodData>>()
    val categorizedList: LiveData<MutableList<FoodData>>
        get() = _allList

    private val _recoCoolTimeList = MutableLiveData<MutableList<RecommendFood>>()
    val recoCoolTimeList: LiveData<MutableList<RecommendFood>>
        get() = _recoCoolTimeList

    private val _recoFavoriteList = MutableLiveData<MutableList<RecommendFood>>()
    val recoFavoriteList: LiveData<MutableList<RecommendFood>>
        get() = _recoFavoriteList

    private val _recoRandomList = MutableLiveData<MutableList<RecommendFood>>()
    val recoRandomList: LiveData<MutableList<RecommendFood>>
        get() = _recoRandomList

    private val _infoFavoriteList = MutableLiveData<List<MyFoodUiState>>()
    val infoFavoriteList: LiveData<List<MyFoodUiState>>
        get() = _infoFavoriteList

    private val _infoHateList = MutableLiveData<List<MyFoodUiState>>()
    val infoHateList: LiveData<List<MyFoodUiState>>
        get() = _infoHateList

    // All
    private fun getAllList() {
        viewModelScope.launch {
            val list = menuRepository.getAllFoodList()
            _allList.value = list
        }
    }

    fun filterAllList(index: Int) =
        _allList.value?.filter { data ->
            if (index == 0) true else data.categories.any { it == categories.value!![index] }
        }

    // Recommend
    fun getAllRecoCoolTimeList() {
        viewModelScope.launch {
            _recoCoolTimeList.value = mutableListOf()
            _recoCoolTimeList.value = menuRepository.getRecommendFoodList(0,true,"ASC",false, 10)
        }
    }

    fun filterRecoCoolTimeByCategory(index: Int) =
        _recoCoolTimeList.value?.filter { data ->
            if (index == 0) true else data.categories.any { it == categories.value!![index] }
        }


    fun getAllRecoFavoriteList() {
        viewModelScope.launch {
            _recoFavoriteList.value = mutableListOf()
            _recoFavoriteList.value = menuRepository.getRecommendFoodList(0,false,"RAND",true, 10)
        }
    }


    fun filterRecoFavoriteByCategory(index: Int) =
        _recoFavoriteList.value?.filter { data ->
            if (index == 0) true else data.categories.any { it == categories.value!![index]}
        }


    fun getAllRecoRandomList() {
        viewModelScope.launch {
            _recoRandomList.value = mutableListOf()
            _recoRandomList.value = menuRepository.getRecommendFoodList(0,false,"RAND",false, 10)
        }
    }


    fun filterRecoRandomByCategory(index: Int) =
        _recoRandomList.value?.filter { data ->
            if (index == 0) true else data.categories.any { it == categories.value!![index] }
        }


    fun updateMyFoodLike(food: RecommendFood, index: Int){
        val newFood = RecommendFood(food.id, food.name, food.thumbnail, !food.isLike, food.lastEatDate, food.categories)
        when(index){
            0 -> {
                val i = _recoCoolTimeList.value!!.indexOf(food)
                _recoCoolTimeList.value!![i] = newFood
            }
            1 -> {
                _recoFavoriteList.value!!.remove(food)
            }
            2 -> {
                val i = _recoRandomList.value!!.indexOf(food)
                _recoRandomList.value!![i] = newFood
            }
        }

        viewModelScope.launch {
            menuRepository.updateMyFavor(food.id, false)
        }
    }

    fun updateMyFoodDislike(food: RecommendFood){
        viewModelScope.launch {
            menuRepository.updateMyFavor(food.id, true)
        }
    }

        // Today Eats
    fun filterMenuByCategory(category: String) {
        viewModelScope.launch {
            val foodData = menuRepository.getFoodListByCategory(category)
            _categorizedFoodList.value = foodData.toMutableList()
        }
    }

    fun todayEatFoodSelected(foodId: String) {
        viewModelScope.launch {
            menuRepository.addTodayEatLog(foodId = foodId)
        }
    }

    // Setting
    private suspend fun getAllInfoFavoriteList() {
        val list = userRepository.getFavoriteFoodList()
        _infoFavoriteList.value = list.map { menu -> MyFoodUiState(menu.id, menu.name, menu.thumbnail) }
    }

    private suspend fun getAllInfoHateList() {
        val list = userRepository.getDislikeFoodList()
        _infoHateList.value = list.map { menu -> MyFoodUiState(menu.id, menu.name, menu.thumbnail) }
    }

    fun updateFoodPreference(isLike: Boolean, foodId: String) {
        viewModelScope.launch {
            val result = menuRepository.updateMyFavor(foodId, !isLike)
            if (!result) return@launch
            if (isLike) getAllInfoFavoriteList() else getAllInfoHateList()
        }
    }

    fun logout() {
        viewModelScope.launch {
            navigateToMain.value = userRepository.logout()
        }
    }
}

