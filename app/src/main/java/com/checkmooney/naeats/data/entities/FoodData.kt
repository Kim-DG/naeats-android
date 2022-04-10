package com.checkmooney.naeats.data.entities

data class FoodData(
    val id: String,
    val name: String,
    val thumbnail: String,
    val isLike: Boolean,
    val categories: List<String>
)
