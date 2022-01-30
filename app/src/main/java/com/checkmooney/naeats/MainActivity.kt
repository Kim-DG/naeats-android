@file:Suppress("PreviewAnnotationInFunctionWithParameters")

package com.checkmooney.naeats

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.checkmooney.naeats.databinding.ContentMainBinding

import com.checkmooney.naeats.models.MenuCategoryList
import com.checkmooney.naeats.ui.theme.*
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var viewBinding: ContentMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewBinding = ContentMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        val navView: NavigationView = viewBinding.navView
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        navView.setupWithNavController(navController)

        setContent {
            TodayRecommend()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TodayRecommend() {
    Scaffold() {
        Column {
            Text(
                text = "오늘의 추천 메뉴",
                style = titleText,
                modifier = titleModifier,
            )
            RecommendIcon()
        }
    }
}

@Composable
fun RecommendIcon() {
    var selectRecommend by remember { mutableStateOf(0) }
    val icons = listOf(
        painterResource(id = R.drawable.alarm_grey),
        painterResource(id = R.drawable.favorite_border_grey),
        painterResource(id = R.drawable.question_mark_grey)
    )
    Column {
        TabRow(contentColor = ChoicePink, selectedTabIndex = selectRecommend, backgroundColor = Color.White) {
            icons.forEachIndexed { index, icon ->
                Tab(
                    icon = { Image(painter = icon, contentDescription = "recommend icon") },
                    selected = selectRecommend == index,
                    onClick = { selectRecommend = index },
                    selectedContentColor = ChoicePink,
                )
            }
        }
        UnderBar()
        MenuCategory(selectRecommend)
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
    var selectCategory by remember { mutableStateOf(0) }
    Column {
        ScrollableTabRow(contentColor = ChoicePink, selectedTabIndex = selectCategory, backgroundColor = ThemePink, edgePadding = 0.dp) {

            MenuCategoryList.MenuCategoryList.forEachIndexed { index, text ->
                Tab(
                    text = { Text(text = text, fontFamily = FontFamily(
                        Font(
                            R.font.cafe24surround_air,
                        )
                    ), color = Color.Black) },
                    selected = selectCategory == index,
                    onClick = { selectCategory = index },
                    selectedContentColor = ChoicePink,
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        RecommendWindow(selectRecommend, selectCategory)
    }
}

@Composable
fun RecommendWindow(selectRecommend: Int, selectCategory: Int) {
    val configuration = LocalConfiguration.current
    val screenHeight = (configuration.screenHeightDp * 0.5).dp

    LazyColumn(
        modifier = pinkBgModifier.height(screenHeight),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        item{
            Text(text = selectRecommend.toString())
            Text(text = selectCategory.toString())
        }
    }
}
