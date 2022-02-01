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

    fun getMenuList(category: Category) = allFoods.filter { it.category == category }
    fun addMenu(food: Food) = allFoods.add(food)
}

data class Food(val category: Category, val name: String, val id: Int) {

}
