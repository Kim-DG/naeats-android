package com.checkmooney.naeats.data.entities

import com.checkmooney.naeats.models.Food

abstract class BaseResponse(
    open var statusCode: Int = 0,
    open var errorCode: Int = 0,
    open var message: String = "",
) {
    fun isSuccess() = statusCode in 200..299
}

data class GetProfileResponse(
    val profile: UserProfile,
) : BaseResponse()

data class AuthTokenResponse(
    val accessToken: String = "",
    val refreshToken: String = "",
) : BaseResponse()

data class RefreshAccessTokenResponse(
    val accessToken: String = "",
) : BaseResponse()

data class GetAllFoodsResponse(
    val foods: MutableList<FoodData>
) : BaseResponse()

data class GetRecoCoolTimeFoodsResponse(
    val foods: MutableList<FoodData>,
) : BaseResponse()

data class GetRecoRandomFoodsResponse(
    val foods: MutableList<FoodData>,
) : BaseResponse()

data class GetRecoFavoriteFoodsResponse(
    val foods: MutableList<FoodData>,
) : BaseResponse()

data class GetFavoriteFoodsResponse(
    val foods: List<FoodData>,
) : BaseResponse()

class LogoutResponse : BaseResponse()
