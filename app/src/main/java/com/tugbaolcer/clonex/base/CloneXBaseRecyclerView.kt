package com.tugbaolcer.clonex.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.ListAdapter
import com.tugbaolcer.clonex.base.component.CloneXDiffCallback
import com.tugbaolcer.clonex.base.component.LayoutModel


abstract class CloneXBaseRecyclerView<T : LayoutModel> :
    ListAdapter<T, CloneXBaseRecyclerView<T>.BaseViewHolder>(CloneXDiffCallback<T>()) {

    var clickListener: ((T, String) -> Unit)? = null

    // Boilerplate'i azaltmak için ViewHolder generic hale getirildi
    inner class BaseViewHolder(val binding: ViewDataBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: T) {
            // "obj" ve "clickListener" XML tarafındaki değişken isimleridir
            binding.setVariable(BR.obj, item)
            binding.setVariable(BR.clickListener, clickListener)

            // Alt sınıflar özel bir logic işletmek isterse:
            onBindAdditionalLogic(binding, item, adapterPosition)

            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val binding = DataBindingUtil.inflate<ViewDataBinding>(
            LayoutInflater.from(parent.context),
            viewType,
            parent,
            false
        )
        return BaseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemViewType(position: Int): Int {
        return getItem(position).layoutId()
    }

    open fun onBindAdditionalLogic(binding: ViewDataBinding, item: T, position: Int) {}
}