package com.checkmooney.naeats.ui.main.setting

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.checkmooney.naeats.R
import com.checkmooney.naeats.ui.main.MainViewModel
import com.checkmooney.naeats.ui.main.recommand.UnderBar
import com.checkmooney.naeats.ui.theme.*

@Composable
fun Setting(viewModel: MainViewModel = viewModel()) {
    var selectedTab by rememberSaveable { mutableStateOf(SettingTab.ByFavorite) }
    NaEatsTheme() {
        val icons = listOf(
            painterResource(id = R.drawable.favorite_red),
            painterResource(id = R.drawable.heart_broken_black),
            painterResource(id = R.drawable.person)
        )
        Column {
            TabRow(
                contentColor = ChoicePink,
                selectedTabIndex = selectedTab.ordinal,
                backgroundColor = Color.White
            ) {
                icons.forEachIndexed { index, icon ->
                    Tab(
                        icon = { Image(painter = icon, contentDescription = "setting icon") },
                        selected = selectedTab.ordinal == index,
                        onClick = { selectedTab = SettingTab.values()[index] },
                        selectedContentColor = ChoicePink,
                    )
                }
            }
            UnderBar()
            when (selectedTab) {
                SettingTab.ByFavorite -> viewModel.infoFavoriteList.observeAsState().value?.let { it ->
                    MyFoodList(
                        it,
                        xButtonClicked = { id -> viewModel.updateFoodPreference(true, id) })
                }
                SettingTab.ByHate -> viewModel.infoHateList.observeAsState().value?.let { it ->
                    MyFoodList(
                        it,
                        xButtonClicked = { id -> viewModel.updateFoodPreference(false, id) })
                }
                SettingTab.ByMyInfo -> {
                    viewModel.userInfo.observeAsState().value?.let {
                        MyInfo(
                            userInfo = it,
                            onLogoutSelected = { viewModel.logout() }
                        )
                    }

                }
            }
            //MenuCategory(selectRecommend)
        }
    }
}

enum class SettingTab {
    ByFavorite, ByHate, ByMyInfo
}

