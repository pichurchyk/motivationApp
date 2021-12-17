package com.pichurchyk.motivationapp.ui.common.utils

import android.content.Context
import android.view.View
import com.google.android.material.snackbar.Snackbar

fun Context.snackBar(view: View, text: String) {
    val snackbar = Snackbar.make(view, text, 1000)
    snackbar.show()
}