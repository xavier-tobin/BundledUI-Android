package com.xaviertobin.bundledui.sheets

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.statusBarsIgnoringVisibility
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalBottomSheetProperties
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetState
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.xaviertobin.bundledui.animations.AnimateInFade
import com.xaviertobin.bundledui.base.conditional
import com.xaviertobin.bundledui.color.alpha
import com.xaviertobin.bundledui.section.widgets.SectionSwitch
import com.xaviertobin.bundledui.theme.BaseTheme
import com.xaviertobin.bundledui.theme.LocalBaseTheme
import com.xaviertobin.bundledui.theme.safeSurface
import com.xaviertobin.bundledui.theme.secondaryText
import com.xaviertobin.bundledui.theme.text

typealias UnitFunction = () -> Unit
typealias ComposableFunction = @Composable () -> Unit

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun Sheet(
    onDismiss: UnitFunction,
    title: String? = null,
    subtitle: @Composable (() -> Unit)? = null,
    forceFullscreen: Boolean = false,
    autoFullscreenAfterInitialLoad: Boolean = false,
    userDismissible: Boolean = true,
    horizontalPadding: Dp = 20.dp,
    disableScroll: Boolean = false,
    defaultContentPadding: PaddingValues = PaddingValues(
        start = horizontalPadding,
        end = horizontalPadding,
        top = 6.dp,
        bottom = 10.dp
    ),
    systemContentPadding: PaddingValues = WindowInsets.navigationBars.asPaddingValues(),
    content: @Composable (ColumnScope.() -> Unit),
) {

    CompositionLocalProvider(LocalIndication provides ripple(color = MaterialTheme.colorScheme.secondaryText)) {

        SheetBase(
            onDismiss = onDismiss,
            forceFullscreen = forceFullscreen,
            autoFullscreenAfterInitialLoad = autoFullscreenAfterInitialLoad,
            userDismissible = userDismissible,
        ) { sheetState, isFullscreen ->

            if (userDismissible) {
                SheetDragHandleShield(sheetValue = sheetState, isFullscreen = isFullscreen)
            } else {
                Spacer(
                    modifier = Modifier
                        .height(
                            WindowInsets.statusBarsIgnoringVisibility
                                .asPaddingValues()
                                .calculateTopPadding()
                        )
                        .fillMaxWidth()
                )
            }


            if (title != null) {
                SheetTitle(
                    title = title,
                    subtitle = subtitle,
                    isFullscreen = isFullscreen
                )
            }

            Box(
                modifier = Modifier.conditional(isFullscreen) {
                    Modifier.fillMaxSize()
                }
            ) {

                Column(
                    modifier = Modifier
                        .conditional(isFullscreen) { Modifier.fillMaxSize() }
                        .then(
                            if (!disableScroll) Modifier.verticalScroll(
                                rememberScrollState(),
                            ) else Modifier
                        )
                        .padding(top = if (isFullscreen) 6.dp else 0.dp)
                        .padding(systemContentPadding)
                        .padding(defaultContentPadding)

                ) {
                    content()
                }


                if (title != null) {
                    FadeScrollEdge()
                }
            }
        }

    }

}


@Composable
fun SheetTitle(
    title: String,
    subtitle: @Composable (() -> Unit)? = null,
    isFullscreen: Boolean = false,
    padding: PaddingValues = PaddingValues(
        top = 4.dp,
        start = 20.dp,
        bottom = 16.dp,
        end = 20.dp
    )
) {

    Column(
        modifier = Modifier
            .padding(padding)
            .padding(top = if (isFullscreen) 12.dp else 0.dp)
    ) {
        Text(
            text = title,
            color = MaterialTheme.colorScheme.text,
            style = MaterialTheme.typography.titleLarge,
            autoSize = TextAutoSize.StepBased(maxFontSize = 24.sp, stepSize = 2.sp),
            maxLines = 4,
            modifier = Modifier.padding(start = 10.dp)
        )

        subtitle?.invoke()

    }

}

@Composable
fun SheetSubtitle(text: String) {
    Text(
        text = text,
        color = MaterialTheme.colorScheme.tertiary.alpha(0.85f),
        style = MaterialTheme.typography.bodyLarge,
        modifier = Modifier.padding(top = 4.dp, start = 1.dp)
    )
}


