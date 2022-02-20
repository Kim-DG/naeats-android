package com.checkmooney.naeats.ui.login

sealed class WelcomeAction {
    object ShowLogo : WelcomeAction()
    object TryAutoLogin : WelcomeAction()
    object TryLogin : WelcomeAction()
    data class VerifyToken(val token: String) : WelcomeAction()
    object Finished : WelcomeAction()
}
