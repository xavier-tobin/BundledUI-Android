package com.xaviertobin.bundledui.animations

import androidx.compose.animation.CapturedAnimatedVisibility
import androidx.compose.animation.expandIn
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkOut
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun AnimatedExpandVisibility(
    visible: Boolean,
    modifier: Modifier = Modifier,
    clipRadius: Dp = 28.dp,
    content: @Composable () -> Unit
) = CapturedAnimatedVisibility(
    visible = visible,
    enter = fadeIn()
            + expandIn(defaultSpring),
    exit = shrinkOut(defaultSpring)
            + fadeOut(),
    modifier = Modifier
        .then(modifier)
        .clip(RoundedCornerShape(clipRadius)),
    content = content
)