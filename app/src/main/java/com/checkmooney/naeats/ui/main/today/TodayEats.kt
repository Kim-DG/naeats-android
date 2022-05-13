package com.checkmooney.naeats.ui.main.today

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.*
import com.checkmooney.naeats.R
import com.checkmooney.naeats.data.entities.FoodData
import com.checkmooney.naeats.ui.components.simpleVerticalScrollbar
import com.checkmooney.naeats.ui.main.MainViewModel
import com.checkmooney.naeats.ui.main.setting.LogOutDialogContent
import com.checkmooney.naeats.util.SettingDialogForm
import com.checkmooney.naeats.ui.theme.*

@Composable
fun TodayEats(viewModel: MainViewModel = viewModel()) {
    var selectedTab by rememberSaveable { mutableStateOf(TodayEatsTab.ByKeyword) }
    var openDialog by rememberSaveable { mutableStateOf(false) }
    var tmpFoodId by rememberSaveable { mutableStateOf("") }

    NaEatsTheme() {
        Column {
            Tab(selectedTab) { selectedTab = it }
            when (selectedTab) {
                TodayEatsTab.ByKeyword -> SearchByKeyword(
                    menuList = viewModel.allList.observeAsState().value,
                    onItemClicked = {
                        openDialog = true
                        tmpFoodId = it
                    }
                )
                TodayEatsTab.ByCategory -> SearchByCategory(
                    allCategory = viewModel.categories.value!!,
                    categorizedFoods = viewModel.categorizedList.observeAsState().value,
                    onCategoryChanged = { category ->
                        viewModel.filterMenuByCategory(category)
                    },
                    onItemClicked = {
                        openDialog = true
                        tmpFoodId = it
                    }
                )
            }
        }
    }
    if (openDialog) {
        SettingDialogForm(onCancel = {
            openDialog = false
            tmpFoodId = ""
        }) {
            EatDialogContent(foodId = tmpFoodId, okClicked = {
                openDialog = false
                viewModel.todayEatFoodSelected(it)
                tmpFoodId = ""
            })
        }
    }
}

enum class TodayEatsTab {
    ByKeyword, ByCategory
}

@Composable
fun Tab(selectedTab: TodayEatsTab, onClick: (TodayEatsTab) -> Unit = {}) {
    TabRow(
        selectedTabIndex = selectedTab.ordinal,
        contentColor = ChoicePink,
        backgroundColor = Color.White
    ) {
        Tab(
            selected = selectedTab == TodayEatsTab.ByKeyword,
            onClick = { onClick(TodayEatsTab.ByKeyword) },
            icon = {
                Icon(
                    Icons.Rounded.Search,
                    contentDescription = "search",
                    tint = Color.Gray
                )
            })
        Tab(
            selected = selectedTab == TodayEatsTab.ByCategory,
            onClick = { onClick(TodayEatsTab.ByCategory) },
            icon = {
                Icon(
                    Icons.Rounded.Menu,
                    contentDescription = "menu",
                    tint = Color.Gray
                )
            })
    }
}

@Composable
fun SearchByKeyword(
    menuList: List<FoodData>?,
    onItemClicked: (String) -> Unit = {}
) {
    var searchText by rememberSaveable { mutableStateOf("") }
    val searchList =
        menuList?.filter { food -> food.name.contains(searchText) }?.sortedBy { it.name }
    Column {
        SearchBar(searchText) { searchText = it }
        ListView(searchList ?: listOf()) {
            onItemClicked(it)
        }
    }
}

