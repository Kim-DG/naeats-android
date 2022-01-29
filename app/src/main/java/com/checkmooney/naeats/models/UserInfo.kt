package com.checkmooney.naeats.models

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.text.TextStyle

data class UserInfo(val email: String) {

}

object MenuCategoryList {
    val MenuCategoryList = listOf("전체", "한식", "중식", "일식", "양식", "분식", "아시안", "디져트", "밥", "면", "고기")
    val size = MenuCategoryList.size
    val selectList = listOf(
        mutableStateOf(TextStyle()),
        mutableStateOf(TextStyle()),
        mutableStateOf(TextStyle()),
        mutableStateOf(TextStyle()),
        mutableStateOf(TextStyle()),
        mutableStateOf(TextStyle()),
        mutableStateOf(TextStyle()),
        mutableStateOf(TextStyle()),
        mutableStateOf(TextStyle()),
        mutableStateOf(TextStyle()),
        mutableStateOf(TextStyle())
    )
}