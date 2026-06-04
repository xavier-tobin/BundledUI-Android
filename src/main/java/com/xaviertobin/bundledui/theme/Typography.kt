package com.xaviertobin.bundledui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

fun buildCustomTypography(
    colorScheme: ColorScheme,
    font: FontFamily?,
    fontScale: Float
): Typography {
    return Typography(
        displayLarge = TextStyle(
            fontFamily = font,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp * fontScale,
            color = colorScheme.text
        ),
        displayMedium = TextStyle(
            fontFamily = font,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp * fontScale,
            color = colorScheme.text
        ),
        displaySmall = TextStyle(
            fontFamily = font,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp * fontScale,
            color = colorScheme.text
        ),
        headlineLarge = TextStyle(
            fontFamily = font,
            fontWeight = FontWeight.SemiBold,
            fontSize = 30.sp * fontScale,
            color = colorScheme.text
        ),
        headlineMedium = TextStyle(
            fontFamily = font,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp * fontScale,
            color = colorScheme.text
        ),
        headlineSmall = TextStyle(
            fontFamily = font,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp * fontScale,
            color = colorScheme.text
        ),
        titleLarge = TextStyle(
            fontFamily = font,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp * fontScale,
            color = colorScheme.text
        ),
        titleMedium = TextStyle(
            fontFamily = font,
            fontWeight = FontWeight.SemiBold,
            fontSize = 18.sp * fontScale,
            color = colorScheme.text
        ),
        titleSmall = TextStyle(
            fontFamily = font,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp * fontScale,
            color = colorScheme.text
        ),
        bodyLarge = TextStyle(
            fontFamily = font,
            fontWeight = FontWeight.Medium,
            fontSize = 14.5.sp * fontScale,
            color = colorScheme.text
        ),
        bodyMedium = TextStyle(
            fontFamily = font,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp * fontScale,
            color = colorScheme.text,
        ),
        bodySmall = TextStyle(
            fontFamily = font,
            fontWeight = FontWeight.Normal,
            fontSize = 13.sp * fontScale,
            color = colorScheme.text,
        ),
        labelLarge = TextStyle(
            fontFamily = font,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp * fontScale,
            color = colorScheme.text
        ),
        labelMedium = TextStyle(
            fontFamily = font,
            fontWeight = FontWeight.Medium,
            fontSize = 13.sp * fontScale,
            color = colorScheme.text
        ),
        labelSmall = TextStyle(
            fontFamily = font,
            fontWeight = FontWeight.Medium,
            fontSize = 12.sp * fontScale,
            color = colorScheme.text
        ),
    )
}