package com.xaviertobin.bundledui.color

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.core.graphics.ColorUtils
import com.xaviertobin.bundledui.theme.BaseTheme

val Color.Companion.DarkerGray: Color
    get() = Color(0xFF222222)


fun test() {
    Color.DarkGray
}

val Color.Companion.LighterGray
    get() = Color(0xFFF0F0F0)

fun HSBColor.rotateHue(by: Float = 180f): HSBColor {
    return this.copy(
        hue = (this.hue + by) % 360
    )
}

/**
 * Returns the complementary color on the Color wheel
 */
fun Color.rotateHue(by: Float = 180f): Color {
    return this.hsb().rotateHue(by = by).toColor()
}

fun Color.hue(): Float {
    val hsb = this.hsb()
    return hsb.hue
}

fun HSBColor.adjust(relativeSaturationBy: Float = 0.0f, relativeBrightnessBy: Float = 0.0f): HSBColor {
    return this.copy(
        saturation = (this.saturation + relativeSaturationBy).coerceIn(0f, 1f),
        brightness = (this.brightness + relativeBrightnessBy).coerceIn(0f, 1f),
    )
}


fun Color.adjust(saturationBy: Float = 0.0f, brightnessBy: Float = 0.0f): Color {
    return hsb().adjust(
        relativeSaturationBy = saturationBy,
        relativeBrightnessBy = brightnessBy
    ).toColor()
}

fun Color.deintensify(by: Float = 0.12f): Color {
    val intensity = -this.getIntensityReduction(by)
    return this.adjust(
        saturationBy = intensity,
        brightnessBy = intensity,
    )
}

fun Color.deintensifySaturation(by: Float = 0.12f): Color {
    return this.adjust(
        saturationBy = -this.getIntensityReduction(by),
    )
}

fun Color.deintensifyBrightness(by: Float = 0.12f): Color {
    return this.adjust(
        brightnessBy = -this.getIntensityReduction(by),
    )
}

fun Color.perceivedLightness(): Float {
    val luminance = (0.299f * this.red + 0.587f * this.green + 0.114f * this.blue)
    return luminance.coerceIn(0.0f, 1.0f)
}

fun Color.normalizedLuminance(): Float {
    return this.hsb().toColor(saturation = 1f, brightness = 1f).luminance()
}

fun Color.getIntensityReduction(degree: Float = 0.25f) =
    (degree * this.perceivedLightness()).toFloat()

fun Int.modifyColorForTheme(theme: BaseTheme): Int {
    return when (theme) {
        BaseTheme.LIGHT -> this
        BaseTheme.DARK -> ColorUtils.blendARGB(this, android.graphics.Color.DKGRAY, 0.65f)
        BaseTheme.OLED -> ColorUtils.blendARGB(this, android.graphics.Color.BLACK, 0.62f)
    }
}


fun Color.dulled(forTheme: BaseTheme, by: Float = 0.4f): Color {
    // Want to add more saturation and brightness depending on the brightness of the color (i.e. green)
    val intensityGivenColor = this.getIntensityReduction(by)

    val saturation = when (forTheme) {
        BaseTheme.LIGHT -> 0.86f - intensityGivenColor
        BaseTheme.DARK -> 0.8f - intensityGivenColor
        BaseTheme.OLED -> 0.87f - intensityGivenColor
    }

    val brightness = when (forTheme) {
        BaseTheme.LIGHT -> 0.84f - intensityGivenColor
        BaseTheme.DARK -> 1f - intensityGivenColor
        BaseTheme.OLED -> 0.9f - intensityGivenColor
    }

    return this.hsb().copy(
        saturation = saturation.toFloat(),
        brightness = brightness
    ).toColor()
}

fun Color.Companion.randomAestheticColor(): Color {
    val baseColor = listOf(
        HSBColor(18f, 0.68f, 0.9f),
        HSBColor(45f, 0.62f, 0.92f),
        HSBColor(105f, 0.54f, 0.82f),
        HSBColor(145f, 0.52f, 0.78f),
        HSBColor(205f, 0.63f, 0.88f),
        HSBColor(235f, 0.58f, 0.84f),
        HSBColor(280f, 0.55f, 0.87f),
        HSBColor(335f, 0.62f, 0.9f)
    ).random()

    return baseColor.copy(
        hue = (baseColor.hue + (-14f..14f).random() + 360f) % 360f,
        saturation = (baseColor.saturation + (-0.1f..0.1f).random()).coerceIn(0.42f, 0.78f),
        brightness = (baseColor.brightness + (-0.07f..0.07f).random()).coerceIn(0.72f, 0.96f)
    ).toColor()
}

fun ClosedFloatingPointRange<Float>.random(): Float {
    return (start + Math.random() * (endInclusive - start)).toFloat()
}
