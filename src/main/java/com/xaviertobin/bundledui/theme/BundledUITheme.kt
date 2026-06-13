package com.xaviertobin.bundledui.theme

import android.app.Activity
import android.content.Context
import android.os.Build
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialExpressiveTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat


val LocalBaseTheme = compositionLocalOf { BaseTheme.DARK }

@Composable
fun BundledUITheme(
    themeSetting: BaseThemeSetting = BaseThemeSetting.LIGHT,
    themeColors: CustomMaterialYouColors? = null,
    enableMaterialYouIfAvailable: Boolean = true,
    transparentSystemBars: Boolean = true,
    invertStatusBarColorsForNonOled: Boolean = false,
    typography: @Composable (colorScheme: ColorScheme) -> Typography = { MaterialTheme.typography },
    shapes: Shapes = MaterialTheme.shapes,
    content: @Composable () -> Unit,
) {
    val context = LocalContext.current
    val theme = getBaseThemeFromSetting(themeSetting)

    val colorScheme by remember(
        key1 = theme,
        key2 = themeColors,
        key3 = enableMaterialYouIfAvailable,
    ) {
        derivedStateOf {
            getColorScheme(
                context = context,
                theme = theme,
                isMaterialYouEnabled = enableMaterialYouIfAvailable,
                customMaterialYouColors = themeColors
            )
        }
    }

    val view = LocalView.current
    if (!view.isInEditMode && transparentSystemBars) {
        SideEffect {
            val window = (view.context as Activity).window
            WindowCompat.setDecorFitsSystemWindows(window, false)
            window.statusBarColor = Color.Transparent.toArgb()
            window.navigationBarColor = Color.Transparent.toArgb()
            WindowCompat.getInsetsController(window, view).apply {
                isAppearanceLightStatusBars =
                    if (invertStatusBarColorsForNonOled && theme != BaseTheme.OLED) theme != BaseTheme.LIGHT else theme == BaseTheme.LIGHT
                isAppearanceLightNavigationBars =
                    if (invertStatusBarColorsForNonOled && theme != BaseTheme.OLED) theme != BaseTheme.LIGHT else theme == BaseTheme.LIGHT
            }
        }
    }

    CompositionLocalProvider(LocalBaseTheme provides theme) {
        MaterialExpressiveTheme(
            colorScheme = colorScheme,
            typography = typography(colorScheme),
            shapes = shapes,
            content = content,
        )
    }
}


fun getColorScheme(
    context: Context,
    isMaterialYouEnabled: Boolean,
    theme: BaseTheme,
    customMaterialYouColors: CustomMaterialYouColors? = null,
): ColorScheme {

    if (customMaterialYouColors != null) {
        return customColorScheme(
            colors = customMaterialYouColors,
            theme = theme,
        )
    }

    if (isMaterialYouEnabled && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        return when (theme) {
            BaseTheme.LIGHT -> dynamicLightColorScheme(context).adjust()
            BaseTheme.DARK -> dynamicDarkColorScheme(context).adjust()
            BaseTheme.OLED -> dynamicDarkColorScheme(context).adjustForOled()
        }
    }

    return when (theme) {
        BaseTheme.LIGHT -> defaultLightColorScheme().adjust()
        BaseTheme.DARK -> defaultDarkColorScheme().adjust()
        BaseTheme.OLED -> defaultDarkColorScheme().adjustForOled()
    }
}




