package com.xaviertobin.bundledui.animations

import androidx.compose.animation.CapturedAnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.VisibilityThreshold
import androidx.compose.animation.core.spring
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp

const val DAMPING_RATIO_VERY_LOW = 0.75f

@Composable
fun AnimatedHorizontalVisibility(
    visible: Boolean,
    modifier: Modifier = Modifier,
    clipRadius: Dp = 28.dp,
    content: @Composable () -> Unit
) = CapturedAnimatedVisibility(
    visible = visible,
    enter = defaultHorizontalExpandSpec,
    exit = defaultHorizontalShrinkSpec,
    modifier = Modifier
        .clip(RoundedCornerShape(clipRadius))
        .then(modifier),
    content = content
)

/** Shows [content] while [value] is non-null; the exit animates its last frame. */
@Composable
fun <T : Any> AnimatedHorizontalVisibility(
    value: T?,
    modifier: Modifier = Modifier,
    clipRadius: Dp = 28.dp,
    content: @Composable (T) -> Unit
) = AnimatedHorizontalVisibility(
    visible = value != null,
    modifier = modifier,
    clipRadius = clipRadius,
) {
    value?.let { content(it) }
}

val defaultSpring = spring(
    stiffness = Spring.StiffnessMediumLow,
    dampingRatio = DAMPING_RATIO_VERY_LOW,
    visibilityThreshold = IntSize.VisibilityThreshold
)

val Spring.StiffnessDefault
    get() = 500f

val defaultHorizontalExpandSpec = fadeIn() + expandHorizontally(defaultSpring)

val defaultHorizontalShrinkSpec = shrinkHorizontally() + fadeOut()