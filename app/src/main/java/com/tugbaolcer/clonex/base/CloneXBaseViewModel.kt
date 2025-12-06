package com.tugbaolcer.clonex.base

import android.content.SharedPreferences
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


abstract class CloneXBaseViewModel(val baseApi: AppApi) : ViewModel() {

    val progressVisibility = MutableStateFlow(false)
    val errorMessage = MutableStateFlow<Pair<Int, String>?>(null)

    fun <T : Any> networkCallAsFlow(call: suspend () -> Response<T>): Flow<ApiResult<T?>> = flow {
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
        apiResult: ApiResult<T?>,
        onSuccess: (T) -> Unit,
        onError: (String) -> Unit = { error -> Log.e("LOG_API_STATUS", error) }
    ) {
        when (apiResult) {
            is ApiResult.Success -> {
                setLoading(isVisible = false)
                apiResult.data?.let { onSuccess(it) }
            }

            is ApiResult.Error -> {
                setLoading(isVisible = false)
                val error = Pair(apiResult.code, apiResult.exception.message.orEmpty())
                setErrorMessage(error)
                onError(apiResult.exception.message.orEmpty())
            }

            else -> {
                Log.d("LOG_API_STATUS", "Loading...")
                setLoading(isVisible = true)
            }
        }
    }

    protected fun setLoading(isVisible: Boolean) {
        progressVisibility.value = isVisible
    }

    protected fun setErrorMessage(error: Pair<Int, String>) {
        errorMessage.value = error
    }

    fun clearErrorMessage() {
        errorMessage.value = null
    }
}
