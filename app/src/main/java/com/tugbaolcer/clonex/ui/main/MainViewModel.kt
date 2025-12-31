package com.tugbaolcer.clonex.ui.main

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.tugbaolcer.clonex.base.CloneXBaseViewModel
import com.tugbaolcer.clonex.model.GetGenresResponse
import com.tugbaolcer.clonex.network.AppApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(val api: AppApi) : CloneXBaseViewModel(api) {

    fun getGenreMovieList(handleOnSuccess: (MutableList<GetGenresResponse.Genre>) -> Unit) {
        viewModelScope.launch {
            networkCallAsFlow { api.fetchGenreMovieList() }
                .collect { result ->
                    handleApiResult(
                        apiResult = result,
                        onSuccess = { response ->
                            val genres = response.genres
                            handleOnSuccess(genres.toMutableList())
                        },
                        onError = { errorMessage ->
                            Log.e("LOG_DATA", "Error: $errorMessage")
                        }
                    )
                }
        }
    }


}