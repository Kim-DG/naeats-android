package com.checkmooney.naeats

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import java.security.InvalidParameterException

enum class Screen { Welcome, Login, Main }

fun Fragment.navigate(to: Screen, from: Screen) {
    if (to == from) {
        throw InvalidParameterException("Can't navigate to $to")
    }
    when (to) {
        Screen.Login -> {
            findNavController().navigate(R.id.login_fragment)
        }
        Screen.Main -> {
            findNavController().navigate(R.id.main_fragment)
        }
    }
}
