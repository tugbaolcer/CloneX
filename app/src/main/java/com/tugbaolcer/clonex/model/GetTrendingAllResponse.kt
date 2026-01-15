package com.tugbaolcer.clonex.model

import com.tugbaolcer.clonex.R
import com.tugbaolcer.clonex.base.component.LayoutModel
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GetTrendingAllResponse(
    val page: Int,
    val results: List<TrendingItem>,
    val total_pages: Int,
    val total_results: Int
) : Parcelable {
    @Parcelize
    data class TrendingItem(
        val id: Int,
        val adult: Boolean?,
        val backdrop_path: String?,
        val title: String?, // Filmler için
        val name: String?,  // Diziler için
        val original_language: String?,
        val original_title: String?,
        val original_name: String?,
        val overview: String?,
        val poster_path: String?,
        val media_type: String?,
        val genre_ids: List<Int>?,
        val popularity: Double?,
        val release_date: String?,
        val first_air_date: String?,
        val video: Boolean?,
        val vote_average: Double?,
        val vote_count: Int?,
        val origin_country: List<String>?
    ) : LayoutModel, Parcelable {

        override fun layoutId() = R.layout.item_trending_movie_list
        override fun uniqueId() = id
        fun getDisplayName(): String? = title ?: name
        val fullImageUrl: String
            get() = "https://image.tmdb.org/t/p/w500$poster_path"
    }
}