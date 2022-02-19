package com.checkmooney.naeats.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.checkmooney.naeats.ui.theme.NaEatsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WelcomeFragment : Fragment() {
    private val viewModel by viewModels<LoginViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            viewModel.uiState.observe(viewLifecycleOwner, {
                setContent {
                    NaEatsTheme {
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
                            AnimatedLogo { viewModel.setNextAction(WelcomeAction.TryLogin) }

                            if (it.action == WelcomeAction.TryLogin) {
                                GoogleSignInButton(
                                    onGoogleButtonClicked = viewModel::signInAsGoogle
                                )
                            }
                        }

                    }
                }
            })
        }
    }
}
