package com.xaviertobin.bundledui.section.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.xaviertobin.bundledui.base.ComposableFunction
import com.xaviertobin.bundledui.base.Tone
import com.xaviertobin.bundledui.base.UnitFunction
import com.xaviertobin.bundledui.color.alpha
import com.xaviertobin.bundledui.section.base.Section
import com.xaviertobin.bundledui.section.base.SectionDefaults
import com.xaviertobin.bundledui.section.base.SectionOrientation
import com.xaviertobin.bundledui.section.base.sectionTextColorForTone


@Composable
fun SectionTitleDescriptionIcon(
    title: String,
    modifier: Modifier = Modifier,
    description: String? = null,
    first: Boolean = false,
    last: Boolean = false,
    onClick: UnitFunction? = null,
    selected: Boolean? = null,
    tone: Tone = Tone.NEUTRAL,
    containerColor: Color = SectionDefaults.containerColor(
        selected = selected,
        focused = false,
        tone = tone
    ),
    textColor: Color = sectionTextColorForTone(selected, tone),
    enabled: Boolean = true,
    icon: ImageVector,
) = SectionTitleDescription(
    first = first,
    last = last,
    selected = selected,
    modifier = modifier,
    title = title,
    description = description,
    onClick = onClick,
    enabled = enabled,
    tone = tone,
    containerColor = containerColor,
    textColor = textColor,
    contentStart = {
        Icon(
            icon,
            contentDescription = title,
            tint = SectionDefaults.iconColor(
                selected = selected == true,
                tone = tone
            ),
            modifier = Modifier.padding(end = 18.dp)
        )
    }
)


@Composable
fun SectionTitleDescription(
    title: String,
    modifier: Modifier = Modifier,
    description: String? = null,
    first: Boolean = false,
    last: Boolean = false,
    selected: Boolean? = null,
    tone: Tone = Tone.NEUTRAL,
    containerColor: Color = SectionDefaults.containerColor(
        selected = selected,
        focused = false,
        tone = tone
    ),
    orientation: SectionOrientation = SectionOrientation.VERTICAL,
    textColor: Color = sectionTextColorForTone(selected, tone),
    contentStart: ComposableFunction? = null,
    contentEnd: ComposableFunction? = null,
    contentTop: ComposableFunction? = null,
    contentBottom: ComposableFunction? = null,
    padding: PaddingValues = when (orientation) {
        SectionOrientation.VERTICAL -> SectionDefaults.verticalPaddingValues(
            first = first,
            last = last,
            top = 0.dp,
            bottom = 0.dp
        )
        SectionOrientation.HORIZONTAL -> SectionDefaults.horizontalPaddingValues(
            first = first,
            last = last,
            top = 12.dp,
            bottom = 12.dp,
            start = 20.dp,
            end = 20.dp,
            firstLastExtra = 8.dp
        )
    },
    margin : PaddingValues = SectionDefaults.marginValues(
        orientation = orientation,
        last = last
    ),
    enabled: Boolean = true,
    onClick: UnitFunction? = null,
    onLongClick: UnitFunction? = null,
) = Section(
    first = first,
    last = last,
    onClick = onClick,
    onLongClick = onLongClick,
    selected = selected,
    tone = tone,
    modifier = modifier,
    enabled = enabled,
    margin = margin,
    padding = padding,
    containerColor = containerColor,
    orientation = orientation,
) {
    when (orientation) {
        SectionOrientation.VERTICAL -> InnerVerticalContent(
            title = title,
            description = description,
            textColor = textColor,
            contentStart = contentStart,
            contentEnd = contentEnd,
            contentTop = contentTop,
            contentBottom = contentBottom,
        )

        SectionOrientation.HORIZONTAL -> InnerHorizontalContent(
            title = title,
            description = description,
            textColor = textColor,
            contentTop = contentStart,
            contentBottom = contentBottom,
        )
    }
}


@Composable
private fun InnerVerticalContent(
    title: String,
    description: String?,
    textColor: Color,
    contentStart: ComposableFunction?,
    contentEnd: ComposableFunction?,
    contentTop: ComposableFunction?,
    contentBottom: ComposableFunction?,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        contentStart?.invoke()
        Column(
            modifier = Modifier
                .defaultMinSize(minHeight = 42.dp)
                .padding(
                    start = 0.dp,
                    end = 0.dp,
                    top = 10.dp,
                    bottom = 10.dp
                )
                .weight(1f),
            verticalArrangement = Arrangement.Center
        ) {
            contentTop?.invoke()
            SectionTitle(
                title = title,
                textColor = textColor,
                padding = PaddingValues(bottom = 1.dp, top = 2.dp)
            )
            description?.let {
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Normal,
                    color = textColor.alpha(0.9f),
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .padding(bottom = 2.dp)
                )
            }
            contentBottom?.invoke()
        }
        contentEnd?.invoke()
    }
}


@Composable
private fun ColumnScope.InnerHorizontalContent(
    title: String,
    description: String?,
    textColor: Color,
    contentTop: ComposableFunction?,
    contentBottom: ComposableFunction?,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        contentTop?.invoke()
        SectionTitle(
            title = title,
            textColor = textColor,
            padding = PaddingValues()
        )
        description?.let {
            Text(
                text = description,
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Normal,
                color = textColor.alpha(0.9f),
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .padding(bottom = 2.dp)
            )
        }
        contentBottom?.invoke()
    }
}


@Composable
fun SectionTitle(title: String, textColor: Color, padding: PaddingValues) {
    Text(
        text = title,
        style = MaterialTheme.typography.bodyLarge,
        textAlign = TextAlign.Start,
        modifier = Modifier
            .padding(padding),
        color = textColor
    )
}


@Preview
@Composable
fun SectionTitleDescriptionsPreview() {
    var toggledOn1 by remember { mutableStateOf(false) }
    var toggledOn2 by remember { mutableStateOf(false) }

    Column {

        SectionButton(title = "Button", icon = Icons.Rounded.Add, first = true) { }

        SectionTitleDescriptionIcon(title = "Button", icon = Icons.Rounded.Add)
        SectionSwitch(
            title = "Enable feature",
            checked = toggledOn1,
            onChecked = { toggledOn1 = it },
        )

        SectionSwitch(
            title = "Enable a different feature",
            description = "This is a description",
            checked = toggledOn2,
            onChecked = { toggledOn2 = it },
            last = true
        )

    }
}