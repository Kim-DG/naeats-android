package com.checkmooney.naeats.ui.main.setting

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.checkmooney.naeats.R
import com.checkmooney.naeats.data.entities.UserProfile
import com.checkmooney.naeats.ui.theme.*
import com.skydoves.landscapist.glide.GlideImage


@Composable
fun MyInfo(
    userInfo: UserProfile,
    onLogoutSelected: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .padding(32.dp)
            .fillMaxWidth()
            .height(100.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val openDialog = rememberSaveable { mutableStateOf(false) }
        GlideImage(
            imageModel = userInfo.profileImg,
            modifier = Modifier.weight(0.3f),
            contentScale = ContentScale.FillHeight
        )
        Column(modifier = Modifier.weight(1f), horizontalAlignment = Alignment.End) {
            Text(
                text = userInfo.username,
                fontSize = 16.sp, color = TextGrey, textAlign = TextAlign.End
            )
            Spacer(modifier = Modifier.padding(5.dp))
            Text(
                text = userInfo.email,
                fontSize = 14.sp, color = TextGrey, textAlign = TextAlign.End
            )
            Spacer(modifier = Modifier.padding(10.dp))
            Button(
                onClick = { openDialog.value = true },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = ThemePink,
                    contentColor = ThemeGrey
                ),
                border = BorderStroke(width = 0.5.dp, color = ChoicePink)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.logout),
                    contentDescription = "Log-out",
                    modifier = Modifier.size(ButtonDefaults.IconSize)
                )
                Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                Text("LOGOUT")
            }
        }

        if (openDialog.value) {
            SettingDialogForm(openDialog) { LogOutDialogContent(openDialog, onLogoutSelected) }
        }
    }
}

@Composable
fun LogOutDialogContent(openDialog: MutableState<Boolean>, onLogoutSelected: () -> Unit) {
    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(
            modifier = Modifier
                .height(24.dp)
        )
        Text(
            "로그아웃하시겠습니까?", textAlign = TextAlign.Center, modifier = Modifier
                .padding(vertical = 8.dp)
                .wrapContentSize(), letterSpacing = 1.5.sp, color = TextGrey
        )
        Spacer(
            modifier = Modifier
                .height(12.dp)
        )
        Icon(
            painter = painterResource(id = R.drawable.check),
            contentDescription = "dialog check",
            tint = CheckBlue,
            modifier = Modifier
                .clickable(onClick = {
                    openDialog.value = false
                    onLogoutSelected()
                })
        )
        Spacer(
            modifier = Modifier
                .height(16.dp)
        )
    }
}
