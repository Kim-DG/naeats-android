package com.checkmooney.naeats.ui.login

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

import com.checkmooney.naeats.R
import kotlinx.coroutines.delay


enum class LogoPosition {
    Start, Center
}

@Composable
fun AnimatedLogo(onAnimationFinished: () -> Unit = {}) {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp

    var logoState by rememberSaveable { mutableStateOf(LogoPosition.Start) }
    val offsetAnnotation = animateDpAsState(
        if (logoState == LogoPosition.Start) (0).dp else (screenHeight / 2) - 100.dp,
        spring(dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = Spring.StiffnessLow),

        )
    LaunchedEffect(true) {
        logoState = LogoPosition.Center
        delay(1000)
        onAnimationFinished()
    }
    Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            painter = painterResource(R.drawable.logo),
            contentDescription = "logo",
            modifier = Modifier
                .size(200.dp)
                .absoluteOffset(y = offsetAnnotation.value)

        )
    }
}


