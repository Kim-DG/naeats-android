package com.checkmooney.naeats.ui.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
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
import com.checkmooney.naeats.R
import com.checkmooney.naeats.models.Category
import com.checkmooney.naeats.ui.theme.*


@Preview(showBackground = true)
@Composable
fun TodayRecommend() {
    Scaffold {
        Column {
            RecommendIcon()
        }
    }
}

@Composable
fun RecommendIcon() {
    var recommendIconIndex by rememberSaveable { mutableStateOf(0) }
    val icons = listOf(
        painterResource(id = R.drawable.alarm_grey),
        painterResource(id = R.drawable.favorite_border_grey),
        painterResource(id = R.drawable.question_mark_grey)
    )
    Column {
        TabRow(
            contentColor = ChoicePink,
            selectedTabIndex = recommendIconIndex,
            backgroundColor = Color.White
        ) {
            icons.forEachIndexed { index, icon ->
                Tab(
                    icon = { Image(painter = icon, contentDescription = "recommend icon") },
                    selected = recommendIconIndex == index,
                    onClick = { recommendIconIndex = index },
                    selectedContentColor = ChoicePink,
                )
            }
        }
        UnderBar()
        MenuCategory(recommendIconIndex)
    }
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
fun MenuCategory(selectRecommend: Int) {
    var categoryIndex by rememberSaveable { mutableStateOf(0) }
    Column {
        ScrollableTabRow(
            contentColor = ChoicePink,
            selectedTabIndex = categoryIndex,
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
                    selected = categoryIndex == index,
                    onClick = { categoryIndex = index },
                    selectedContentColor = ChoicePink,
                )
            }
        }
        Spacer(modifier = Modifier.height(15.dp))
        RecommendWindow(selectRecommend, categoryIndex)
    }
}

@Composable
fun RecommendWindow(selectRecommend: Int, selectCategory: Int) {
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
                Text(text = selectRecommend.toString())
                Text(text = selectCategory.toString())
                RecommendFood()
                RecommendFood()
                RecommendFood()
                RecommendFood()
                RecommendFood()
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
fun RecommendFood() {
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
                "햄버거", fontFamily = FontFamily(
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