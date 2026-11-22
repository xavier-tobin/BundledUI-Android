package com.xaviertobin.bundledui.section.widgets

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.xaviertobin.bundledui.base.Tone
import com.xaviertobin.bundledui.base.UnitFunction
import com.xaviertobin.bundledui.base.containerColorForTone
import com.xaviertobin.bundledui.base.iconColorForTone
import com.xaviertobin.bundledui.base.textColorForTone
import com.xaviertobin.bundledui.base.vividContainerColorForTone
import com.xaviertobin.bundledui.base.vividTextColorForTone
import com.xaviertobin.bundledui.buttons.ButtonRow
import com.xaviertobin.bundledui.buttons.RoundedButton
import com.xaviertobin.bundledui.section.base.Section


/**
 * A box that contains a title, icon and description of an issue, hint, warning, or prompt for the user.
 * Can include a dismiss button if the alert is not long-lived.
 * Description is mandatory, but other params are optional.
 **
 * In Bundled Notes, this is used around a hint system to explain functionality
 */
@Composable
fun SectionAlert(
    title: String,
    description: String? = null,
    icon: ImageVector? = null,
    tone: Tone = Tone.NEUTRAL,
    textColor: Color = textColorForTone(tone),
    containerColor: Color = containerColorForTone(tone),
    iconColor: Color = iconColorForTone(tone),
    buttonColor: Color = vividContainerColorForTone(tone),
    dismissOptions: AlertActions? = null,
    first: Boolean = true,
    last: Boolean = true,
    primaryAction: AlertActions? = null,
) {

    Section(
        first = first,
        last = last,
        containerColor = containerColor,
        modifier = Modifier.animateContentSize(
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioLowBouncy,
                stiffness = Spring.StiffnessMedium
            )
        )
    ) {
        Row(
            modifier = Modifier
                .padding()
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = title,
                modifier = Modifier
                    .padding(
                        top = 10.dp,
                        end = 12.dp,
                        bottom = 10.dp
                    )
                    .weight(1f),
                style = MaterialTheme.typography.titleSmall,
                textAlign = TextAlign.Start,
                color = textColor,
            )

            icon?.let {
                Icon(
                    imageVector = it,
                    contentDescription = title,
                    modifier = Modifier.padding(
                        end = 3.dp
                    ).offset(x = 8.dp, y = (-2).dp),
                    tint = iconColor
                )
            }
        }

        description?.let {
            Text(
                text = description,
                modifier = Modifier
                    .padding(
                        bottom = 10.dp
                    ),
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Start,
                color = textColor,
            )
        }

        if (dismissOptions != null || primaryAction != null) {
            ButtonRow(
                paddingValues = PaddingValues(
                    bottom = 6.dp
                )
            ) {
                dismissOptions?.let { options ->
                    RoundedButton(
                        onClick = options.onClick,
                        text = options.text,
                        containerColor = if (primaryAction == null) buttonColor else Color.Transparent,
                        textColor = if (primaryAction == null) vividTextColorForTone(tone) else textColor
                    )
                }
                primaryAction?.let { action ->
                    RoundedButton(
                        onClick = action.onClick,
                        text = action.text,
                        containerColor = buttonColor,
                        textColor = vividTextColorForTone(tone)
                    )
                }
            }
        }
    }
}

data class AlertActions(
    val text: String,
    val onClick: UnitFunction,
)

@Preview
@Composable
fun SectionAlertPreview() {
    Column {
        SectionAlert(
            title = "Test"
        )

        SectionAlert(
            title = "Test 2",
            description = "This is a test description for the alert. It can be quite long, but it should still look good and be readable.",
        )

        SectionAlert(
            title = "Test 2",
            icon = Icons.Rounded.Warning,
            description = "This is a test description for the alert. It can be quite long, but it should still look good and be readable.",
        )
    }
}