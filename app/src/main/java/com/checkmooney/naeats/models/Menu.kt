package com.checkmooney.naeats.models

enum class Category(val title: String, val id: Int) {
    All("전체", 0),
    Korean("한식", 1),
    Chinese("중식", 2),
    Japanese("일식", 3),
    Western("양식", 4),
    SchoolFood("분식", 5),
    Asian("아시안", 6),
    Dessert("디저트", 7),
    Rice("밥", 8),
    Noddle("면", 9),
    Meat("고기", 10)
}

data class Food(val category: Category, val name: String, val id: Int) {

}

data class FoodInfo(
    val id: String,
    val name: String,
    val thumbnail: String,
    val categories: MutableList<String>
    val likeCount: Int,
    val isLike: Int
)
