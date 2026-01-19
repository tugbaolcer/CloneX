package com.tugbaolcer.clonex.ui.home

import androidx.lifecycle.viewModelScope
import com.tugbaolcer.clonex.base.CloneXBaseViewModel
import com.tugbaolcer.clonex.base.LoadingType
import com.tugbaolcer.clonex.model.GetTrendingAllResponse
import com.tugbaolcer.clonex.network.AppApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(val api: AppApi) : CloneXBaseViewModel(api) {

    private val _heroItem = MutableStateFlow<GetTrendingAllResponse.TrendingItem?>(null)
    val heroItem = _heroItem.asStateFlow()

    private val _trendingList =
        MutableStateFlow<List<GetTrendingAllResponse.TrendingItem>>(emptyList())
    val trendingList = _trendingList.asStateFlow()

    private val _movieList =
        MutableStateFlow<List<GetTrendingAllResponse.TrendingItem>>(emptyList())
    val movieList = _movieList.asStateFlow()

    private val _tvList = MutableStateFlow<List<GetTrendingAllResponse.TrendingItem>>(emptyList())
    val tvList = _tvList.asStateFlow()

    fun getTrendingAll() {
        viewModelScope.launch {
            networkCallAsFlow {
                api.fetchTrendingAll()
            }.collect { result ->
                handleApiResult(
                    loadingType = LoadingType.SHIMMER,
                    apiResult = result,
                    onSuccess = { response ->
                        val items = response.results

                        if (items.isNotEmpty()) {
                            _heroItem.value = items.maxByOrNull { it.popularity ?: 0.0 }
                            _trendingList.value = items.take(10)
                            _movieList.value = items.filter { it.media_type == "movie" }
                            _tvList.value = items.filter { it.media_type == "tv" }
                        }
                    }
                )
            }
        }
    }
}