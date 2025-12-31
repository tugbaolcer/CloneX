package com.tugbaolcer.clonex.model

import android.os.Parcelable
import com.tugbaolcer.clonex.R
import com.tugbaolcer.clonex.base.component.LayoutModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class GetGenresResponse(
    val genres: List<Genre>
): Parcelable {
    @Parcelize
    data class Genre(
        val id: Int,
        val name: String
    ) : LayoutModel, Parcelable {
        override fun layoutId() = R.layout.item_genre_movie_list
        override fun uniqueId() = id
    }
}