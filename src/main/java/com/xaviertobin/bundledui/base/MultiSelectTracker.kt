package com.xaviertobin.bundledui.base

import androidx.compose.runtime.mutableStateMapOf

abstract class MultiSelectTracker<I>(val getSelectedItems: (Map<String, String>) -> List<I>) {

    private var isDragActive = false
    private val selectedIds = mutableStateMapOf<String, String>()

    val active
        get() = selectedIds.isNotEmpty()

    fun count(): Int {
        return selectedIds.size
    }

    fun selectedIds(): List<String> {
        return selectedIds.keys.toList()
    }

    fun selectedItems(): List<I> {
        return getSelectedItems(selectedIds)
    }

    fun only(): String? {
        return if (selectedIds.size == 1) {
            selectedIds.keys.firstOrNull()
        } else {
            null
        }
    }

    fun isActive(): Boolean {
        return selectedIds.isNotEmpty()
    }

    fun isDragActive(): Boolean {
        return isDragActive
    }

    fun toggle(item: String) {
        if (selectedIds.contains(item)) {
            selectedIds.remove(item)
        } else {
            selectedIds[item] = item
        }
    }

    fun selectAll(items: List<String>) : Boolean {
        var hasChanged = false
        items.forEach { item ->
            if (!selectedIds.contains(item)) {
                hasChanged = true
                selectedIds[item] = item
            }
        }
        return hasChanged
    }

    fun deselectAll(items: List<String>): Boolean {
        var hasChanged = false
        items.forEach { item ->
            if (selectedIds.contains(item)) {
                hasChanged = true
                selectedIds.remove(item)
            }
        }
        return hasChanged
    }

    fun isSelected(item: String): Boolean {
        return selectedIds.contains(item)
    }

    fun clear() {
        selectedIds.clear()
    }

    fun startDrag() {
        isDragActive = true
    }

    fun endDrag() {
        isDragActive = false
    }



}