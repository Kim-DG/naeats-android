package com.checkmooney.naeats.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material.Button
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.checkmooney.naeats.Screen
import com.checkmooney.naeats.databinding.GoogleLoginButtonBinding
import com.checkmooney.naeats.navigate
import com.checkmooney.naeats.ui.main.MainViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private val viewModel by viewModels<LoginViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        var mGoogleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val adf = GoogleLoginButtonBinding.inflate(inflater, container, false)
        return ComposeView(requireContext()).apply {
            viewModel.isLoggedIn.observe(viewLifecycleOwner, Observer { loggedIn ->
                if (loggedIn) {
                    navigate(Screen.Login, Screen.Main)
                } else {
                    setContent {
                        SignInScreen(onGoogleButtonClicked = viewModel::signInAsGoogle )
                    }
                }
            })
        }
    }
}
