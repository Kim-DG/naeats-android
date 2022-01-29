package com.checkmooney.naeats.ui.theme

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.checkmooney.naeats.R
import androidx.compose.ui.Modifier

// Set of Material typography styles to start with
val Typography = Typography(
    body1 = TextStyle(
        fontFamily = FontFamily(
            Font(
                R.font.cafe24surround_air,
                FontWeight.Normal
            )
        ),
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    /* Other default text styles to override
    button = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    caption = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
    */
)

val titleText = TextStyle(
    fontFamily = FontFamily(
        Font(
            R.font.cafe24surround_air,
            FontWeight.Normal
        )
    ),
    fontWeight = FontWeight.Normal,
    fontSize = 25.sp
)

val titleModifier = Modifier.padding(start = 24.dp, top = 64.dp, bottom =64.dp)
