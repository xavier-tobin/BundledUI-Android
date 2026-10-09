package com.xaviertobin.bundledui.animations

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.VisibilityThreshold
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridItemScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.IntOffset

val placementSpec = spring(
    dampingRatio = Spring.DampingRatioNoBouncy,
    stiffness = Spring.StiffnessMedium,
    visibilityThreshold = IntOffset.VisibilityThreshold
)

val fadeInSpec = tween<Float>(
    durationMillis = 150
)

val fadeOutSpec = tween<Float>(
    durationMillis = 150
)

fun LazyStaggeredGridItemScope.itemAnimations(): Modifier {
    return Modifier
        .animateItem(
            fadeInSpec = fadeInSpec,
            placementSpec = null,
            fadeOutSpec = fadeOutSpec
        )
}

fun LazyItemScope.itemAnimations(): Modifier {
    return Modifier
        .animateItem(
            fadeInSpec = fadeInSpec,
            placementSpec = null,
            fadeOutSpec = fadeOutSpec
        )
}


public fun LazyItemScope.springItemAnimations(): Modifier =
    Modifier.animateItem(
        fadeInSpec = spring(stiffness = Spring.StiffnessMedium),
        placementSpec =
            spring(
                stiffness = Spring.StiffnessMedium,
                visibilityThreshold = IntOffset.VisibilityThreshold,
            ),
        fadeOutSpec = spring(stiffness = Spring.StiffnessMedium),
    )

