package com.xaviertobin.bundledui.animations

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
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
    content: @Composable (AnimatedVisibilityScope.() -> Unit)
) = AnimatedVisibility(
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


@Composable
fun AnimatedFastVerticalVisibility(
    visible: Boolean,
    modifier: Modifier = Modifier,
    content: @Composable (AnimatedVisibilityScope.() -> Unit)
) = AnimatedVisibility(
    visible = visible,
    enter = fadeIn()
            + expandVertically(MaterialTheme.motionScheme.fastSpatialSpec()),
    exit = shrinkVertically(MaterialTheme.motionScheme.fastSpatialSpec())
            + fadeOut(),
    modifier = modifier,
    content = content
)