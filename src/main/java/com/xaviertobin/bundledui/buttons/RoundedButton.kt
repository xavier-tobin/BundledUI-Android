package com.xaviertobin.bundledui.buttons

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.xaviertobin.bundledui.base.ToggleComposable
import com.xaviertobin.bundledui.base.Tone
import com.xaviertobin.bundledui.base.UnitFunction
import com.xaviertobin.bundledui.base.vividContainerColorForTone
import com.xaviertobin.bundledui.base.vividTextColorForTone
import com.xaviertobin.bundledui.section.extras.EndIcon
import com.xaviertobin.bundledui.section.extras.LoadingOrIcon

val RoundedButtonPadding = PaddingValues(
    start = 22.dp,
    end = 22.dp,
    top = 12.dp,
    bottom = 12.dp,
)

@Composable
fun RoundedButtonBase(
    text: String,
    modifier: Modifier = Modifier,
    tone: Tone = Tone.NEUTRAL,
    containerColor: Color = vividContainerColorForTone(tone),
    textColor: Color = vividTextColorForTone(tone),
    enabled: Boolean = true,
    elevation: ButtonElevation = ButtonDefaults.buttonElevation(),
    contentPadding: PaddingValues = RoundedButtonPadding,
    endContent: (@Composable () -> Unit)? = null,
    onClick: UnitFunction?,
) {
    Button(
        enabled = enabled && onClick != null,
        onClick = { onClick?.invoke() },
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            disabledContainerColor = containerColor
        ),
        elevation = elevation,
        modifier = Modifier
            .alpha(if (enabled) 1f else 0.7f)
            .then(modifier),
        contentPadding = contentPadding,
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge,
            color = textColor,
            textAlign = TextAlign.End,
            fontSize = 15.sp,
            modifier = Modifier.padding(
                end = if (endContent == null) 0.dp else 4.dp
            )
        )
        endContent?.invoke()
    }
}


@Composable
fun RoundedLoadingButton(
    text: String,
    loadingText: String? = null,
    modifier: Modifier = Modifier,
    tone: Tone = Tone.POSITIVE,
    containerColor: Color = vividContainerColorForTone(tone),
    textColor: Color = vividTextColorForTone(tone),
    enabled: Boolean = true,
    icon: ImageVector? = null,
    loading: Boolean = false,
    onClick: UnitFunction,
) = RoundedButtonBase(
    text = if (loading && loadingText != null) loadingText else text,
    modifier = modifier,
    tone = tone,
    containerColor = containerColor,
    onClick = onClick,
    enabled = enabled && !loading,
    textColor = textColor,
    endContent = {
        LoadingOrIcon(
            loadingFromClick = loading,
            tint = textColor,
            icon = icon,
            iconDescription = text,
            tone = tone,
            size = 20.dp,
            endPadding = 0.dp
        )
    }
)


@Composable
fun RoundedButton(
    text: String,
    modifier: Modifier = Modifier,
    tone: Tone = Tone.POSITIVE,
    enabled: Boolean = true,
    elevation: ButtonElevation = ButtonDefaults.buttonElevation(),
    containerColor: Color = vividContainerColorForTone(tone),
    textColor: Color = vividTextColorForTone(tone),
    contentPadding: PaddingValues = RoundedButtonPadding,
    icon: ImageVector? = null,
    onClick: UnitFunction?,
) = RoundedButtonBase(
    text = text,
    modifier = modifier,
    tone = tone,
    containerColor = containerColor,
    contentPadding = contentPadding,
    elevation = elevation,
    onClick = onClick,
    enabled = enabled,
    textColor = textColor,
    endContent = icon?.let {
        {
            EndIcon(
                icon = icon,
                color = textColor,
                iconDescription = text,
                size = 20.dp,
                endPadding = 0.dp
            )
        }
    }
)


@Composable
fun RoundedButtonSheet(
    text: String,
    modifier: Modifier = Modifier,
    tone: Tone = Tone.NEUTRAL,
    icon: ImageVector? = null,
    elevation: ButtonElevation = ButtonDefaults.buttonElevation(),
    containerColor: Color = vividContainerColorForTone(tone),
    textColor: Color = vividTextColorForTone(tone),
    contentPadding: PaddingValues = RoundedButtonPadding,
    sheetLayout: @Composable (onDismiss: () -> Unit) -> Unit
) = ToggleComposable(
        defaultContent = { onShow ->
            RoundedButton(
                text = text,
                modifier = modifier,
                tone = tone,
                icon = icon,
                elevation = elevation,
                containerColor = containerColor,
                textColor = textColor,
                contentPadding = contentPadding
            ) {
                onShow()
            }
        },
        enabledContent = sheetLayout
    )


@Preview
@Composable
private fun RoundedButtonPreview() {
    var loading by remember { mutableStateOf(false) }
    Column {
        ButtonRow {
            RoundedLoadingButton(
                text = "Update email",
                loadingText = "Updating email",
                loading = loading,
                tone = Tone.POSITIVE
            ) {
                loading = true
            }
            RoundedButton(
                text = "Cancel",
                tone = Tone.NEUTRAL,
                icon = Icons.Rounded.Email
            ) { }
        }

        ButtonRow {
            RoundedLoadingButton(
                text = "Share info",
                loadingText = "Sharing info",
                loading = true,
            ) {
                loading = true
            }
        }
    }
}