@Composable
fun BoxScope.FadeScrollEdge(
    height: Dp = 6.dp,
    color: Color = MaterialTheme.colorScheme.safeSurface(),
    bottom: Boolean = false,
) {
    Spacer(
        modifier = Modifier
            .background(
                brush = Brush.verticalGradient(
                    colors = if (bottom) listOf(
                        Color.Transparent,
                        color
                    ) else listOf(
                        color,
                        Color.Transparent
                    )
                )
            )
            .fillMaxWidth()
            .height(height)
            .align(
                if (bottom) Alignment.BottomCenter else Alignment.TopCenter
            )

    )
}

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun SheetDragHandleShield(
    sheetValue: SheetValue,
    isFullscreen: Boolean
) {

    Column(
        modifier = Modifier
            .height(WindowInsets.statusBarsIgnoringVisibility
                    .asPaddingValues()
                    .calculateTopPadding()
            )
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        AnimateInFade(sheetValue != SheetValue.Expanded || !isFullscreen) {
            Spacer(
                modifier = Modifier
                    .animateContentSize()
                    .background(
                        MaterialTheme.colorScheme.text.copy(
                            alpha = 0.3f
                        ),
                        RoundedCornerShape(6.dp)
                    )
                    .size(36.dp, 5.dp)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun SheetBase(
    onDismiss: UnitFunction,
    properties: ModalBottomSheetProperties? = null,
    forceFullscreen: Boolean = false,
    autoFullscreenAfterInitialLoad: Boolean = true,
    contentWindowInsets: @Composable () -> WindowInsets = { WindowInsets.ime },
    userDismissible: Boolean = true,
    content: @Composable (ColumnScope.(targetState: SheetValue, isFullscreen: Boolean) -> Unit)
) {

    val modalSheetState = rememberBottomSheetState(
        initialValue = SheetValue.Hidden,
        enabledValues = setOf(SheetValue.Hidden, SheetValue.Expanded),
        confirmValueChange = { userDismissible || it == SheetValue.Expanded }
    )

    var isFullscreen by remember { mutableStateOf(forceFullscreen) }
    var hasHandledInitialSize by remember { mutableStateOf(false) }
    val isFullscreenExpanded = modalSheetState.targetValue == SheetValue.Expanded && isFullscreen

    val isLightTheme = LocalBaseTheme.current == BaseTheme.LIGHT
    val configuration = LocalWindowInfo.current
    val density = LocalDensity.current
    val top = WindowInsets.statusBars.getTop(density)
    val bottom = WindowInsets.navigationBars.getBottom(density)
    val fortyDp = with(density) { 40.dp.toPx() }

    val roundedCornerRadius by animateDpAsState(
        targetValue = if (isFullscreenExpanded) 4.dp else 36.dp,
        label = "roundedCornerRadius"
    )

    ModalBottomSheet(
        modifier = Modifier.onSizeChanged {

            if (it.height <= 0) return@onSizeChanged

            if (forceFullscreen) {
                isFullscreen = true
            } else if (!hasHandledInitialSize || autoFullscreenAfterInitialLoad) {
                val fullscreenThreshold = configuration.containerSize.height - top - bottom - fortyDp
                isFullscreen = it.height >= fullscreenThreshold
                hasHandledInitialSize = true
            }
        },
        onDismissRequest = onDismiss,
        shape = RoundedCornerShape(roundedCornerRadius, roundedCornerRadius),
        sheetState = modalSheetState,
        sheetGesturesEnabled = userDismissible,
        dragHandle = null,
        contentWindowInsets = contentWindowInsets,
        containerColor = MaterialTheme.colorScheme.safeSurface(),
        contentColor = MaterialTheme.colorScheme.safeSurface(),
        tonalElevation = 0.dp,
        properties = properties ?: ModalBottomSheetProperties(
            isAppearanceLightNavigationBars = isLightTheme,
            isAppearanceLightStatusBars = isLightTheme,
        ),
        content = {
            content(modalSheetState.targetValue, isFullscreen)
        }
    )
}

@Preview
@Composable
fun SheetPreview() {
    Sheet(title = "Theme settings", onDismiss = { /*TODO*/ }) {
        SectionSwitch(title = "Test toggle", checked = true, onChecked = {})
    }
}