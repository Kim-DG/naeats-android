package com.checkmooney.naeats.ui.login

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

import com.checkmooney.naeats.R
import com.checkmooney.naeats.ui.theme.ThemePink
import kotlinx.coroutines.delay


enum class LogoPosition {
    Start, Center
}

@Composable
fun AnimatedLogo(onAnimationFinished: () -> Unit = {}) {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp

    var upLogoState by rememberSaveable { mutableStateOf(LogoPosition.Start) }
    var downLogoState by rememberSaveable { mutableStateOf(LogoPosition.Start) }
    val naOffsetAnnotation = animateDpAsState(
        if (upLogoState == LogoPosition.Start) (-150).dp else (screenHeight / 2) - 75.dp,
        spring(dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = Spring.StiffnessLow),
    )
    val eatsOffsetAnnotation = animateDpAsState(
        if (downLogoState == LogoPosition.Start) (-150).dp else (screenHeight / 2) - 65.dp,
        spring(dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = Spring.StiffnessLow),
    )
    LaunchedEffect(true){
        downLogoState = LogoPosition.Center
        delay(1000)
        upLogoState = LogoPosition.Center
        onAnimationFinished()
    }
    Column(
        Modifier
            .fillMaxSize()
            .background(color = ThemePink), horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            painter = painterResource(R.drawable.logo_na),
            contentDescription = "logo",
            modifier = Modifier
                .width(150.dp)
                .absoluteOffset(y = naOffsetAnnotation.value)
        )
        Image(
            painter = painterResource(R.drawable.logo_eats),
            contentDescription = "logo",
            modifier = Modifier
                .width(150.dp)
                .absoluteOffset(y = eatsOffsetAnnotation.value)

        )
    }
}