@Composable
fun SearchBar(searchText: String = "", valueChanged: ((String) -> Unit) = {}) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(ThemePink)
            .padding(all = 12.dp),
        contentAlignment = Alignment.Center
    ) {
        BasicTextField(
            singleLine = true,
            value = searchText,
            onValueChange = valueChanged,
            modifier = Modifier
                .border(
                    width = 1.dp,
                    color = Color.Gray,
                    shape = RoundedCornerShape(percent = 40)
                )
                .background(
                    MaterialTheme.colors.surface,
                    RoundedCornerShape(percent = 40)
                )
                .padding(8.dp)
                .height(32.dp)
                .fillMaxWidth(0.8f),
            textStyle = LocalTextStyle.current.copy(
                color = MaterialTheme.colors.onSurface,
                fontSize = 16.sp
            ),
            decorationBox = { innerTextField ->
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        Modifier
                            .weight(1f)
                            .padding(horizontal = 8.dp)
                    ) {
                        innerTextField()
                    }
                    Icon(Icons.Rounded.Search, contentDescription = null, tint = Color.Gray)
                }
            }
        )
    }
}

@Composable
fun ListItem(
    modifier: Modifier = Modifier,
    text: String = "dfdf",
    fontSize: TextUnit = 17.sp,
    textAlign: TextAlign? = null,
) {
    Text(
        text = text,
        modifier = modifier,
        fontSize = fontSize,
        color = TextGrey,
        textAlign = textAlign
    )
}

@Composable
fun ListView(list: List<FoodData>, onItemClick: (String) -> Unit = {}) {
    val lazyListState = rememberLazyListState()
    LazyColumn(
        state = lazyListState,
        modifier = Modifier.simpleVerticalScrollbar(lazyListState)
    ) {
        items(list) { item ->
            ListItem(text = item.name, modifier = Modifier
                .clickable { onItemClick(item.id) }
                .background(MaterialTheme.colors.background)
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 19.dp))
        }
    }
}

@Composable
fun SearchByCategory(
    allCategory: List<String>,
    categorizedFoods: List<FoodData>?,
    onCategoryChanged: (String) -> Unit,
    onItemClicked: (String) -> Unit
) {
    val categories = allCategory.drop(1)
    val categoriesCount = categories.count()
    val rowCount = 4
    val nColumns = (categoriesCount / rowCount) + (if (categoriesCount % rowCount == 0) 0 else 1)

    var selectedCategory by rememberSaveable { mutableStateOf("전체") }
    Column {
        when (selectedCategory) {
            "전체" -> {
                LazyColumn {
                    items(nColumns) { col ->
                        Row(
                            modifier = Modifier
                                .background(ThemePink)
                                .fillMaxWidth()
                        ) {
                            for (n in col * rowCount until rowCount * (col + 1)) {
                                val category = if (n < categoriesCount) categories[n] else null
                                ListItem(
                                    text = category ?: "",
                                    modifier = Modifier
                                        .clickable {
                                            category?.let {
                                                selectedCategory = category
                                            }
                                        }
                                        .padding(16.dp)
                                        .weight(1.0f),
                                    textAlign = TextAlign.Center)
                            }
                        }
                    }
                }
            }
            else -> {
                BackHandler {
                    selectedCategory = "전체"
                }
                onCategoryChanged(selectedCategory)

                Row(
                    modifier = Modifier
                        .background(ThemePink)
                        .fillMaxWidth()
                ) {
                    ListItem(
                        text = selectedCategory, modifier = Modifier
                            .padding(16.dp)
                            .weight(1.0f)
                    )
                    IconButton(onClick = { selectedCategory = "전체" }) {
                        Icon(
                            imageVector = Icons.Rounded.Close,
                            contentDescription = null,
                            tint = ThemeGrey
                        )
                    }
                }
                if (categorizedFoods?.first()?.categories?.contains(selectedCategory) == true) {
                    ListView(list = categorizedFoods.sortedBy { it.name }) { onItemClicked(it) }
                }
            }
        }
    }
}

@Composable
fun EatDialogContent(foodId: String, okClicked: (String) -> Unit) {
    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(
            modifier = Modifier
                .height(24.dp)
        )
        Text(
            "등록하시겠습니까?", textAlign = TextAlign.Center, modifier = Modifier
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
            modifier = Modifier.clickable(onClick = { okClicked(foodId) })
        )
        Spacer(
            modifier = Modifier
                .height(16.dp)
        )
    }
}


