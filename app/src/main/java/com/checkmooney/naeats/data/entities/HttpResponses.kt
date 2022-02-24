package com.checkmooney.naeats.data.entities

abstract class BaseResponse(
    open var statusCode: Int = 0,
    open var errorCode: Int = 0,
    open var message: String = "",
) {
    fun isSuccess() = statusCode in 200..299
}

data class GetProfileResponse(
    val id: String = "",
    val email: String = "",
    val username: String = "",
    val profileImg: String = ""
) : BaseResponse()

data class AuthTokenResponse(
    val accessToken: String = "",
    val refreshToken: String = ""
) : BaseResponse()

data class RefreshAccessTokenResponse(
    val accessToken: String = "",
) : BaseResponse()
