package com.checkmooney.naeats.models

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.text.TextStyle

sealed class User {
    data class LoggedInUser(val email: String) : User()
    object NoUserLoggedIn : User()
}
