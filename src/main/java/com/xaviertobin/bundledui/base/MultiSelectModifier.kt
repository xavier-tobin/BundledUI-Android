package com.xaviertobin.bundledui.base

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.border
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

val CardShape = RoundedCornerShape(14.dp)

@Composable
fun Modifier.multiSelected(
    selected: Boolean,
    outlineColor: Color = MaterialTheme.colorScheme.primary,
    shape: Shape? = null
): Modifier {

    val animation by animateFloatAsState(
        targetValue = if (selected) 1f else 0f,
        label = "Selected Scale Animation",
        animationSpec = spring(
            stiffness = 1000f,
            dampingRatio = Spring.DampingRatioMediumBouncy,
        )
    )

    val painter = rememberVectorPainter(image = Icons.Rounded.Check)

    val scale = 1f - (animation * 0.1f)

    val textColor = MaterialTheme.colorScheme.surface
    val tint = remember { ColorFilter.tint(textColor) }

    val border = 3.dp * animation
    val radius = 11.dp * animation

    return this
        .drawWithContent {
            drawContent()
            if (selected) {

                val partialBorder = (border / 1.5f).toPx()
                val centreX = (this.size.width * 0.95f) - partialBorder
                val centreY = this.size.height / 2f

                // Draw circle tick badge on top right corner
                val radius = radius.toPx()
                drawCircle(
                    color = outlineColor,
                    radius = radius,
                    center = Offset(
                        x = centreX,
                        y = centreY
                    )
                )

                // tick vector icon
                translate(
                    left = centreX - radius + partialBorder,
                    top = centreY - radius + partialBorder
                ) {
                    with(painter) {
                        draw(
                            size = painter.intrinsicSize.times(animation.coerceAtLeast(0f) * 0.75f),
                            colorFilter = tint
                        )
                    }
                }


            }
        }
        .graphicsLayer(
            scaleX = scale,
            scaleY = scale,
            alpha = scale
        )
        .then(
            if (selected && shape != null) {
                Modifier.border(
                    width = border,
                    color = outlineColor,
                    shape = shape
                )
            } else {
                Modifier
            }
        )
}


