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
import com.checkmooney.naeats.data.entities.RecommendFood
import com.checkmooney.naeats.ui.main.MainViewModel
import com.checkmooney.naeats.ui.theme.*
import com.skydoves.landscapist.glide.GlideImage
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.*

@Preview(showBackground = true)
@Composable
fun TodayRecommend(viewModel: MainViewModel = viewModel()) {
    val openDialog = rememberSaveable { mutableStateOf(false) }
    var selectedTab by rememberSaveable { mutableStateOf(RecommendTab.ByCoolTime) }
    val selectedCategoryIndex = rememberSaveable { mutableStateOf(0) }
    val foodList = remember { mutableStateListOf<RecommendFood>() }

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
    when (selectedTab) {
        RecommendTab.ByCoolTime -> viewModel.recoCoolTimeList.observeAsState().value?.let { it ->
            foodList.clear()
            it.forEach { food->
                foodList.add(food)
            }
        }
        RecommendTab.ByFavorite -> viewModel.recoFavoriteList.observeAsState().value?.let { it ->
            foodList.clear()
            it.forEach { food->
                foodList.add(food)
            }
        }
        RecommendTab.ByRandom -> viewModel.recoRandomList.observeAsState().value?.let { it ->
            foodList.clear()
            it.forEach { food->
                foodList.add(food)
            }
        }
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
                            selectedCategoryIndex.value = 0
                            when (selectedTab) {
                                RecommendTab.ByCoolTime -> viewModel.getRecoCoolTimeList("전체")
                                RecommendTab.ByFavorite -> viewModel.getAllRecoFavoriteList()
                                RecommendTab.ByRandom -> viewModel.getAllRecoRandomList()
                            }
                        },
                        selectedContentColor = ChoicePink,
                    )
                }
            }
            UnderBar()
            if (viewModel.categories.value!!.isNotEmpty()) {
                MenuCategory(selectedTab, selectedCategoryIndex, foodList)
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
fun MenuCategory(
    selectedTab: RecommendTab,
    selectedTabIndex: MutableState<Int>,
    foodList: MutableList<RecommendFood>,
    viewModel: MainViewModel = viewModel(),
) {
    Column {
        ScrollableTabRow(
            contentColor = ChoicePink,
            selectedTabIndex = selectedTabIndex.value,
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
                    selected = selectedTabIndex.value == index,
                    onClick = {
                        selectedTabIndex.value = index
                        viewModel.updateCategoryIndex(index)
                        if(selectedTabIndex.value == 0){
                            viewModel.getRecoCoolTimeList("전체") // 수정 -> 모든 추천으로
                        }
                        viewModel.categoryIndex.value?.let {
                            when (selectedTab.ordinal) {
                                0 -> {
                                    viewModel.getRecoCoolTimeList(viewModel.categories.value!![it])
                                }
                                1 -> {
                                    viewModel.getRecoCoolTimeList(viewModel.categories.value!![it])
                                }
                                else -> {
                                    viewModel.getRecoCoolTimeList(viewModel.categories.value!![it])
                                }
                            }
                        }
                    },
                    selectedContentColor = ChoicePink,
                )
            }
        }
        Spacer(modifier = Modifier.height(15.dp))
        if(selectedTabIndex.value == 0){
            RecommendWindow(foodList, selectedTab.ordinal)
        }
        else{
            RecommendWindow(foodList, selectedTab.ordinal)
        }
    }
}

@Composable
fun RecommendWindow(
    foodList: MutableList<RecommendFood>,
    index: Int,
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
                foodList.forEach {
                    RecommendFood(it, index) {
                        foodList.remove(it)
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

@SuppressLint("SimpleDateFormat")
@Composable
fun RecommendFood(
    food: RecommendFood,
    index: Int,
    viewModel: MainViewModel = viewModel(),
    onDelete: () -> Unit = {}
) {
    var favor by remember {
        mutableStateOf(0)
    }
    favor = if (food.isLike) {
        R.drawable.favorite_red
    } else {
        R.drawable.favorite_border_red
    }

    val dDay = if (food.lastEatDate == null) {
        "D+X"
    } else {
        val currentTime = Calendar.getInstance()
        currentTime.add(Calendar.HOUR, -9)

        val date = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse(food.lastEatDate!!)
        val dDate = (currentTime.time.time - date.time) / (1000 * 60 * 60 * 24)
        "D+$dDate"
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
                dDay, fontFamily = FontFamily(
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
                DropDown(openDropDown, food){
                    onDelete()
                }
            }
            Icon(
                painter = painterResource(id = favor),
                contentDescription = "favorite",
                tint = HeartRed,
                modifier = Modifier
                    .size(30.dp)
                    .clickable(onClick = {
                        viewModel.updateMyFoodLike(food, index)
                        if (index == 1) {
                            onDelete()
                        }
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
    food: RecommendFood,
    viewModel: MainViewModel = viewModel(),
    onHate: () -> Unit = {}
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
                onHate()
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
