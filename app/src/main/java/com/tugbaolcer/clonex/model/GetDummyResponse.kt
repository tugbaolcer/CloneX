package com.tugbaolcer.clonex.model

import com.tugbaolcer.clonex.R
import com.tugbaolcer.clonex.base.component.LayoutModel


data class GetDummyResponse(
    val id: Int,
    val name: String,
    val pictureMedium: String
): LayoutModel {
    override fun layoutId(): Int {
        return R.layout.item_dummy
    }

}