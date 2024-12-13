package com.tugbaolcer.clonex.base

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tugbaolcer.clonex.network.AppApi
import com.tugbaolcer.clonex.network.model.BaseResponseModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


open class CloneXBaseViewModel(
    val baseAppApi: AppApi,
    var prefs: SharedPreferences,
    private val controllerNameStr: String
) : ViewModel() {

    fun <T> performApiCall(
        apiCall: suspend () -> BaseResponseModel<T>,
        handleOnSuccess: (T) -> Unit,
        handleOnError: (String) -> Unit
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = apiCall()
                handleApiResponse(response,
                    onSuccess = { result: T ->
                        handleOnSuccess(result)
                    },
                    onError = { errorMessage ->
                        handleOnError(errorMessage)
                    }
                )
            } catch (e: Exception) {
                handleOnError("Error de red: ${e.message}")
            }
        }
    }

    fun <T> handleApiResponse(response: BaseResponseModel<T>, onSuccess: (T) -> Unit, onError: (String) -> Unit) {
        if (response.isSuccessful) {
            response.data?.let { responseBody ->
                onSuccess(responseBody)
            } ?: onError("Hata")
        } else {
            val errorMessage = response.errorMessage ?: "Hataaaa"
            onError(errorMessage)
        }
    }
}