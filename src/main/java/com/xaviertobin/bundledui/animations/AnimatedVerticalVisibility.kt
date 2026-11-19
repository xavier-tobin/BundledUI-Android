package com.xaviertobin.bundledui.animations

import androidx.compose.animation.CapturedAnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun AnimatedVerticalVisibility(
    visible: Boolean,
    modifier: Modifier = Modifier,
    clipRadius: Dp = 0.dp,
    content: @Composable () -> Unit
) = CapturedAnimatedVisibility(
    visible = visible,
    enter = fadeIn()
            + expandVertically(MaterialTheme.motionScheme.defaultSpatialSpec()),
    exit = shrinkVertically(MaterialTheme.motionScheme.defaultSpatialSpec())
            + fadeOut(),
    modifier = Modifier
        .then(modifier)
        .clip(RoundedCornerShape(clipRadius)),
    content = content
)

/** Shows [content] while [visible] is non-null; the exit animates its last frame. */
@Composable
fun <T : Any> AnimatedVerticalVisibility(
    visible: T?,
    modifier: Modifier = Modifier,
    clipRadius: Dp = 0.dp,
    content: @Composable (T) -> Unit
) = AnimatedVerticalVisibility(
    visible = visible != null,
    modifier = modifier,
    clipRadius = clipRadius,
) {
    visible?.let { content(it) }
}


@Composable
fun AnimatedFastVerticalVisibility(
    visible: Boolean,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) = CapturedAnimatedVisibility(
    visible = visible,
    enter = fadeIn()
            + expandVertically(MaterialTheme.motionScheme.fastSpatialSpec()),
    exit = shrinkVertically(MaterialTheme.motionScheme.fastSpatialSpec())
            + fadeOut(),
    modifier = modifier,
    content = content
)