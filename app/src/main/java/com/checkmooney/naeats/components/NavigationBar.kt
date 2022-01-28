package com.checkmooney.naeats.components

import android.view.Gravity
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.checkmooney.naeats.R

sealed class NavigationItem(var route: String, var title: String) {
    object Recommend: NavigationItem("recommend", "Today\nrecommend")
    object TodayEats: NavigationItem("today", "Today\neats")
    object Preference: NavigationItem("preference", "Preference")
}

@Preview
@Composable
fun BottomNavigationBar() {
    val items = listOf(NavigationItem.Recommend, NavigationItem.TodayEats, NavigationItem.Preference)
    BottomNavigation() {
        items.forEach { item ->
            BottomNavigationItem(selected = false, onClick = { /*TODO*/ }, icon = {
                Text(text = item.title)
            })
        }
    }
}
