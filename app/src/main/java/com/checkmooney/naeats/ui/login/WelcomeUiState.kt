package com.checkmooney.naeats.ui.login

data class WelcomeUiState(
    val loggedIn: Boolean = false,
    val action: WelcomeAction = WelcomeAction.ShowLogo
)
