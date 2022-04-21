package com.checkmooney.naeats.ui.main.recommand

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.checkmooney.naeats.data.entities.FoodData
import com.checkmooney.naeats.ui.main.MainViewModel
import com.checkmooney.naeats.ui.theme.*
import com.skydoves.landscapist.glide.GlideImage
import kotlinx.coroutines.delay

@Preview(showBackground = true)
@Composable
fun TodayRecommend(viewModel: MainViewModel = viewModel()) {
    val openDialog = rememberSaveable { mutableStateOf(false) }
    var selectedTab by rememberSaveable { mutableStateOf(RecommendTab.ByCoolTime) }
    val icons = listOf(
        painterResource(id = R.drawable.alarm_grey),
        painterResource(id = R.drawable.favorite_border_grey),
        painterResource(id = R.drawable.question_mark_grey)
    )
    LaunchedEffect(true) {
        while (true) {
            openDialog.value = viewModel.categories.value!!.isEmpty()
            if (!openDialog.value) {
                break
            }
            delay(300)
        }
    }
    if (openDialog.value) {
        LoadingDialog()
    }
    NaEatsTheme {
        Column {
            TabRow(
                contentColor = ChoicePink,
                selectedTabIndex = selectedTab.ordinal,
                backgroundColor = Color.White
            ) {
                icons.forEachIndexed { index, icon ->
                    Tab(
                        icon = { Image(painter = icon, contentDescription = "recommend icon") },
                        selected = selectedTab.ordinal == index,
                        onClick = {
                            selectedTab = RecommendTab.values()[index]
                            viewModel.initCategoryIndex()
                        },
                        selectedContentColor = ChoicePink,
                    )
                }
            }
            UnderBar()
            if (viewModel.categories.value!!.isNotEmpty()) {
                MenuCategory(selectedTab)
            }
        }
    }
}

enum class RecommendTab {
    ByCoolTime, ByFavorite, ByRandom
}


@Composable
fun UnderBar() {
    Box(
        Modifier
            .fillMaxWidth()
            .height(2.dp)
            .background(color = LineLightGrey)
    )
}

@Composable
fun MenuCategory(selectedTab: RecommendTab, viewModel: MainViewModel = viewModel()) {
    var selectedTabIndex by remember { mutableStateOf(0) }
    Column {
        ScrollableTabRow(
            contentColor = ChoicePink,
            selectedTabIndex = selectedTabIndex,
            backgroundColor = ThemePink,
            edgePadding = 0.dp
        ) {
            val categoryList = viewModel.categories.value!!
            categoryList.forEachIndexed { index, text ->
                Tab(
                    text = {
                        Text(
                            text = text, fontFamily = FontFamily(
                                Font(
                                    R.font.cafe24surround_air,
                                )
                            ), color = Color.Black
                        )
                    },
                    selected = selectedTabIndex == index,
                    onClick = {
                        selectedTabIndex = index
                        viewModel.updateCategoryIndex(index)
                    },
                    selectedContentColor = ChoicePink,
                )
            }

        }

        Spacer(modifier = Modifier.height(15.dp))

        when (selectedTab) {
            RecommendTab.ByCoolTime -> viewModel.recoCoolTimeList.observeAsState().value?.let { it ->
                RecommendWindow(it, 0)
            }
            RecommendTab.ByFavorite -> viewModel.recoFavoriteList.observeAsState().value?.let { it ->
                RecommendWindow(it, 1)
            }
            RecommendTab.ByRandom -> viewModel.recoRandomList.observeAsState().value?.let { it ->
                RecommendWindow(it, 2)
            }
        }
    }
}

