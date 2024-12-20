package com.tugbaolcer.clonex.ui

import androidx.lifecycle.viewModelScope
import com.tugbaolcer.clonex.base.CloneXBaseRecyclerView
import com.tugbaolcer.clonex.base.CloneXBaseViewModel
import com.tugbaolcer.clonex.model.GetGenresResponse
import com.tugbaolcer.clonex.network.AppApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(private val api: AppApi) : CloneXBaseViewModel() {

    lateinit var genresMovieAdapter : CloneXBaseRecyclerView<GetGenresResponse.Genre>

    private val _genreMovieDataList = MutableStateFlow<List<GetGenresResponse.Genre>>(emptyList())
    val genreMovieDataList: StateFlow<List<GetGenresResponse.Genre>> get() = _genreMovieDataList

    fun getGenreMovieList() {
        viewModelScope.launch {
            setLoadingState(true)
            try {
                val response = api.fetchGenreMovieList()
                _genreMovieDataList.value = response.genres
            } catch (e: Exception) {
                handleError(e)
            } finally {
                setLoadingState(false)
            }
        }
    }
}
