package com.xaviertobin.bundledui.section.widgets


import androidx.annotation.StringRes
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ChevronRight
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.xaviertobin.bundledui.base.Tone
import com.xaviertobin.bundledui.base.UnitFunction
import com.xaviertobin.bundledui.section.base.SectionDefaults
import com.xaviertobin.bundledui.section.base.SectionOrientation
import com.xaviertobin.bundledui.section.base.sectionTextColorForTone
import com.xaviertobin.bundledui.section.extras.LoadingOrIcon


@Composable
fun ButtonChevron(
    title: String,
    selected: Boolean? = null,
    tone: Tone = Tone.NEUTRAL,
    loadingFromClick: Boolean = false,
) = LoadingOrIcon(
    loadingFromClick = loadingFromClick,
    icon = Icons.Rounded.ChevronRight,
    tone = tone,
    size = 20.dp,
    iconDescription = title,
    tint = SectionDefaults.iconColor(
        selected = selected == true,
        tone = tone
    ),
    endPadding = 0.dp,
    alpha = 0.5f
)

@Composable
fun SectionButton(
    title: String,
    icon: ImageVector,
    modifier: Modifier = Modifier,
    description: String? = null,
    first: Boolean = false,
    last: Boolean = false,
    selected: Boolean? = null,
    tone: Tone = Tone.NEUTRAL,
    orientation: SectionOrientation = SectionOrientation.VERTICAL,
    containerColor : Color = SectionDefaults.containerColor(
        selected = selected,
        focused = false,
        tone = tone
    ),
    margin: PaddingValues = SectionDefaults.marginValues(
        orientation = orientation,
        last = last
    ),
    textColor: Color = sectionTextColorForTone(selected, tone),
    loadingFromClick: Boolean = false,
    onLongClick: UnitFunction? = null,
    onClick: UnitFunction,
) = SectionTitleDescription(
    first = first,
    last = last,
    onClick = onClick,
    selected = selected,
    modifier = modifier,
    title = title,
    description = description,
    onLongClick = onLongClick,
    tone = tone,
    containerColor = containerColor,
    textColor = textColor,
    orientation = orientation,
    margin = margin,
    contentEnd = {
       ButtonChevron(
           title = title,
           selected = selected,
           tone = tone,
           loadingFromClick = loadingFromClick
       )
    },
    contentStart = {
        Icon(
            imageVector = icon,
            contentDescription = title,
            tint = SectionDefaults.iconColor(
                selected = selected == true,
                tone = tone
            ),
            modifier = Modifier.padding(end = if (orientation == SectionOrientation.HORIZONTAL) 0.dp else 16.dp)
        )
    }
)


@Composable
fun SectionButton(
    title: String,
    @StringRes icon: Int,
    modifier: Modifier = Modifier,
    description: String? = null,
    first: Boolean = false,
    last: Boolean = false,
    selected: Boolean? = null,
    tone: Tone = Tone.NEUTRAL,
    orientation: SectionOrientation = SectionOrientation.VERTICAL,
    containerColor : Color = SectionDefaults.containerColor(
        selected = selected,
        focused = false,
        tone = tone
    ),
    margin: PaddingValues = SectionDefaults.marginValues(
        orientation = orientation,
        last = last
    ),
    textColor: Color = sectionTextColorForTone(selected, tone),
    loadingFromClick: Boolean = false,
    onLongClick: UnitFunction? = null,
    onClick: UnitFunction,
) = SectionTitleDescription(
    first = first,
    last = last,
    onClick = onClick,
    selected = selected,
    modifier = modifier,
    title = title,
    description = description,
    onLongClick = onLongClick,
    tone = tone,
    containerColor = containerColor,
    textColor = textColor,
    orientation = orientation,
    margin = margin,
    contentEnd = {
        ButtonChevron(
            title = title,
            selected = selected,
            tone = tone,
            loadingFromClick = loadingFromClick
        )
    },
    contentStart = {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = title,
            tint = SectionDefaults.iconColor(
                selected = selected == true,
                tone = tone
            ),
            modifier = Modifier.padding(end = if (orientation == SectionOrientation.HORIZONTAL) 0.dp else 18.dp)
        )
    }
)

@Composable
fun RowScope.SectionButton(
    title: String,
    icon: ImageVector,
    modifier: Modifier = Modifier,
    description: String? = null,
    first: Boolean = false,
    last: Boolean = false,
    selected: Boolean? = null,
    tone: Tone = Tone.NEUTRAL,
    containerColor : Color = SectionDefaults.containerColor(
        selected = selected,
        focused = false,
        tone = tone
    ),
    textColor: Color = sectionTextColorForTone(selected, tone),
    loadingFromClick: Boolean = false,
    onLongClick: UnitFunction? = null,
    onClick: UnitFunction,
) = SectionButton(
    title = title,
    icon = icon,
    modifier = modifier,
    description = description,
    first = first,
    last = last,
    selected = selected,
    tone = tone,
    orientation = SectionOrientation.HORIZONTAL,
    containerColor = containerColor,
    textColor = textColor,
    loadingFromClick = loadingFromClick,
    onLongClick = onLongClick,
    onClick = onClick
)
