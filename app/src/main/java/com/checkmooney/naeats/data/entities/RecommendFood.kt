package com.checkmooney.naeats.data.entities

data class RecommendFood (
    val id: String,
    val name: String,
    val thumbnail: String,
    var isLike: Boolean,
    var lastEatDate: String?,
    val categories: List<String>
)