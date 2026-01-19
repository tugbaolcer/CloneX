package com.tugbaolcer.clonex.base

import android.util.Log
import androidx.lifecycle.ViewModel
import com.tugbaolcer.clonex.network.AppApi
import com.tugbaolcer.clonex.network.model.ApiResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import java.io.IOException


enum class LoadingType {
    PROGRESS_DIALOG,
    SHIMMER,
    NONE
}

abstract class CloneXBaseViewModel(val baseApi: AppApi) : ViewModel() {

    val loadingState = MutableStateFlow<LoadingType>(LoadingType.NONE)
    val errorMessage = MutableStateFlow<Pair<Int, String>?>(null)

    fun <T : Any> networkCallAsFlow(
        call: suspend () -> Response<T>
    ): Flow<ApiResult<T?>> = flow {
        emit(ApiResult.Loading)
        val response = call()
        if (response.isSuccessful && response.body() != null) {
            emit(ApiResult.Success(response.body()))
        } else {
            val errorCode = response.code() // Hata kodunu yakala
            val errorMessage = response.errorBody()?.string().orEmpty()
            emit(ApiResult.Error(IOException(errorMessage), errorCode))
        }
    }.catch { e ->
        emit(ApiResult.Error(IOException(e.message), -1)) // -1: Genel hata kodu
    }.flowOn(Dispatchers.IO)

    fun <T : Any> handleApiResult(
        loadingType: LoadingType = LoadingType.PROGRESS_DIALOG,
        apiResult: ApiResult<T?>,
        onSuccess: (T) -> Unit,
        onError: (String) -> Unit = { error -> Log.e("LOG_API_STATUS", error) }
    ) {
        when (apiResult) {
            is ApiResult.Success -> {
                loadingState.value = LoadingType.NONE
                apiResult.data?.let { onSuccess(it) }
            }

            is ApiResult.Error -> {
                loadingState.value = LoadingType.NONE
                val error = Pair(apiResult.code, apiResult.exception.message.orEmpty())
                setErrorMessage(error)
                onError(apiResult.exception.message.orEmpty())
            }

            else -> {
                loadingState.value = loadingType
            }
        }
    }

    protected fun setErrorMessage(error: Pair<Int, String>) {
        errorMessage.value = error
    }

    fun clearErrorMessage() {
        errorMessage.value = null
    }
}
