package com.checkmooney.naeats.ui.login

import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidViewBinding
import com.checkmooney.naeats.databinding.GoogleLoginButtonBinding
import com.google.android.gms.common.SignInButton

@Composable
fun GoogleSignInButton(onGoogleButtonClicked: () -> Unit = {}) {
    AndroidViewBinding(
        GoogleLoginButtonBinding::inflate
    ) {
        signInButton.setSize(SignInButton.SIZE_WIDE)
        signInButton.setOnClickListener { onGoogleButtonClicked() }
        signInButton.setPadding(0, 0, 0, 100)
    }
}
