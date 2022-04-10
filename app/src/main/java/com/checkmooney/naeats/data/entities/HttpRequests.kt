package com.checkmooney.naeats.data.entities

data class GoogleAuthRequest(
    val idToken: String
)

data class EatLog(
    val eatDate: String,
    val description: String,
    val foodId: String
)
