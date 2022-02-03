package com.checkmooney.naeats.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.checkmooney.naeats.R
import com.checkmooney.naeats.ui.theme.ChoicePink
import com.checkmooney.naeats.ui.theme.LineGrey
import com.checkmooney.naeats.ui.theme.pinkBgModifier

@Preview(showBackground = true)
@Composable
fun Setting(){
    Scaffold {
        Column {
            SettingIcon()
        }
    }
}

@Composable
fun SettingIcon() {
    var selectSetting by remember { mutableStateOf(0) }
    val icons = listOf(
        painterResource(id = R.drawable.favorite_red),
        painterResource(id = R.drawable.heart_broken_black),
        painterResource(id = R.drawable.person)
    )
    Column {
        TabRow(
            contentColor = ChoicePink,
            selectedTabIndex = selectSetting,
            backgroundColor = Color.White
        ) {
            icons.forEachIndexed { index, icon ->
                Tab(
                    icon = { Image(painter = icon, contentDescription = "setting icon") },
                    selected = selectSetting == index,
                    onClick = { selectSetting = index },
                    selectedContentColor = ChoicePink,
                )
            }
        }
        UnderBar()
        when (selectSetting){
            0 -> MyFoodList()
            1 -> MyFoodList()
            2 -> MyInfo("kdg5746@gmail.com")
        }
        //MenuCategory(selectRecommend)
    }
}

@Composable
fun MyFoodList(){
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        item {
            MyFood("양꼬치")
        }
    }
}

@Composable
fun MyFood(name: String){
    Row(modifier = Modifier.padding(32.dp)){
        Text(text = name, modifier = Modifier.weight(1F), fontFamily = FontFamily(
            Font(
                R.font.cafe24surround_air,
            ),
        ), fontSize = 20.sp ,color = Color.Black)
        Icon(painter = painterResource(id = R.drawable.delete), contentDescription = "delete food", modifier = Modifier.clickable {  })
    }
}

@Composable
fun MyInfo(email: String){
    Column(modifier = Modifier.padding(32.dp)) {
        Row(modifier = Modifier.padding(bottom = 16.dp)){
            Icon(painter = painterResource(id = R.drawable.person), contentDescription = "person", tint = LineGrey)
            Text(text = email, modifier = Modifier.weight(1F), fontFamily = FontFamily(
                Font(
                    R.font.cafe24surround_air,
                ),
            ), fontSize = 20.sp ,color = Color.Black, textAlign = TextAlign.End)
        }
    }
}