package com.tugbaolcer.clonex.utils

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputLayout

fun View.setOnSingleClickListener(block: () -> Unit) {
    setOnClickListener(OnSingleClickListener(block))
}

inline fun <T : Fragment> T.withArgs(argsBuilder: Bundle.() -> Unit): T =
    this.apply {
        arguments = Bundle().apply(argsBuilder)
    }

fun TextInputLayout.removePadding() {
    for (i in 0 until this.childCount) {
        this.getChildAt(i).setPadding(0, 0, 0, 0)
    }
}