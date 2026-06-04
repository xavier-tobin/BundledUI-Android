package com.xaviertobin.bundledui.section.widgets

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.focusable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.TextObfuscationMode
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.rounded.Visibility
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SecureTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TextFieldDefaults.indicatorLine
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.autofill.ContentType
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.contentType
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.xaviertobin.bundledui.animations.AnimatedVerticalVisibility
import com.xaviertobin.bundledui.base.Tone
import com.xaviertobin.bundledui.buttons.IconButton
import com.xaviertobin.bundledui.section.base.Section
import com.xaviertobin.bundledui.section.base.SectionDefaults
import com.xaviertobin.bundledui.section.base.SectionRow
import com.xaviertobin.bundledui.theme.ThemedPreview
import com.xaviertobin.bundledui.theme.text

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SectionTextInput(
    value: String,
    label: String,
    modifier: Modifier = Modifier,
    fontSize: TextUnit = 16.sp,
    placeholder: String? = label,
    singleLine: Boolean = true,
    minLines: Int = 1,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    first: Boolean = false,
    last: Boolean = false,
    required: Boolean = false,
    enabled: Boolean = true,
    errorMessage: String? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions(imeAction = ImeAction.Next, capitalization = KeyboardCapitalization.Sentences),
    onValueChange: (value: String) -> Unit,
) {

    var focused by remember { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }
    val isError = errorMessage != null
    val indicatorColors = sectionTextInputIndicatorColors(isError)

    Section(
        first = first,
        last = last,
        padding = SectionDefaults.verticalPaddingValues(
            first = first,
            last = last,
            start = 22.dp,
            top = 4.dp,
            end = 22.dp,
            bottom = 10.dp
        ),
        focused = focused,
        enabled = enabled,
        tone = if (isError) {
            Tone.NEGATIVE
        } else {
            Tone.NEUTRAL
        }
    ) {

        SectionTextInputSlidingLabel(
            label = label,
            required = required
        )

        BasicTextField(
            modifier = Modifier
                .sectionTextInput(
                    interactionSource = interactionSource,
                    indicatorColors = indicatorColors,
                    onFocusChanged = {
                        focused = it
                    }
                )
                .then(modifier),
            textStyle = MaterialTheme.typography.headlineLarge.copy(fontSize = fontSize),
            value = value,
            onValueChange = onValueChange,
            singleLine = singleLine,
            minLines = minLines,
            maxLines = maxLines,
            interactionSource = interactionSource,
            keyboardOptions = keyboardOptions,
            enabled = enabled,
            visualTransformation = visualTransformation,
        ) { innerTextField ->
            TextFieldDefaults.DecorationBox(
                value = value,
                interactionSource = interactionSource,
                visualTransformation = VisualTransformation.None,
                innerTextField = innerTextField,
                singleLine = singleLine,
                isError = isError,
                enabled = enabled,
                colors = sectionTextInputColors(),

                placeholder = { SectionTextInputPlaceholder(placeholder, fontSize) },
                contentPadding = sectionTextInputContentPadding,
            )
        }

        SectionErrorMessage(
            errorMessage = errorMessage,
            isError = isError
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SectionPasswordInput(
    password: TextFieldState,
    fontSize: TextUnit = 16.sp,
    label: String,
    placeholder: String? = label,
    first: Boolean = false,
    last: Boolean = false,
    required: Boolean = false,
    enabled: Boolean = true,
    errorMessage: String? = null,
) {

    var showPassword by remember { mutableStateOf(false) }
    var focused by remember { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }
    val isError = errorMessage != null
    val indicatorColors = sectionTextInputIndicatorColors(isError)

    SectionRow(
        first = first,
        last = last,
        padding = SectionDefaults.verticalPaddingValues(
            first = first,
            last = last,
            start = 22.dp,
            top = 4.dp,
            end = 14.dp,
            bottom = 10.dp
        ),
        focused = focused,
        modifier = Modifier.alpha(if (enabled) 1f else 0.5f),
        tone = if (isError) {
            Tone.NEGATIVE
        } else {
            Tone.NEUTRAL
        }
    ) {
        Column(modifier = Modifier.weight(1f)) {

            SectionTextInputSlidingLabel(
                label = label,
                required = required
            )

            SecureTextField(
                state = password,
                textObfuscationMode = if (showPassword) {
                    TextObfuscationMode.Visible
                } else {
                    TextObfuscationMode.RevealLastTyped
                },
                modifier = Modifier
                    .sectionTextInput(
                        interactionSource = interactionSource,
                        indicatorColors = indicatorColors,
                        onFocusChanged = {
                            focused = it
                        }
                    )
                    .semantics { contentType = ContentType.Password },
                textStyle = MaterialTheme.typography.headlineLarge.copy(fontSize = fontSize),
                interactionSource = interactionSource,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Password
                ),
                enabled = enabled,
                colors = sectionTextInputColors(),
                placeholder = { SectionTextInputPlaceholder(placeholder, fontSize) },
                isError = isError,
                contentPadding = sectionTextInputContentPadding
            )

            SectionErrorMessage(
                errorMessage = errorMessage,
                isError = isError
            )
        }

        IconButton(
            contentDescription = "Toggle password visibility",
            icon = if (showPassword) {
                Icons.Rounded.Visibility
            } else {
                Icons.Filled.VisibilityOff
            },
            modifier = Modifier.align(Alignment.CenterVertically),
            color = MaterialTheme.colorScheme.tertiary
        ) {
            showPassword = !showPassword
        }

    }
}

@Composable
fun sectionTextInputColors(
) = TextFieldDefaults.colors(
    focusedContainerColor = Color.Transparent,
    unfocusedContainerColor = Color.Transparent,
    focusedIndicatorColor = Color.Transparent,
    unfocusedIndicatorColor = Color.Transparent,
    errorContainerColor = Color.Transparent,
    disabledContainerColor = Color.Transparent,
    errorIndicatorColor = Color.Transparent,
    disabledIndicatorColor = Color.Transparent,
)

@Composable
fun sectionTextInputIndicatorColors(isError: Boolean): IndicatorColors {
    val focusedIndicatorColor =
        if (isError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary.copy(
            alpha = 0.6f
        )
    val unfocusedIndicatorColor =
        if (isError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.text.copy(
            alpha = 0.2f
        )

    return IndicatorColors(
        focused = focusedIndicatorColor,
        unfocused = unfocusedIndicatorColor
    )
}

data class IndicatorColors(
    val focused: Color,
    val unfocused: Color
)

val sectionTextInputContentPadding = PaddingValues(
    top = 8.dp,
    bottom = 14.dp,
    start = 4.dp,
    end = 4.dp
)

@Composable
fun Modifier.sectionTextInput(
    interactionSource: MutableInteractionSource,
    indicatorColors: IndicatorColors,
    onFocusChanged: (focused: Boolean) -> Unit
) = this
    .focusable(true)
    .fillMaxWidth()
    .indicatorLine(
        enabled = true,
        isError = false,
        interactionSource = interactionSource,
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = indicatorColors.focused,
            unfocusedIndicatorColor = indicatorColors.unfocused,
            focusedContainerColor = Color.Transparent,
            errorContainerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent
        ),
        unfocusedIndicatorLineThickness = 2.dp,
        focusedIndicatorLineThickness = 3.dp,
    )
    .onFocusChanged {
        onFocusChanged(it.isFocused)
    }
    .animateContentSize()

@Composable
fun SectionTextInputPlaceholder(
    placeholder: String? = null,
    fontSize: TextUnit,
    extraTopPadding: Dp = 0.dp
) {
    if (placeholder != null) {
        Text(
            text = placeholder,
            modifier = Modifier.alpha(0.35f).offset(y = -extraTopPadding ),
            color = MaterialTheme.colorScheme.text,
            fontSize = fontSize,
            style = MaterialTheme.typography.bodyLarge,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
fun SectionErrorMessage(
    errorMessage: String?,
    isError: Boolean = errorMessage != null,
) {
    AnimatedVerticalVisibility(visible = isError, clipRadius = 0.dp) {
        Text(
            text = errorMessage ?: "",
            modifier = Modifier.padding(
                top = 8.dp,
                start = 0.dp,
                bottom = 0.dp
            ),
            color = MaterialTheme.colorScheme.error,
            fontSize = 14.sp,
        )
    }
}

@Composable
fun SectionTextInputSlidingLabel(
    label: String,
    required: Boolean,
) {
    Row {
        Text(
            text = label + if (required) "*" else "",
            Modifier
                .padding(
                    top = 4.dp,
                    start = 4.dp,
                    bottom = 0.dp
                )
                .weight(1f),
            color = MaterialTheme.colorScheme.tertiary,
            style = MaterialTheme.typography.bodyLarge,
            fontSize = 14.sp
        )
    }
}

@Preview(
    showBackground = true,
)
@Composable
fun SectionTextInputPreview() {
    var bundleName by remember { mutableStateOf("") }
    var bundleDescription by remember { mutableStateOf("") }

    ThemedPreview {
        SectionTextInput(
            first = true,
            value = bundleName,
            label = "Title",
            placeholder = "Movies, project, notes...",
            errorMessage = "Required field",
            fontSize = 23.sp
        ) { bundleName = it }
        SectionTextInput(
            value = bundleDescription,
            label = "Description",
            placeholder = "Describe what goes in this bundle",
            required = true
        ) { bundleDescription = it }
        SectionPasswordInput(
            password = TextFieldState(),
            label = "Password",
            placeholder = "Enter password",
            last = true
        )
    }
}