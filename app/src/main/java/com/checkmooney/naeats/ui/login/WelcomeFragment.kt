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
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.checkmooney.naeats.Screen
import com.checkmooney.naeats.navigate
import com.checkmooney.naeats.ui.theme.NaEatsTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class WelcomeFragment : Fragment() {
    private val viewModel by viewModels<LoginViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            viewModel.uiState.observe(viewLifecycleOwner, { action ->
                setContent {
                    val coroutineScope = rememberCoroutineScope()
                    NaEatsTheme {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.BottomCenter
                        ) {
                            AnimatedLogo { viewModel.setNextAction(WelcomeAction.TryAutoLogin) }
                            when (action) {
                                is WelcomeAction.TryLogin -> {
                                    GoogleSignInButton(
                                        onGoogleButtonClicked = viewModel::signInAsGoogle
                                    )
                                }
                                is WelcomeAction.VerifyToken, WelcomeAction.TryAutoLogin -> {
                                    Text(
                                        text = "로그인 정보를 확인 중이예요.",
                                        modifier = Modifier.padding(bottom = 100.dp)
                                    )
                                }
                                is WelcomeAction.Finished -> {
                                    Text(
                                        text = "반갑습니다!",
                                        modifier = Modifier.padding(bottom = 100.dp)
                                    )
                                    coroutineScope.launch {
                                        delay(500) // NOTE: 너무 빨리 다음 프래그먼트로 전환되길래 delay 제공.
                                        navigate(to = Screen.Main, from = Screen.Welcome)
                                    }
                                }
                            }
                        }
                    }
                }
            })
        }
    }
}
