package com.adrian.mercadolibreconsumer.utils

import android.content.Context
import android.util.DisplayMetrics
import android.util.TypedValue

fun Float.dpToPx(context: Context): Float {
    val metrics: DisplayMetrics = context.resources.displayMetrics
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this, metrics)
}

fun Float.toPriceTag(): String {
    val number = this.toInt().toString()
        .reversed()
        .chunked(3)
        .joinToString(".")
        .reversed()
    return "$ $number"
}
