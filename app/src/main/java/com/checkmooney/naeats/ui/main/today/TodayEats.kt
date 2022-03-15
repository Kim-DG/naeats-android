package com.checkmooney.naeats.ui.main.today

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
import com.checkmooney.naeats.ui.components.simpleVerticalScrollbar
import com.checkmooney.naeats.models.Category
import com.checkmooney.naeats.ui.main.MainViewModel
import com.checkmooney.naeats.ui.theme.*

@Composable
fun TodayEats(viewModel: MainViewModel = viewModel()) {
    var selectedTab by rememberSaveable { mutableStateOf(TodayEatsTab.ByKeyword) }
    NaEatsTheme() {
        Column {
            Tab(selectedTab) { selectedTab = it }
            viewModel.menuList.observeAsState().value?.let { it ->
                when (selectedTab) {
                    TodayEatsTab.ByKeyword -> SearchByKeyword(it.map { food -> food.name })
                    TodayEatsTab.ByCategory -> SearchByCategory(
                        onCategoryChanged = { category ->
                            viewModel.filterMenuByCategory(category)?.map { menu -> menu.name }
                        })
                }
            }
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
    menuList: List<String>,
    onItemClicked: () -> Unit = {}
) {
    var searchText by rememberSaveable { mutableStateOf("") }
    val searchList =
        menuList.filter { food -> food.contains(searchText) }
            .sorted()
    Column {
        SearchBar(searchText) { searchText = it }
        ListView(searchList) {
            onItemClicked()
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
    fontSize: TextUnit = 18.sp,
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
fun ListView(list: List<String>, onItemClick: () -> Unit = {}) {
    val lazyListState = rememberLazyListState()
    LazyColumn(
        state = lazyListState,
        modifier = Modifier.simpleVerticalScrollbar(lazyListState)
    ) {
        items(list) { item ->
            ListItem(text = item, modifier = Modifier
                .clickable { onItemClick() }
                .background(MaterialTheme.colors.background)
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 24.dp))
        }
    }
}

@Composable
fun SearchByCategory(onCategoryChanged: (Category) -> List<String>?) {
    val categories = Category.values().drop(1)
    val categoriesCount = categories.count()
    val rowCount = 4
    val nColumns = (categoriesCount / rowCount) + (if (categoriesCount % rowCount == 0) 0 else 1)

    var selectedCategory by rememberSaveable { mutableStateOf(Category.All) }
    Column {
        when (selectedCategory) {
            Category.All -> {
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
                                    text = category?.title ?: "",
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
                Row(
                    modifier = Modifier
                        .background(ThemePink)
                        .fillMaxWidth()
                ) {
                    ListItem(
                        text = selectedCategory.title, modifier = Modifier
                            .padding(16.dp)
                            .weight(1.0f)
                    )
                    IconButton(onClick = { selectedCategory = Category.All }) {
                        Icon(
                            imageVector = Icons.Rounded.Close,
                            contentDescription = null,
                            tint = ThemeGrey
                        )
                    }
                }
                ListView(list = onCategoryChanged(selectedCategory) ?: listOf()) {}
            }
        }
    }
}


