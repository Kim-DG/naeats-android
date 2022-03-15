package com.checkmooney.naeats.ui.main.recommand

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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.checkmooney.naeats.R
import com.checkmooney.naeats.models.Category
import com.checkmooney.naeats.models.Food
import com.checkmooney.naeats.ui.main.MainViewModel
import com.checkmooney.naeats.ui.theme.*


@Preview(showBackground = true)
@Composable
fun TodayRecommend(viewModel: MainViewModel = viewModel()) {
    var selectedTab by rememberSaveable { mutableStateOf(RecommendTab.ByCoolTime) }
    val icons = listOf(
        painterResource(id = R.drawable.alarm_grey),
        painterResource(id = R.drawable.favorite_border_grey),
        painterResource(id = R.drawable.question_mark_grey)
    )
    NaEatsTheme() {
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
            MenuCategory(selectedTab)
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
    Column {
        viewModel.categoryIndex.observeAsState().value?.let {
            ScrollableTabRow(
                contentColor = ChoicePink,
                selectedTabIndex = it.ordinal,
                backgroundColor = ThemePink,
                edgePadding = 0.dp
            ) {
                val categoryList = Category.values().toList()
                categoryList.forEachIndexed { index, text ->
                    Tab(
                        text = {
                            Text(
                                text = text.title, fontFamily = FontFamily(
                                    Font(
                                        R.font.cafe24surround_air,
                                    )
                                ), color = Color.Black
                            )
                        },
                        selected = it.ordinal == index,
                        onClick = { viewModel.updateCategoryIndex(Category.values()[index]) },
                        selectedContentColor = ChoicePink,
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(15.dp))

        when (selectedTab) {
            RecommendTab.ByCoolTime -> viewModel.recoCoolTimeList.observeAsState().value?.let { it ->
                RecommendWindow(it)
            }
            RecommendTab.ByFavorite -> viewModel.recoFavoriteList.observeAsState().value?.let { it ->
                RecommendWindow(it)
            }
            RecommendTab.ByRandom -> viewModel.recoRandomList.observeAsState().value?.let { it ->
                RecommendWindow(it)
            }
        }

    }
}

@Composable
fun RecommendWindow(
    recommendList: List<Food>,
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
                //Text(text = selectedTab.toString())
                //Text(text = viewModel.categoryIndex.value.toString())
                when(viewModel.categoryIndex.value){
                    Category.All -> recommendList.forEach {
                        RecommendFood(it)
                    }
                    else -> {
                        val filterList = recommendList.filter { it.category == viewModel.categoryIndex.value }
                        filterList.forEach{
                            RecommendFood(it)
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
fun RecommendFood(food: Food) {
    var favor by remember { mutableStateOf(R.drawable.favorite_border_red) }
    val openDropDown = remember { mutableStateOf(false) }
    Row(
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 8.dp)
            .height(120.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.sample),
            contentDescription = "food image",
            modifier = Modifier
                .size(120.dp)
                .clip(RoundedCornerShape(30.dp))
        )
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
                DropDown(openDropDown)
            }
            Icon(
                painter = painterResource(id = favor),
                contentDescription = "favorite",
                tint = HeartRed,
                modifier = Modifier
                    .size(30.dp)
                    .clickable(onClick = {
                        favor = if (favor == R.drawable.favorite_red) {
                            R.drawable.favorite_border_red
                        } else {
                            R.drawable.favorite_red
                        }
                    })
            )
        }
    }
}

@Composable
fun DropDown(openDropDown: MutableState<Boolean>) {
    MaterialTheme(shapes = MaterialTheme.shapes.copy(medium = RoundedCornerShape(16.dp))) {
        DropdownMenu(
            expanded = openDropDown.value,
            onDismissRequest = { openDropDown.value = false },
            Modifier
                .wrapContentSize()
        ) {
            DropdownMenuItem(
                onClick = { openDropDown.value = false }, Modifier
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
            DropdownMenuItem(onClick = { openDropDown.value = false }) {
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
