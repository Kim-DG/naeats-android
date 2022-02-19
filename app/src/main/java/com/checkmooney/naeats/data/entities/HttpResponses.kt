package com.checkmooney.naeats.data.entities

abstract class BaseResponse(
    open val statusCode: Int = 0,
    open val errorCode: Int = 0,
    open val message: String = ""
)

data class GetProfileResponse(
    val id: String,
    val email: String,
    val username: String,
    val profileImg: String
) : BaseResponse()

data class AuthTokenResponse(
    val accessToken: String,
    val refreshToken: String
) : BaseResponse()


