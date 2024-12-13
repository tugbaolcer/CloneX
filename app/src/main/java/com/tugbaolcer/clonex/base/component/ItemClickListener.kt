package com.tugbaolcer.clonex.base.component

import android.view.View

interface ItemClickListener<T> {

    fun onItemClick(view: View, item: T)

}