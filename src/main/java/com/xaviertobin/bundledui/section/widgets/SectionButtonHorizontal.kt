package com.xaviertobin.bundledui.section.widgets

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.xaviertobin.bundledui.base.UnitFunction
import com.xaviertobin.bundledui.base.firstLastCornersChip
import com.xaviertobin.bundledui.section.base.Section
import com.xaviertobin.bundledui.theme.text


// TODO remove in favour of using SectionButton with orientation = horizontal
@Composable
fun SectionButtonHorizontal(
    title: String,
    description: String? = null,
    icon: ImageVector,
    first: Boolean = false,
    last: Boolean = false,
    selected: Boolean = false,
    warning: Boolean = false,
    onClick: UnitFunction,
) {

    val textColor = if (selected) {
        MaterialTheme.colorScheme.surface
    } else if (warning) {
        MaterialTheme.colorScheme.error
    } else {
        MaterialTheme.colorScheme.text
    }

    val selectedExtraPadding by animateDpAsState(
        targetValue = if (selected) 5.dp else 0.dp,
        label = "selectedExtraPadding",
    )

    Section(
        first = first,
        last = last,
        onClick = onClick,
        containerColor = if (selected) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.surfaceColorAtElevation(12.dp),
        padding = PaddingValues(
            start = if (first) 24.dp else 20.dp,
            end = if (last) 24.dp else 20.dp,
            top = 12.dp,
            bottom = 12.dp,
        ),
        margin = PaddingValues(
            end = 4.dp + selectedExtraPadding,
            start = selectedExtraPadding,
            top = 2.dp,
            bottom = 16.dp,
        ),
        shape = firstLastCornersChip(
            first = first,
            last = last,
            pronouncedCornerRadius = 28.dp,
            defaultCornerRadius = 6.dp
        )
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .size(20.dp),
                imageVector = icon,
                contentDescription = description ?: title,
                tint = if (warning) MaterialTheme.colorScheme.error else if (selected) textColor else MaterialTheme.colorScheme.tertiary
            )
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge,
                color = textColor,
                textAlign = TextAlign.Start,
            )
            description?.let {
                Text(
                    modifier = Modifier
                        .padding(top = 0.dp),
                    text = description,
                    style = MaterialTheme.typography.bodySmall,
                    color = textColor,
                    textAlign = TextAlign.Start,
                    fontSize = 11.sp
                )
            }
        }
    }
}
