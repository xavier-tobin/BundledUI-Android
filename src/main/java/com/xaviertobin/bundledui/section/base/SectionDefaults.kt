package com.xaviertobin.bundledui.section.base

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.xaviertobin.bundledui.base.Tone
import com.xaviertobin.bundledui.base.containerColorForTone
import com.xaviertobin.bundledui.base.firstLastCornersHorizontal
import com.xaviertobin.bundledui.base.firstLastCornersVertical
import com.xaviertobin.bundledui.base.iconColorForTone
import com.xaviertobin.bundledui.base.textColorForTone
import com.xaviertobin.bundledui.color.blend

object SectionDefaults {

    /**
     * PADDING
     */

    fun paddingValues(
        orientation: SectionOrientation,
        first: Boolean,
        last: Boolean,
    ): PaddingValues {
        return when (orientation) {
            SectionOrientation.VERTICAL -> verticalPaddingValues(first, last)
            SectionOrientation.HORIZONTAL -> horizontalPaddingValues(first, last)
        }
    }

    fun verticalPaddingValues(
        first: Boolean = false,
        last: Boolean = false,
        top: Dp = 10.dp,
        bottom: Dp = 10.dp,
        start: Dp = 26.dp,
        end: Dp = 26.dp,
        firstLastExtra: Dp = 2.dp,
    ) = PaddingValues(
        start = start,
        end = end,
        top = if (first) top + firstLastExtra else top,
        bottom = if (last) bottom + firstLastExtra else bottom,
    )

    fun horizontalPaddingValues(
        first: Boolean = false,
        last: Boolean = false,
        firstLastExtra: Dp = 4.dp,
        top: Dp = 12.dp,
        bottom: Dp = 12.dp,
        start: Dp = 20.dp,
        end: Dp = 20.dp
    ) = PaddingValues(
        start = if (first) start + firstLastExtra else start,
        end = if (last) end + firstLastExtra else end,
        top = top,
        bottom = bottom,
    )

    /**
     * MARGIN
     */

    @Composable
    fun marginValues(orientation: SectionOrientation, last: Boolean): PaddingValues {
        return when (orientation) {
            SectionOrientation.VERTICAL -> verticalMarginValues(last)
            SectionOrientation.HORIZONTAL -> horizontalMarginValues()
        }
    }

    @Composable
    fun verticalMarginValues(
        last: Boolean = false,
    ): PaddingValues {
        val result by animateDpAsState(if (last) 16.dp else 1.5.dp, label = "verticalMarginValues")
        return PaddingValues(
            bottom = result,
            top = 1.5.dp
        )
    }

    fun horizontalMarginValues() = PaddingValues(
        end = 3.dp,
        start = 0.dp,
        bottom = 16.dp
    )

    /**
     * COLOR
     */

    @Composable
    fun containerColor(focused: Boolean, tone: Tone, selected: Boolean? = null): Color {

        val animatedColor by animateColorAsState(
            targetValue = if (selected == true) {
                MaterialTheme.colorScheme.tertiary
            } else if (focused || selected == false) {
                MaterialTheme.colorScheme.tertiaryTintedSurface()
            } else {
                containerColorForTone(tone)
            },
        )

        return animatedColor
    }

    @Composable
    fun iconColor(
        tone: Tone,
        selected: Boolean
    ): Color {
        return if (selected) {
            MaterialTheme.colorScheme.surface
        } else {
            iconColorForTone(tone)
        }
    }

    /**
     * CORNERS
     */

    @Composable
    fun shape(orientation: SectionOrientation, first: Boolean, last: Boolean): RoundedCornerShape {
        return when (orientation) {
            SectionOrientation.VERTICAL -> firstLastCornersVertical(first, last)
            SectionOrientation.HORIZONTAL -> firstLastCornersHorizontal(first, last)
        }
    }

}

fun ColorScheme.tertiaryTintedSurface(): Color {
    return surface.blend(tertiary, by = 0.2f)
}

enum class SectionOrientation {
    VERTICAL,
    HORIZONTAL
}


@Composable
fun sectionTextColorForTone(selected: Boolean?, tone: Tone): Color {

    val animatedColor by animateColorAsState(
        targetValue = if (selected == true) {
            MaterialTheme.colorScheme.surface
        } else {
            textColorForTone(tone)
        },
        animationSpec = tween(durationMillis = 200)
    )

    return animatedColor

}
