package com.checkmooney.naeats.data.entities

data class GoogleAuthRequest(
    val idToken: String
)

data class EatLogRequest(
    val eatDate: String,
    val description: String = ".",
    val foodId: String
)

data class PreferenceRequest(
    val isDislike: Boolean
)