@Composable
fun RecommendWindow(
    recommendList: MutableList<FoodData>,
    index: Int,
    viewModel: MainViewModel = viewModel()
) {
    Column {
        LazyColumn(
            modifier = pinkBgModifier.weight(1F),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            item {
                Spacer(
                    modifier = Modifier
                        .height(8.dp)
                )

                when (viewModel.categoryIndex.value) {
                    0 -> {
                        recommendList.forEach {
                            RecommendFood(it, index)
                        }
                    }

                    else -> {
                        val filterList = viewModel.categoryIndex.value?.let {
                            when (index) {
                                0 -> {
                                    viewModel.filterRecoCoolTimeByCategory(it)
                                }
                                1 -> {
                                    viewModel.filterRecoFavoriteByCategory(it)
                                }
                                else -> {
                                    viewModel.filterRecoRandomByCategory(it)
                                }
                            }
                        }
                        filterList?.forEach {
                            RecommendFood(it, index)
                        }
                    }

                }
                Spacer(
                    modifier = Modifier
                        .height(8.dp)
                )
            }
        }
        Spacer(
            modifier = Modifier
                .height(11.dp)
                .background(color = Color.White)
        )
    }
}

@Composable
fun RecommendFood(food: FoodData, index: Int, viewModel: MainViewModel = viewModel()) {
    var favor by remember {
        mutableStateOf(0)
    }
    favor = if (food.isLike) {
        R.drawable.favorite_red
    } else {
        R.drawable.favorite_border_red
    }

    val openDropDown = remember { mutableStateOf(false) }
    Row(
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 8.dp)
            .height(120.dp)
    ) {
        GlideImage(
            imageModel = food.thumbnail,
            modifier = Modifier
                .size(120.dp)
                .clip(RoundedCornerShape(30.dp)),
            failure = {
                Text(text = "image request failed.")
            })

        Column(
            verticalArrangement = Arrangement.SpaceEvenly, modifier = Modifier
                .fillMaxHeight()
                .padding(start = 16.dp)
        ) {
            Text(
                food.name, fontFamily = FontFamily(
                    Font(
                        R.font.cafe24surround_air,
                    ),
                ), fontSize = 16.sp, color = TextGrey
            )
            Text(
                "D+11", fontFamily = FontFamily(
                    Font(
                        R.font.cafe24surround_air,
                    ),
                ), fontSize = 12.sp, color = ThemeGrey
            )
        }
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.End
        ) {
            Column {
                Icon(
                    painter = painterResource(id = R.drawable.horiz),
                    contentDescription = "horiz",
                    tint = ThemeGrey,
                    modifier = Modifier
                        .size(30.dp)
                        .clickable(onClick = { openDropDown.value = true })
                )
                DropDown(openDropDown, food)
            }
            Icon(
                painter = painterResource(id = favor),
                contentDescription = "favorite",
                tint = HeartRed,
                modifier = Modifier
                    .size(30.dp)
                    .clickable(onClick = {
                        viewModel.updateMyFoodLike(food, index)
                        food.isLike = !food.isLike
                        favor = if (food.isLike) {
                            R.drawable.favorite_red
                        } else {
                            R.drawable.favorite_border_red
                        }
                    })
            )
        }
    }
}

@SuppressLint("SimpleDateFormat")
@Composable
fun DropDown(
    openDropDown: MutableState<Boolean>,
    food: FoodData,
    viewModel: MainViewModel = viewModel()
) {
    MaterialTheme(shapes = MaterialTheme.shapes.copy(medium = RoundedCornerShape(16.dp))) {
        DropdownMenu(
            expanded = openDropDown.value,
            onDismissRequest = { openDropDown.value = false },
            Modifier
                .wrapContentSize()
        ) {
            DropdownMenuItem(
                onClick = {
                    openDropDown.value = false
                    viewModel.todayEatFoodSelected(food.id)
                }
            ) {
                Text(
                    "오늘 먹었어요",
                    fontFamily = FontFamily(
                        Font(
                            R.font.cafe24surround_air,
                        ),
                    ),
                    fontSize = 12.sp,
                    color = TextGrey,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
            DropdownMenuItem(onClick = {
                openDropDown.value = false
                viewModel.updateMyFoodDislike(food)
            }) {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.heart_broken_black),
                        contentDescription = "favorite",
                        modifier = Modifier
                            .size(30.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun LoadingDialog() {
    Dialog(onDismissRequest = { }) {
        CircularProgressIndicator(color = ChoicePink)
    }
}
