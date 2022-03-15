package com.checkmooney.naeats.ui.main.setting

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.checkmooney.naeats.R
import com.checkmooney.naeats.models.Food
import com.checkmooney.naeats.ui.theme.CheckBlue
import com.checkmooney.naeats.ui.theme.TextGrey
import com.checkmooney.naeats.ui.theme.ThemeGrey


@Composable
fun MyFoodList(preferenceList: List<Food>) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        item {
            Spacer(
                modifier = Modifier
                    .height(12.dp)
            )
            preferenceList.forEach {
                MyFood(it)
            }
            Spacer(
                modifier = Modifier
                    .height(12.dp)
            )
        }
    }
}

@Composable
fun MyFood(food: Food) {
    Row(modifier = Modifier.padding(24.dp)) {
        val openDialog = remember {
            mutableStateOf(false)
        }
        Text(
            text = food.name, modifier = Modifier
                .weight(1F)
                .align(Alignment.CenterVertically), fontFamily = FontFamily(
                Font(
                    R.font.cafe24surround_air,
                ),
            ), fontSize = 18.sp, color = TextGrey
        )
        Icon(
            painter = painterResource(id = R.drawable.delete),
            contentDescription = "delete food",
            tint = ThemeGrey,
            modifier = Modifier
                .clickable(onClick = { openDialog.value = true })
                .size(20.dp)
        )
        if (openDialog.value) {
            settingDialogForm(openDialog) { DeleteDialogContent(openDialog) }
        }
    }

}


@Composable
fun DeleteDialogContent(openDialog: MutableState<Boolean>) {
    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(
            modifier = Modifier
                .height(24.dp)
        )
        Text(
            "삭제하시겠습니까?", textAlign = TextAlign.Center, modifier = Modifier
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
            modifier = Modifier
                .clickable(onClick = {
                    openDialog.value = false
                    //삭제
                })
        )
        Spacer(
            modifier = Modifier
                .height(16.dp)
        )
    }
}
