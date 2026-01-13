package com.tugbaolcer.clonex.ui.main

import androidx.lifecycle.viewModelScope
import com.tugbaolcer.clonex.base.CloneXBaseViewModel
import com.tugbaolcer.clonex.model.GetGenresResponse
import com.tugbaolcer.clonex.network.AppApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class ChipUIState {
    object Home : ChipUIState()
    object Series : ChipUIState()
    object Movies : ChipUIState()
    object Categories: ChipUIState()
}

@HiltViewModel
class MainViewModel @Inject constructor(val api: AppApi) : CloneXBaseViewModel(api) {

    private val _categoriesState = MutableStateFlow<List<GetGenresResponse.Genre>>(emptyList())
    val categoriesState = _categoriesState.asStateFlow()

    private val _uiState = MutableStateFlow<ChipUIState>(ChipUIState.Home)
    val uiState: StateFlow<ChipUIState> = _uiState.asStateFlow()

    fun onSeriesClicked() {
        _uiState.value = ChipUIState.Series
    }

    fun onMoviesClicked() {
        _uiState.value = ChipUIState.Movies
    }

    fun onCloseClicked() {
        _uiState.value = ChipUIState.Home
    }

    fun onCategoriesClicked() {
        _uiState.value = ChipUIState.Categories
    }

    init {
        preloadCategories()
    }

    private fun preloadCategories() {
        viewModelScope.launch {
            networkCallAsFlow { api.fetchGenreMovieList() }
                .collect { result ->
                    handleApiResult(result, onSuccess = { _categoriesState.value = it.genres })
                }
        }
    }
}