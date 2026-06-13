package com.xaviertobin.bundledui.section.widgets

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.InputTransformation
import androidx.compose.foundation.text.input.OutputTransformation
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.xaviertobin.bundledui.section.base.Section
import com.xaviertobin.bundledui.section.base.SectionDefaults

@Composable
fun SectionTextInput2(
    value: TextFieldState,
    modifier: Modifier = Modifier,
    label: String? = null,
    fontSize: TextUnit = 16.sp,
    placeholder: String? = label,
    singleLine: Boolean = true,
    allowEnterForNewLine: Boolean = false,
    minLines: Int = 1,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    first: Boolean = false,
    last: Boolean = false,
    required: Boolean = false,
    enabled: Boolean = true,
    errorMessage: String? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
    inputTransformation: InputTransformation? = null,
    outputTransformation: OutputTransformation? = null,
) {

    var focused by remember { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }
    val isError = errorMessage != null
    val indicatorColors = sectionTextInputIndicatorColors(isError)
    val resolvedKeyboardOptions = if (allowEnterForNewLine) {
        keyboardOptions.copy(imeAction = ImeAction.Default)
    } else {
        keyboardOptions
    }

    Section(
        first = first,
        last = last,
        padding = SectionDefaults.verticalPaddingValues(
            first = first,
            last = last,
            start = 22.dp,
            top = 0.dp,
            end = 22.dp,
            bottom = 10.dp
        ),
        focused = focused,
        enabled = enabled,
        modifier = modifier,
//        tone = if (isError) Tone.NEGATIVE else Tone.NEUTRAL
    ) {


        TextField(
            state = value,
            modifier = Modifier
                .sectionTextInput(
                    interactionSource = interactionSource,
                    indicatorColors = indicatorColors,
                    onFocusChanged = {
                        focused = it
                    }
                ),
            textStyle = MaterialTheme.typography.bodyLarge.copy(
                fontSize = fontSize,
            ),
            interactionSource = interactionSource,
            label = if (label != null) {
                {
                    SectionTextInput2Label(
                        label = label,
                        required = required
                    )
                }
            } else null,
            keyboardOptions = resolvedKeyboardOptions,
            enabled = enabled,
            colors = sectionTextInputColors(),
            placeholder = {
                SectionTextInputPlaceholder(
                    placeholder = placeholder,
                    fontSize = fontSize,
                    extraTopPadding = 2.5.dp
                )
            },
            isError = isError,
            contentPadding = if (label == null) {
                TextFieldDefaults.contentPaddingWithoutLabel(
                    top = 16.dp,
                    bottom = 10.dp,
                    start = 4.dp,
                    end = 0.dp
                )
            } else {
                TextFieldDefaults.contentPaddingWithLabel(
                    top = 8.dp,
                    bottom = 10.dp,
                    start = 4.dp,
                    end = 0.dp
                )
            },
            inputTransformation = inputTransformation,
            outputTransformation = outputTransformation,
            lineLimits = TextFieldLineLimits.MultiLine(
                minHeightInLines = minLines,
                maxHeightInLines = maxLines
            )
        )

        SectionErrorMessage(
            errorMessage = errorMessage,
            isError = isError
        )
    }
}

@Composable
fun SectionTextInput2Label(
    label: String,
    required: Boolean,
) {
    Text(
        text = label + if (required) "*" else "",
        color = MaterialTheme.colorScheme.tertiary,
        style = MaterialTheme.typography.bodyLarge,
        fontSize = 14.sp,
        modifier = Modifier
            .padding(
                bottom = 6.dp,
                start = 4.dp
            )
            .offset(x = (-4).dp)
    )

}

@Preview
@Composable
private fun SectionTextInput2Preview() {
    val textFieldState = remember { TextFieldState("") }
    val textFieldState2 = remember { TextFieldState("P") }

    Column {
        SectionTextInput2(
            value = textFieldState,
            label = "Label",
            placeholder = "Placeholder",
            first = true,
            required = true,
        )

        SectionTextInput2(
            value = textFieldState2,
            placeholder = "Placeholder",
            last = true,
            required = true,
            errorMessage = "This is an error message"
        )
    }
}