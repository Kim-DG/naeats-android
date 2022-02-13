package com.checkmooney.naeats.ui.login

import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidViewBinding
import com.checkmooney.naeats.databinding.GoogleLoginButtonBinding
import com.google.android.gms.common.SignInButton

@Preview
@Composable
fun SignInScreen(onGoogleButtonClicked: () -> Unit = {}) {
    AndroidViewBinding(
        GoogleLoginButtonBinding::inflate,
        modifier = Modifier.clickable { onGoogleButtonClicked() }) {
        signInButton.setSize(SignInButton.SIZE_WIDE)
    }
}
