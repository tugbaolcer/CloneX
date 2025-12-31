package com.tugbaolcer.clonex.base.component

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil


class CloneXDiffCallback<T : LayoutModel> : DiffUtil.ItemCallback<T>() {
    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem.uniqueId() == newItem.uniqueId()
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem == newItem
    }
}