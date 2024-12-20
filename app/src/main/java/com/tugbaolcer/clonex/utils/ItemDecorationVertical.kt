package com.tugbaolcer.clonex.utils

import android.content.res.Resources
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class ItemDecorationVertical(private val space: Int) : RecyclerView.ItemDecoration() {

    val Int.toDp: Int
        get() = (this * Resources.getSystem().displayMetrics.density).toInt()

    override fun getItemOffsets(outRect: Rect, view: View,
                                parent: RecyclerView, state: RecyclerView.State) {

        with(outRect) {
            if (parent.getChildAdapterPosition(view) > 0)
                top = space.toDp
        }
    }
}