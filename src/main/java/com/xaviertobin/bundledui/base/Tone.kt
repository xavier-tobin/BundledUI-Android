package com.xaviertobin.bundledui.base

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.xaviertobin.bundledui.color.blend
import com.xaviertobin.bundledui.theme.elevatedSurface
import com.xaviertobin.bundledui.theme.text

enum class Tone {
    NEGATIVE, WARNING, NEUTRAL, POSITIVE,
}

val baseWarningColor = Color(
    0xffffb624
)


fun ColorScheme.textWarningColor() = text.blend(
    to = baseWarningColor,
    by = 0.2f
)


fun ColorScheme.textErrorColor() = text.blend(
    to = error,
    by = 0.5f
)


fun ColorScheme.vividWarningSurfaceColor() = elevatedSurface().blend(
    to = baseWarningColor,
    by = 0.7f
)


fun ColorScheme.vividErrorSurfaceColor() = elevatedSurface().blend(
    to = error,
    by = 0.73f
)


@Composable
fun vividTextColorForTone(tone: Tone) = when (tone) {
    Tone.POSITIVE -> MaterialTheme.colorScheme.surface
    Tone.NEUTRAL -> MaterialTheme.colorScheme.primary
    Tone.NEGATIVE -> MaterialTheme.colorScheme.surface
    Tone.WARNING -> MaterialTheme.colorScheme.surface
}

// Vivid container colours are for small, primary, eye-catching components - like a button
@Composable
fun vividContainerColorForTone(tone: Tone) = when (tone) {
    Tone.POSITIVE -> MaterialTheme.colorScheme.tertiary
    Tone.NEUTRAL -> MaterialTheme.colorScheme.surfaceColorAtElevation(12.dp)
    Tone.NEGATIVE -> MaterialTheme.colorScheme.vividErrorSurfaceColor()
    Tone.WARNING -> MaterialTheme.colorScheme.vividWarningSurfaceColor()
}

// Standard container colours are for large surface components - like Sections
@Composable
fun containerColorForTone(tone: Tone) = when (tone) {
    Tone.POSITIVE -> MaterialTheme.colorScheme.primary
    Tone.NEUTRAL -> MaterialTheme.colorScheme.surfaceColorAtElevation(12.dp)
    Tone.NEGATIVE -> MaterialTheme.colorScheme.surfaceColorAtElevation(4.dp).blend(
        to = Color.Red,
        by = 0.08f
    )
    Tone.WARNING -> MaterialTheme.colorScheme.surfaceColorAtElevation(6.dp).blend(
        to = baseWarningColor,
        by = 0.11f
    )
}


@Composable
fun textColorForTone(tone: Tone) = when (tone) {
    Tone.POSITIVE -> MaterialTheme.colorScheme.surface
    Tone.NEUTRAL -> MaterialTheme.colorScheme.text
    Tone.NEGATIVE -> MaterialTheme.colorScheme.textErrorColor()
    Tone.WARNING -> MaterialTheme.colorScheme.textWarningColor()
}

@Composable
fun iconColorForTone(tone: Tone) = when (tone) {
    Tone.POSITIVE -> MaterialTheme.colorScheme.surface
    Tone.NEUTRAL -> MaterialTheme.colorScheme.tertiary
    Tone.NEGATIVE -> MaterialTheme.colorScheme.textErrorColor()
    Tone.WARNING -> MaterialTheme.colorScheme.textWarningColor()
}