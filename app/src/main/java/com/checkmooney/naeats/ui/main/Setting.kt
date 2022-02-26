package com.checkmooney.naeats.ui.main

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.checkmooney.naeats.R
import com.checkmooney.naeats.models.Food
import com.checkmooney.naeats.ui.theme.*

@Preview(showBackground = true)
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
                    MyFoodList(it)
                }
                SettingTab.ByHate -> viewModel.infoHateList.observeAsState().value?.let { it ->
                    MyFoodList(it)
                }
                SettingTab.ByMyInfo -> MyInfo("kdg5746@gmail.com")
            }
            //MenuCategory(selectRecommend)
        }
    }
}

enum class SettingTab {
    ByFavorite, ByHate, ByMyInfo
}


@Composable
fun MyFoodList(preferenceList: List<Food>) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        item {
            Spacer(
                modifier = Modifier
                    .height(12.dp)
            )
            preferenceList.forEach{
                MyFood(it)
            }
            Spacer(
                modifier = Modifier
                    .height(12.dp)
            )
        }
    }
}

@Composable
fun MyFood(food: Food) {
    Row(modifier = Modifier.padding(24.dp)) {
        val openDialog = remember {
            mutableStateOf(false)
        }
        Text(
            text = food.name, modifier = Modifier
                .weight(1F)
                .align(Alignment.CenterVertically), fontFamily = FontFamily(
                Font(
                    R.font.cafe24surround_air,
                ),
            ), fontSize = 18.sp, color = TextGrey
        )
        Icon(
            painter = painterResource(id = R.drawable.delete),
            contentDescription = "delete food",
            tint = ThemeGrey,
            modifier = Modifier
                .clickable(onClick = { openDialog.value = true })
                .size(20.dp)
        )
        if (openDialog.value) {
            settingDialogForm(openDialog) { DeleteDialogContent(openDialog) }
        }
    }

}

@Composable
fun MyInfo(email: String) {
    Column(modifier = Modifier.padding(32.dp)) {
        val openDialog = remember {
            mutableStateOf(false)
        }
        Row(modifier = Modifier.padding(bottom = 16.dp)) {
            Icon(
                painter = painterResource(id = R.drawable.person),
                contentDescription = "person",
                tint = ThemeGrey
            )
            Text(
                text = email, modifier = Modifier.weight(1F), fontFamily = FontFamily(
                    Font(
                        R.font.cafe24surround_air,
                    ),
                ), fontSize = 15.sp, color = TextGrey, textAlign = TextAlign.End
            )
        }
        Surface(
            modifier = Modifier
                .padding(top = 12.dp)
                .align(Alignment.End)
        ) {
            Button(
                onClick = { openDialog.value = true },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = ThemePink,
                    contentColor = ThemeGrey
                ),
                border = BorderStroke(width = 0.5.dp, color = ChoicePink)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.logout),
                    contentDescription = "Log-out",
                    modifier = Modifier.size(ButtonDefaults.IconSize)
                )
                Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                Text("LOGOUT")
            }
        }
        if (openDialog.value) {
            settingDialogForm(openDialog) { LogOutDialogContent(openDialog) }
        }
    }
}

@Composable
fun DeleteDialogContent(openDialog: MutableState<Boolean>) {
    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(
            modifier = Modifier
                .height(24.dp)
        )
        Text(
            "삭제하시겠습니까?", textAlign = TextAlign.Center, modifier = Modifier
                .padding(vertical = 8.dp)
                .wrapContentSize(), letterSpacing = 1.5.sp, color = TextGrey
        )
        Spacer(
            modifier = Modifier
                .height(12.dp)
        )
        Icon(
            painter = painterResource(id = R.drawable.check),
            contentDescription = "dialog check",
            tint = CheckBlue,
            modifier = Modifier
                .clickable(onClick = {
                    openDialog.value = false
                    //삭제
                })
        )
        Spacer(
            modifier = Modifier
                .height(16.dp)
        )
    }
}

@Composable
fun LogOutDialogContent(openDialog: MutableState<Boolean>) {
    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(
            modifier = Modifier
                .height(24.dp)
        )
        Text(
            "로그아웃하시겠습니까?", textAlign = TextAlign.Center, modifier = Modifier
                .padding(vertical = 8.dp)
                .wrapContentSize(), letterSpacing = 1.5.sp, color = TextGrey
        )
        Spacer(
            modifier = Modifier
                .height(12.dp)
        )
        Icon(
            painter = painterResource(id = R.drawable.check),
            contentDescription = "dialog check",
            tint = CheckBlue,
            modifier = Modifier
                .clickable(onClick = {
                    openDialog.value = false
                    //삭제
                })
        )
        Spacer(
            modifier = Modifier
                .height(16.dp)
        )
    }
}

@Composable
fun settingDialogForm(openDialog: MutableState<Boolean>, content: @Composable () -> Unit) {
    Dialog(onDismissRequest = { openDialog.value = false }) {
        Surface(
            modifier = Modifier
                .width(240.dp)
                .wrapContentHeight(), shape = RoundedCornerShape(12.dp), color = ThemePink
        ) {
            content()
        }
    }
}
