package com.checkmooney.naeats.util

import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.checkmooney.naeats.ui.theme.ThemePink

@Composable
fun SettingDialogForm(onCancel:() -> Unit, content: @Composable () -> Unit) {
    Dialog(onDismissRequest = onCancel) {
        Surface(
            modifier = Modifier
                .width(240.dp)
                .wrapContentHeight(), shape = RoundedCornerShape(12.dp), color = ThemePink
        ) {
            content()
        }
    }
}
