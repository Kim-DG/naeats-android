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
import com.checkmooney.naeats.models.Category
import com.checkmooney.naeats.models.Food
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

    var categories : List<String> = listOf()

    init {
        viewModelScope.launch {
            val profile = userRepository.getUserProfile()
            profile?.let { _userProfile.value = it }

            categories = menuRepository.getCategories()
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
        _categoryIndex.value = Category.All
    }

    fun updateCategoryIndex(category: Category) {
        _categoryIndex.value = category
    }

    private val _categoryIndex = MutableLiveData(Category.All)
    val categoryIndex: LiveData<Category>
        get() = _categoryIndex

    private val _allList = MutableLiveData<List<FoodData>>()
    val allList: LiveData<List<FoodData>>
        get() = _allList

    private val _categorizedFoodList = MutableLiveData<List<FoodData>>()
    val categorizedList: LiveData<List<FoodData>>
        get() = _allList

    private val _recoCoolTimeList = MutableLiveData<List<FoodData>>()
    val recoCoolTimeList: LiveData<List<FoodData>>
        get() = _recoCoolTimeList

    private val _recoFavoriteList = MutableLiveData<List<FoodData>>()
    val recoFavoriteList: LiveData<List<FoodData>>
        get() = _recoFavoriteList

    private val _recoRandomList = MutableLiveData<List<FoodData>>()
    val recoRandomList: LiveData<List<FoodData>>
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

    fun filterRecoCoolTimeByCategory(category: Category) =
        _recoCoolTimeList.value?.filter { data ->
            if (category == Category.All) true else data.categories.any { it == category.title }
        }


    private fun getAllRecoFavoriteList() {
        viewModelScope.launch {
            _recoFavoriteList.value = menuRepository.getRecoFavoriteFoodList()
        }
    }

    /*
    fun filterRecoFavoriteByCategory(category: Category) =
        _recoFavoriteList.value?.filter {
            if (category == Category.All) true else it.category == category
        }
    */

    private fun getAllRecoRandomList() {
        viewModelScope.launch {
            _recoRandomList.value = menuRepository.getRecoRandomFoodList()
        }
    }

    /*
    fun filterRecoRandomByCategory(category: Category) =
        _recoRandomList.value?.filter {
            if (category == Category.All) true else it.category == category
        }
    */

    // Today Eats
    fun filterMenuByCategory(category: String) {
        viewModelScope.launch {
            val foodData = menuRepository.getFoodListByCategory(category)
            _categorizedFoodList.value = foodData
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

