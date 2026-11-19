package com.xaviertobin.bundledui.animations

import androidx.compose.animation.CapturedAnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable

@Composable
fun AnimateInFade(
    visible: Boolean,
    durationMillis: Int = 300,
    content: @Composable () -> Unit
) = CapturedAnimatedVisibility(
    visible = visible,
    enter = fadeIn(tween(durationMillis)),
    exit = fadeOut(tween(durationMillis)),
    content = content
)