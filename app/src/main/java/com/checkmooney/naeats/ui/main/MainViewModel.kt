package com.checkmooney.naeats.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.checkmooney.naeats.data.MenuRepository
import com.checkmooney.naeats.ui.components.NavigationItem
import com.checkmooney.naeats.data.UserRepository
import com.checkmooney.naeats.data.entities.FoodData
import com.checkmooney.naeats.data.entities.UserProfile
import com.checkmooney.naeats.ui.main.recommand.RecommendTab
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

    var categories : MutableList<String> = mutableListOf()

    init {
        viewModelScope.launch {
            val profile = userRepository.getUserProfile()
            profile?.let { _userProfile.value = it }

            categories = menuRepository.getCategories().toMutableList()
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

    fun updateRecommendList(index: RecommendTab) {
        when (index) {
            //RecommendTab.ByCoolTime ->
            //RecommendTab.ByFavorite ->
            //RecommendTab.ByRandom ->
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

    private val _recoCoolTimeList = MutableLiveData<MutableList<FoodData>>()
    val recoCoolTimeList: LiveData<MutableList<FoodData>>
        get() = _recoCoolTimeList

    private val _recoFavoriteList = MutableLiveData<MutableList<FoodData>>()
    val recoFavoriteList: LiveData<MutableList<FoodData>>
        get() = _recoFavoriteList

    private val _recoRandomList = MutableLiveData<MutableList<FoodData>>()
    val recoRandomList: LiveData<MutableList<FoodData>>
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

    // Recommend
    private fun getAllRecoCoolTimeList() {
        viewModelScope.launch {
            _recoCoolTimeList.value = menuRepository.getRecoCoolTimeFoodList()
        }
    }

    fun filterRecoCoolTimeByCategory(index: Int) =
        _recoCoolTimeList.value?.filter { data ->
            if (index == 0) true else data.categories.any { it == categories[index] }
        }


    private fun getAllRecoFavoriteList() {
        viewModelScope.launch {
            _recoFavoriteList.value = menuRepository.getRecoFavoriteFoodList()
        }
    }


    fun filterRecoFavoriteByCategory(index: Int) =
        _recoFavoriteList.value?.filter { data ->
            if (index == 0) true else data.categories.any { it == categories[index] }
        }


    private fun getAllRecoRandomList() {
        viewModelScope.launch {
            _recoRandomList.value = menuRepository.getRecoRandomFoodList()
        }
    }


    fun filterRecoRandomByCategory(index: Int) =
        _recoRandomList.value?.filter { data ->
            if (index == 0) true else data.categories.any { it == categories[index] }
        }


    fun updateMyFoodLike(food: FoodData, index: Int){
        val newFood = FoodData(food.id, food.name, food.thumbnail, !food.isLike, food.categories)
        when(index){
            0 -> {
                val i = _recoCoolTimeList.value!!.indexOf(food)
                _recoCoolTimeList.value!![i] = newFood
            }
            1 -> {
                val i = _recoFavoriteList.value!!.indexOf(food)
                _recoFavoriteList.value!![i] = newFood
            }
            2 -> {
                val i = _recoRandomList.value!!.indexOf(food)
                _recoRandomList.value!![i] = newFood
            }
        }

        viewModelScope.launch {

            println("!!" + _recoCoolTimeList.value)
            menuRepository.updateMyFavor(food.id, false)
        }
    }

    fun updateMyFoodDislike(food: FoodData){
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
            //TODO: 통신 결과 팝업으로 띄워줘야함
        }
    }

    // Setting
    private suspend fun getAllInfoFavoriteList() {
        val list = userRepository.getFavoriteFoodList()
        _infoFavoriteList.value = list.map { menu -> MyFoodUiState(menu.id, menu.name, menu.thumbnail) }
    }


    private suspend fun getAllInfoHateList() {
        val list = userRepository.getFavoriteFoodList()
//        _infoHateList.value = list
    }

    fun logout() {
        viewModelScope.launch {
            navigateToMain.value = userRepository.logout()
        }
    }
}

