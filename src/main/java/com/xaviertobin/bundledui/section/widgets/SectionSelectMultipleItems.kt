package com.xaviertobin.bundledui.section.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import com.xaviertobin.bundledui.base.multiSelected
import com.xaviertobin.bundledui.base.rememberDpAsPx
import com.xaviertobin.bundledui.color.alpha
import com.xaviertobin.bundledui.section.base.SectionDefaults
import com.xaviertobin.bundledui.section.base.SectionOrientation

@Composable
fun <T> SectionSelectMultipleItems(
    items: List<T>,
    selectedIds: Set<String>,
    itemId: (T) -> String,
    onSelected: (String) -> Unit,
    modifier: Modifier = Modifier,
    itemView: @Composable (T, Boolean, Boolean, () -> Unit) -> Unit
) {
    val stroke = Stroke(
        width = rememberDpAsPx(1.8.dp),
        pathEffect = PathEffect.dashPathEffect(floatArrayOf(24f, 12f)),
    )
    val strokeColor = MaterialTheme.colorScheme.tertiary.alpha(0.5f)
    val shape = RoundedCornerShape(40.dp)

    LazyColumn(
        modifier = modifier
            .heightIn(min = 360.dp, max = 420.dp)
            .background(MaterialTheme.colorScheme.tertiary.alpha(0.06f), shape)
            .drawBehind {
                drawRoundRect(
                    color = strokeColor,
                    style = stroke,
                    cornerRadius = CornerRadius(40.dp.toPx()),
                )
            }
            .clip(shape)
            .fillMaxWidth(),
        contentPadding = PaddingValues(14.dp),
    ) {
        itemsIndexed(
            items = items,
            key = { _, item -> itemId(item) },
        ) { index, item ->
            val id = itemId(item)
            val first = index == 0
            val last = index == items.lastIndex
            val shape = SectionDefaults.shape(orientation = SectionOrientation.VERTICAL, first = first, last = last)

            Box(Modifier.multiSelected(selected = id in selectedIds, shape = shape)) {
                itemView(
                    item,
                    index == 0,
                    index == items.lastIndex
                ) {
                    onSelected(id)
                }
            }
        }
    }
}