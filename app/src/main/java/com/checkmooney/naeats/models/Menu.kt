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

object Menu {
    private var allFoods = mutableSetOf<Food>()

    fun getMenuList(category: Category = Category.All) =
        allFoods.filter { if (category == Category.All) true else it.category == category }

    fun addMenu(food: Food) = allFoods.add(food)

    init {
        listOf(
            Food(Category.Western, "햄버거", 0),
            Food(Category.Chinese, "마라탕", 0),
            Food(Category.Korean, "김치찌개", 0),
            Food(Category.Chinese, "마라샹궈", 0),
            Food(Category.Noddle, "짜장면", 0),
            Food(Category.Chinese, "마파두부", 0),
            Food(Category.Japanese, "초밥", 0),
            Food(Category.Western, "피자", 0),
            Food(Category.Western, "파스타", 0),
            Food(Category.Rice, "김치볶음밥", 0),
            Food(Category.Noddle, "냉면", 0),
            Food(Category.Noddle, "라면", 0),
            Food(Category.SchoolFood, "떡볶이", 0),
        ).forEach { addMenu(it) }
        println("asdfsaf")
    }
}

data class Food(val category: Category, val name: String, val id: Int) {

}
