package com.checkmooney.naeats.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.checkmooney.naeats.R
import com.checkmooney.naeats.ui.theme.NaEatsTheme

class WelcomeFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                NaEatsTheme {
                    WelcomeScreen()
                    // findNavController().navigate(R.id.action_welcome_to_main)
                }
            }
        }
    }
}
