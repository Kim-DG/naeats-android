package com.checkmooney.naeats.data.entities

data class FoodInfo(
    val id: String,
    val name: String,
    val thumbnail: String,
    val categories: MutableList<String>,
    val likeCount: Int,
    val isLike: Int
)
