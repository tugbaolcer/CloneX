package com.tugbaolcer.clonex.utils

import android.view.View

fun View.setOnSingleClickListener(block: () -> Unit) {
    setOnClickListener(OnSingleClickListener(block))
}