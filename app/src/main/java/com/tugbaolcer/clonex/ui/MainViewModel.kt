package com.tugbaolcer.clonex.ui

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.tugbaolcer.clonex.base.CloneXBaseRecyclerView
import com.tugbaolcer.clonex.base.CloneXBaseViewModel
import com.tugbaolcer.clonex.model.GetGenresResponse
import com.tugbaolcer.clonex.network.AppApi
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(private val api: AppApi) : CloneXBaseViewModel() {

    lateinit var genresMovieAdapter : CloneXBaseRecyclerView<GetGenresResponse.Genre>

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
