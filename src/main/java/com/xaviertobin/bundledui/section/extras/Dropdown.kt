package com.xaviertobin.bundledui.section.extras

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.xaviertobin.bundledui.base.ToggleComposable
import com.xaviertobin.bundledui.buttons.IconButton
import com.xaviertobin.bundledui.color.blend
import com.xaviertobin.bundledui.theme.elevatedSurface

@Composable
fun DropdownIcon(
    dropdownContent: @Composable ColumnScope.(onDismiss: () -> Unit) -> Unit,
) {
    DropdownComposable(
        triggerContent = { onTriggered, _ ->
            IconButton(
                icon = Icons.Rounded.MoreVert,
                contentDescription = "",
                onClick = onTriggered
            )
        },
        dropdownContent = dropdownContent
    )
}

@Composable
fun DropdownChip(
    value: String,
    defaultMargin: PaddingValues = PaddingValues(horizontal = 4.dp),
    icon: ImageVector? = null,
    dropdownContent: @Composable ColumnScope.(onDismiss: () -> Unit) -> Unit,
) {
    DropdownComposable(
        triggerContent = { onTriggered, expanded ->
            DropdownChipTrigger(value, onTriggered, expanded, defaultMargin, icon)
        },
        dropdownContent = dropdownContent
    )
}

@Composable
fun DropdownChipSheet(
    value: String,
    defaultMargin: PaddingValues = PaddingValues(horizontal = 4.dp),
    icon: ImageVector? = null,
    sheet: @Composable (onDismiss: () -> Unit) -> Unit,
) {
    ToggleComposable(
        defaultContent = { onShow ->
            DropdownChipTrigger(value, onShow, false, defaultMargin, icon)
        },
        enabledContent = sheet
    )
}

@Composable
private fun DropdownChipTrigger(
    value: String,
    onClick: () -> Unit,
    expanded: Boolean,
    margin: PaddingValues,
    icon: ImageVector?,
) {
    val shape = RoundedCornerShape(22.dp)

    val rotate by animateFloatAsState(
        targetValue = if (expanded) 180f else 0f,
        animationSpec = spring(stiffness = 400f, dampingRatio = 0.5f),
        label = "rotate"
    )

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(margin)
            .clip(shape)
            .background(
                color = MaterialTheme.colorScheme.elevatedSurface(),
            )
            .clickable(
                onClick = onClick
            )
            .padding(start = 20.dp, top = 12.dp, end = 16.dp, bottom = 12.dp)
    ) {
        icon?.let {
            Icon(
                imageVector = it,
                contentDescription = value,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .padding(end = 10.dp)
                    .size(16.dp)
            )
        }

        Text(text = value, color = MaterialTheme.colorScheme.primary, modifier = Modifier.animateContentSize())
        Icon(
            imageVector = Icons.Rounded.KeyboardArrowDown,
            contentDescription = value,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .padding(start = 4.dp)
                .size(20.dp)
                .rotate(rotate)
        )
    }
}

@Composable
fun DropdownItem(
    text: String,
    supportingText: String? = null,
    icon: ImageVector,
    onDismiss: (() -> Unit)?,
    onClick: () -> Unit,
) {
    DropdownMenuItem(
        text = {
            Text(
                text = text,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(bottom = 4.dp, end = 18.dp)
            )
        },
        supportingText = supportingText?.let {
            {
                Text(
                    text = supportingText,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(end = 18.dp)
                )
            }
        },
        onClick = {
            onClick()
            onDismiss?.invoke()
        },
        leadingIcon = {
            Icon(
                imageVector = icon,
                contentDescription = text,
                tint = MaterialTheme.colorScheme.tertiary,
                modifier = Modifier
                    .padding(horizontal = 4.dp)
                    .size(22.dp)
            )
        },
        shape = RoundedCornerShape(16.dp),
    )

}

@Composable
fun DropdownComposable(
    triggerContent: @Composable (onTriggered: () -> Unit, isExpanded: Boolean) -> Unit,
    dropdownContent: @Composable ColumnScope.(
        dismiss: () -> Unit,
    ) -> Unit,
) {

    var expanded by remember { mutableStateOf(false) }

    val surface = MaterialTheme.colorScheme.surface
    val tertiary = MaterialTheme.colorScheme.tertiary
    val backgroundColor = remember { surface.blend(tertiary, 0.1f) }

    Column {

        triggerContent({ expanded = !expanded }, expanded)

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
            },
            shape = RoundedCornerShape(22.dp),
            containerColor = backgroundColor,
            content = { dropdownContent({ expanded = false }) },
        )
    }
}


@Preview
@Composable
private fun DropdownIconPreview() {

    Column(
        verticalArrangement = Arrangement.spacedBy(0.dp),
        modifier = Modifier.size(400.dp)
    ) {

        DropdownIcon {
            Text("Option 1")
            Text("Option 2")
            Text("Option 3")
        }


        DropdownChip("For all bundles") {
            Text("Option 1")
            Text("Option 2")
            Text("Option 3")
        }

    }

}