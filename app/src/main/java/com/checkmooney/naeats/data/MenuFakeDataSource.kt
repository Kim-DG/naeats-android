package com.checkmooney.naeats.data

import com.checkmooney.naeats.di.AppModule
import com.checkmooney.naeats.models.Category
import com.checkmooney.naeats.models.Food
import javax.inject.Inject

object MenuFakeDataSource : MenuDataSource {
    private var allFoods = mutableSetOf<Food>()

    init {
        allFoods.addAll(
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
            )
        )
    }

    override suspend fun getAllMenu() = allFoods.toList()
}
