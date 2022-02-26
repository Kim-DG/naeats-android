package com.checkmooney.naeats.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.checkmooney.naeats.R
import com.checkmooney.naeats.ui.theme.*

enum class NavigationItem(var route: String, var title: String, var description: String) {
    Recommend("recommend", "Today\nrecommend", "오늘의 추천 메뉴"),
    TodayEats("today", "Today\neats", "오늘 먹은 음식"),
    Setting("info", "My Info", "내 정보")
}

@Preview
@Composable
fun BottomNavigationBar(
    selectedItem: NavigationItem = NavigationItem.Recommend,
    onClick: (item: NavigationItem) -> Unit = {}
) {
    NaEatsTheme {
        val items = NavigationItem.values()
        BottomNavigation(
            modifier = Modifier.padding(top = 5.dp),
            backgroundColor = MaterialTheme.colors.background
        ) {
            items.forEach { item ->
                BottomNavigationItem(
                    selected = selectedItem == item,
                    onClick = { onClick(item) },
                    icon = {
                        Text(
                            text = item.title,
                            textAlign = TextAlign.Center,
                            style = TextStyle(fontFamily = FontFamily(Font(R.font.cafe24surround_air)), fontSize = 12.sp),
                        )
                    },
                    selectedContentColor = BottomTextPink,
                    unselectedContentColor = ThemeGrey
                )
            }
        }
    }
}
