package com.perniciosius.todonative.models

import androidx.compose.ui.graphics.Color
import com.perniciosius.todonative.R
import com.perniciosius.todonative.ui.theme.Blue200
import com.perniciosius.todonative.ui.theme.Pink700

enum class Category {
    PERSONAL, WORK,
}

fun Category.getCategoryName() : String {
    return when(this) {
        Category.PERSONAL -> "Personal"
        Category.WORK -> "Work"
    }
}

fun Category.getCategoryColor() : Color {
    return when(this) {
        Category.PERSONAL -> Blue200
        Category.WORK -> Pink700
    }
}

fun Category.getCategoryImage() : Int {
    return when(this) {
        Category.PERSONAL -> R.drawable.personal
        Category.WORK -> R.drawable.work
    }
}