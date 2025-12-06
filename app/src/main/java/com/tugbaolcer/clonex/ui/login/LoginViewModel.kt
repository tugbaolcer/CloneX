package com.tugbaolcer.clonex.ui.login

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.tugbaolcer.clonex.BuildConfig
import com.tugbaolcer.clonex.base.CloneXBaseViewModel
import com.tugbaolcer.clonex.model.CreateLoginRequest
import com.tugbaolcer.clonex.network.AppApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(val api: AppApi) : CloneXBaseViewModel(api) {

    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()

    fun onLoginClicked(){
        viewModelScope.launch {
            val response = CreateLoginRequest(
                username = email.value!!,
                password = password.value!!,
                requestToken = BuildConfig.API_KEY
            )
            networkCallAsFlow { api.createLogin(response) }
                .collect { result ->
                    handleApiResult(
                        apiResult = result,
                        onSuccess = { response ->

                        },
                        onError = { errorMessage ->
                            Log.e("LOG_DATA", "Error: $errorMessage")
                        }
                    )
                }
        }
    }
}
