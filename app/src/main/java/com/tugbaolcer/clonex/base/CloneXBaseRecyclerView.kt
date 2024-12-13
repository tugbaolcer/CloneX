package com.tugbaolcer.clonex.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import androidx.databinding.library.baseAdapters.BR
import com.tugbaolcer.clonex.base.component.LayoutModel

open class CloneXBaseRecyclerView<T: LayoutModel> : RecyclerView.Adapter<CloneXBaseRecyclerView<T>.ViewHolder> {

    lateinit var listItems: MutableList<T>
    var clickListener: ((T, String) -> Unit)? = null

    constructor(list: MutableList<T>, listener: ((T, String) -> Unit)?) : super() {
        this.listItems = list
        this.clickListener = listener
    }

    open inner class ViewHolder(open val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ViewDataBinding>(
            layoutInflater,
            viewType,
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { item ->
            holder.binding.setVariable(BR.obj, item)
            holder.binding.setVariable(BR.position, position)
            if (clickListener != null)
                holder.binding.setVariable(BR.clickListener, clickListener)

            holder.binding.executePendingBindings()
        }
    }

    fun setItems(items: MutableList<T>) {
        if (::listItems.isInitialized.not())
            listItems = items
        else {
            listItems.clear()
            listItems.addAll(items)
        }
        notifyDataSetChanged()
    }

    fun addItems(items: MutableList<T>) {
        if (::listItems.isInitialized.not())
            listItems = items
        else {
            listItems.addAll(items)
        }
        notifyDataSetChanged()
    }

    fun clearItems(){
        this.listItems.clear()
        notifyDataSetChanged()
    }

    open fun getItem(index: Int): T? {
        return if (index < listItems.size) listItems[index] else null
    }

    override fun getItemCount(): Int {
        return listItems.size
    }

    override fun getItemViewType(position: Int): Int {
        return listItems[position].layoutId()
    }
}