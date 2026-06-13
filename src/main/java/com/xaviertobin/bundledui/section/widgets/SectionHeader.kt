package com.xaviertobin.bundledui.section.widgets

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.xaviertobin.bundledui.base.ToggleComposable
import com.xaviertobin.bundledui.buttons.IconButton

@Composable
fun SectionHeader(
    @StringRes header: Int,
) {
    SectionHeader(text = stringResource(id = header))
}


@Composable
fun SectionHeader(
    text: String,
    modifier: Modifier = Modifier,
    color: Color? = null,
    endContent: @Composable (RowScope.() -> Unit)? = null
) {
    Row(
        modifier = Modifier.padding(start = 22.dp, end = 12.dp).then(modifier),
        verticalAlignment = Alignment.Bottom,
    ) {
        Text(
            modifier = Modifier.padding(bottom = 10.dp, top = 10.dp),
            text = text,
            style = MaterialTheme.typography.labelLarge,
            color = color ?: MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Start,
        )

        endContent?.invoke(this)
    }
}


@Composable
fun SectionHeaderAction(
    text: String,
    modifier: Modifier = Modifier,
    color: Color? = null,
    icon: ImageVector,
    onClick: () -> Unit,
    contentDescription: String
) {
    SectionHeader(
        text = text,
        modifier = modifier,
        color = color,
        endContent = {
            Spacer(modifier = Modifier.weight(1f))
            IconButton(icon = icon, contentDescription = contentDescription, margin = PaddingValues.Zero, onClick = onClick)
        }
    )
}


@Composable
fun SectionHeaderSheet(
    text: String,
    modifier: Modifier = Modifier,
    color: Color? = null,
    icon: ImageVector,
    enabled: Boolean = true,
    sheet: @Composable (onDismiss: () -> Unit) -> Unit
) {
    if (!enabled) {
        SectionHeader(text = text, modifier = modifier, color = color)
        return
    }
    ToggleComposable(
        defaultContent = {
            SectionHeaderAction(
                text = text,
                modifier = modifier,
                color = color,
                icon = icon,
                contentDescription = "Open $text sheet",
                onClick = it
            )
        }
    ) {
        sheet(it)
    }
}
