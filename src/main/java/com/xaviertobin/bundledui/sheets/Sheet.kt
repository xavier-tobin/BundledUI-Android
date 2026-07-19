package com.xaviertobin.bundledui.sheets

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.snap
import androidx.compose.animation.core.tween
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
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsIgnoringVisibility
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalBottomSheetProperties
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
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
import com.xaviertobin.bundledui.theme.text

typealias UnitFunction = () -> Unit
typealias ComposableFunction = @Composable () -> Unit

private val LocalSheetAtExpandedPosition = compositionLocalOf { true }
private val LocalAnimateSheetSize = compositionLocalOf { false }
private val LocalOnSheetSizeAnimationFinished = compositionLocalOf<() -> Unit> { {} }

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun Sheet(
    onDismiss: UnitFunction,
    title: String? = null,
    subtitle: @Composable (() -> Unit)? = null,
    forceFullscreen: Boolean = false,
    autoFullscreenAfterInitialLoad: Boolean = true,
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

    var naturalContentHeight by remember { mutableIntStateOf(0) }
    var headerHeight by remember(title) { mutableIntStateOf(0) }
    val density = LocalDensity.current
    val headerHeightDp = with(density) { headerHeight.toDp() }
    val scrollState = rememberScrollState()
    val isScrolled by remember { derivedStateOf { scrollState.value > 0 } }
    val animateSheetSize = LocalAnimateSheetSize.current
    val onSheetSizeAnimationFinished = LocalOnSheetSizeAnimationFinished.current

    SheetBase(
        onDismiss = onDismiss,
        forceFullscreen = forceFullscreen,
        autoFullscreenAfterInitialLoad = autoFullscreenAfterInitialLoad,
        userDismissible = userDismissible,
        naturalContentHeight = naturalContentHeight,
    ) { sheetState, isFullscreen ->
        Box(
            modifier = Modifier
                .animateContentSize(
                    animationSpec = if (animateSheetSize) {
                        tween(durationMillis = 320, easing = FastOutSlowInEasing)
                    } else {
                        snap()
                    },
                    finishedListener = { _, _ ->
                        if (animateSheetSize) onSheetSizeAnimationFinished()
                    },
                )
                .conditional(isFullscreen) {
                    Modifier.fillMaxHeight()
                }
        ) {
            Column(
                modifier = Modifier
                    .conditional(isFullscreen) { Modifier.fillMaxHeight() }
                    .then(
                        if (!disableScroll || isFullscreen) {
                            Modifier.verticalScroll(scrollState)
                        } else {
                            Modifier
                        }
                    )
            ) {
                Column(
                    modifier = Modifier
                        .onSizeChanged { naturalContentHeight = it.height }
                        .padding(systemContentPadding)
                        .padding(defaultContentPadding)
                ) {
                    Spacer(modifier = Modifier.height(headerHeightDp))
                    content()
                }
            }

            SheetHeader(
                sheetValue = sheetState,
                isFullscreen = isFullscreen,
                userDismissible = userDismissible,
                title = title,
                subtitle = subtitle,
                isScrolled = isScrolled,
                onSizeChanged = { headerHeight = maxOf(headerHeight, it) },
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BoxScope.SheetHeader(
    sheetValue: SheetValue,
    isFullscreen: Boolean,
    userDismissible: Boolean,
    title: String?,
    subtitle: @Composable (() -> Unit)?,
    isScrolled: Boolean,
    onSizeChanged: (Int) -> Unit,
) {
    val backgroundColor = MaterialTheme.colorScheme.safeSurface()
    val isAtExpandedPosition = LocalSheetAtExpandedPosition.current

    val titleElevation by animateDpAsState(
        targetValue = if (isScrolled) 8.dp else 0.dp,
        label = "titleElevation",
    )

    Column(
        modifier = Modifier
            .align(Alignment.TopCenter)
            .fillMaxWidth()
            .onSizeChanged { onSizeChanged(it.height) }
            .background(
                Brush.verticalGradient(
                    colors = listOf(backgroundColor, Color.Transparent)
                )
            )
    ) {
        SheetDragHandleShield(
            sheetValue = sheetValue,
            isFullscreen = isFullscreen,
            showHandle = userDismissible,
            isAtExpandedPosition = isAtExpandedPosition,
        )

        if (title != null) {
            Surface(
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 8.dp),
                color = backgroundColor,
                shape = RoundedCornerShape(24.dp),
                shadowElevation = titleElevation,
            ) {
                SheetTitle(
                    title = title,
                    subtitle = subtitle,
                    isCompact = isScrolled,
                )
            }
        }
    }
}


@Composable
fun SheetTitle(
    title: String,
    subtitle: @Composable (() -> Unit)? = null,
    isCompact: Boolean = false,
    padding: PaddingValues = PaddingValues(
        horizontal = 10.dp,
        vertical = 10.dp,
    )
) {
    val titleScale by animateFloatAsState(
        targetValue = if (isCompact) 0.75f else 1f,
        label = "titleFontSize",
    )

    Column(
        modifier = Modifier.padding(padding)
    ) {
        Text(
            text = title,
            color = MaterialTheme.colorScheme.text,
            style = MaterialTheme.typography.titleLarge.copy(
                fontSize = 24.sp,
                lineHeight = 28.sp,
            ),
            maxLines = 4,
            modifier = Modifier
                .graphicsLayer {
                    scaleX = titleScale
                    scaleY = titleScale
                    transformOrigin = TransformOrigin(0f, 0.5f)
                }
                .padding(horizontal = 10.dp)
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
    isFullscreen: Boolean,
    showHandle: Boolean = true,
    isAtExpandedPosition: Boolean = sheetValue == SheetValue.Expanded,
) {

    Column(
        modifier = Modifier
            .height(
                WindowInsets.statusBarsIgnoringVisibility
                    .asPaddingValues()
                    .calculateTopPadding()
            )
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        AnimateInFade(
            showHandle && (!isFullscreen || !isAtExpandedPosition),
            100,
        ) {
            Spacer(
                modifier = Modifier
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
    naturalContentHeight: Int = 0,
    content: @Composable (ColumnScope.(targetState: SheetValue, isFullscreen: Boolean) -> Unit)
) {

    val modalSheetState = rememberBottomSheetState(
        initialValue = SheetValue.Hidden,
        enabledValues = setOf(SheetValue.Hidden, SheetValue.Expanded),
        confirmValueChange = { userDismissible || it == SheetValue.Expanded }
    )

    var isFullscreen by remember { mutableStateOf(forceFullscreen) }
    var hasHandledInitialSize by remember { mutableStateOf(false) }
    var animateSheetSize by remember { mutableStateOf(false) }

    val isLightTheme = LocalBaseTheme.current == BaseTheme.LIGHT
    val configuration = LocalWindowInfo.current
    val density = LocalDensity.current

    val top = WindowInsets.navigationBars.getBottom(density)
    val bottom = WindowInsets.navigationBars.getBottom(density)
    val extra = with(density) { 2.dp.toPx() }
    val fullscreenThreshold = configuration.containerSize.height - bottom - top - extra
    val expandedPositionTolerance = with(density) { 1.dp.toPx() }
    val isAtExpandedPosition by remember(isFullscreen, expandedPositionTolerance) {
        derivedStateOf {
            !isFullscreen || runCatching {
                modalSheetState.requireOffset() <= expandedPositionTolerance
            }.getOrDefault(true)
        }
    }

    LaunchedEffect(
        forceFullscreen,
        naturalContentHeight,
        fullscreenThreshold,
        autoFullscreenAfterInitialLoad,
    ) {
        if (forceFullscreen) {
            if (!isFullscreen) {
                animateSheetSize = hasHandledInitialSize
                isFullscreen = true
            }
            if (naturalContentHeight > 0) hasHandledInitialSize = true
        } else if (naturalContentHeight > 0 && (!hasHandledInitialSize || autoFullscreenAfterInitialLoad)) {
            val nextIsFullscreen = naturalContentHeight >= fullscreenThreshold
            if (nextIsFullscreen != isFullscreen) {
                animateSheetSize = hasHandledInitialSize
                isFullscreen = nextIsFullscreen
            }
            hasHandledInitialSize = true
        }
    }

    val roundedCornerRadius by animateDpAsState(
        targetValue = if (isFullscreen && isAtExpandedPosition) 0.dp else 36.dp,
        label = "roundedCornerRadius"
    )

    ModalBottomSheet(
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
            CompositionLocalProvider(
                LocalSheetAtExpandedPosition provides isAtExpandedPosition,
                LocalAnimateSheetSize provides animateSheetSize,
                LocalOnSheetSizeAnimationFinished provides { animateSheetSize = false },
            ) {
                content(modalSheetState.targetValue, isFullscreen)
            }
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