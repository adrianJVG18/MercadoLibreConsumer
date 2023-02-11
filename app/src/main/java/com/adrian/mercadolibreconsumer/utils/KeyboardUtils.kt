package com.adrian.mercadolibreconsumer.utils

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.google.android.material.internal.ViewUtils.dpToPx


fun View.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}

// This method sets the listener when soft keyboard opens
fun View.onSoftKeyboardDisplayed(onDisplay: () -> Unit)  {
    this.viewTreeObserver.addOnGlobalLayoutListener{
        val heightDiff: Int = this.rootView.height - this.height
        if (heightDiff > 200f.dpToPx(this.context)) { // if more than 200 dp, it's probably a keyboard...
            onDisplay.invoke()
        }
    }
}