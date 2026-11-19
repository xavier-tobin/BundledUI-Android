> IN PROGRESS - This library is not yet distributed and the docs are a work in progress.

# Bundled UI for Jetpack Compose

A simple, idiomatic Material 3 UI design system for Jetpack Compose,
built for
[Bundled Notes](https://play.google.com/store/apps/details?id=com.xaviertobin.noted).

## Quick look

<!-- ![Basic italics usage example](images/home_page.png) -->

<img src="images/all.png"  alt="Screenshots of the library being used in Bundled Notes"/>

## What's included?

BundledUI is the design system used in Bundled Notes. It is comprised of three key parts for creating gorgeous Material You apps:
1. The `BundledUITheme` wrapper
2. The `Section` component
3. A small suite of extra components, animations and utilities

## Step 1: Theming

The theming in this library is an abstraction around the Compose Material3 library. It will work with a
regular `MaterialTheme` wrapper, but to take advantage of the rich theming options available (including
OLED & Dark themes, and auto-system themes), wrap your app in `BundledUITheme`:

```kotlin
setContent {
    BundledUITheme(theme = BaseTheme.Dark) {
        // you can now use BundledUI
    }
}
```

> [!TIP]
> It's helpful to understand the basics of
> the [Compose Material3 library](https://developer.android.com/develop/ui/compose/designsystems/material3)

## Step 2: The `Section`

`Section` is a base, Card-like component that is found on almost every page of
Bundled Notes. It abstracts away almost all fiddling with padding, margin, and
modifiers, as well as supporting tone, orientation, clicks, enabled/selected states, and focus.

Though you can use the base `Section` directly, BundledUI includes a
number of `Section` components that are commonly used in Bundled Notes:

- `SectionTextInput`
- `SectionTitleDescription` (list item)
- `SectionButton` (clickable, with icon + loading)
- `SectionButtonSheet` (opens sheet when clicked, with icon)
- `SectionSwitch` (on/off switch)
- `SectionSlider` (slider with values/steps)
- `SectionAlert` (alert with icon, tones, dismiss action)

All you have to do to get started is group related `Sections`, and set their
`first` and `last` params:

```kotlin
Column {

    SectionHeader("Theme")

    SectionButton(
        first = true,
        title = "Theme",
        description = "Choose between light, dark and OLED themes",
        icon = Icons.Rounded.WbSunny,
        onClick = { /* toggle between themes*/ }
    )

    SectionSwitch(
        title = "Enable Material You",
        description = "Tint theme based on wallpaper",
        checked = isChecked,
        onChecked = { isChecked = it }
    )

    SectionButtonSheet(
        last = true,
        title = "Font scale",
        description = "Choose fonts and text size",
        icon = Icons.Rounded.FontDownload,
    ) { onDismiss ->
        // FontSettingsSheet(onDismiss)
    }

}
```

The above code leads to this layout:

<img src="images/sections_basic.png" alt="Basic italics usage example" width="450px" >

__In BundledUI, almost everything is a `Section` - you can make many layouts just by using
the included Section components and even creating your own.__

> [!NOTE]
> You don't have to think about padding and margins at all when
> using `Section`. The `first` and `last` parameters take care of all that for
> you.

# Step 3: Extras, animations and utilities

> More to come... docs are a work in progress

