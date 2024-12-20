package com.tugbaolcer.clonex.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


abstract class CloneXBaseViewModel : ViewModel() {
    private val _uiState = MutableStateFlow<UIState>(UIState.SuccessResult)
    val uiState: StateFlow<UIState> get() = _uiState

    protected fun handleError(exception: Exception) {
        _uiState.value = UIState.ErrorResult(exception.message ?: "An error occurred")
    }

    protected fun setLoadingState(isLoading: Boolean) {
        _uiState.value = if (isLoading) UIState.Loading else UIState.SuccessResult
    }
}

sealed class UIState {
    object SuccessResult : UIState()
    object Loading : UIState()
    data class ErrorResult(val message: String) : UIState()
}