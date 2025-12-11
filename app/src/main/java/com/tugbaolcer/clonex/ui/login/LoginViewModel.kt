package com.tugbaolcer.clonex.ui.login

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.tugbaolcer.clonex.base.CloneXBaseViewModel
import com.tugbaolcer.clonex.di.datastore.SecureDataStore
import com.tugbaolcer.clonex.model.CreateSessionRequest
import com.tugbaolcer.clonex.network.AppApi
import com.tugbaolcer.clonex.model.CreateLoginRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    val api: AppApi,
    private val dataStore: SecureDataStore
) : CloneXBaseViewModel(api) {

    val userName = MutableLiveData<String>()
    val password = MutableLiveData<String>()

    val loginSuccess = MutableLiveData<Unit>()


    fun onLoginClicked() {
        viewModelScope.launch {
            if (userName.value.isNullOrEmpty() || password.value.isNullOrEmpty()) {
                setErrorMessage(Pair(-1, "Email ve şifre zorunlu"))
                return@launch
            } else {
                createRequestToken()
            }
        }
    }


    private fun createRequestToken() {
        viewModelScope.launch {
            networkCallAsFlow { api.createRequestToken() }.collect {result ->
                handleApiResult(
                    apiResult = result,
                    onSuccess = { data ->

                        createLogin(data.requestToken)
                    }
                )
            }
        }
    }


    private fun createLogin(requestToken: String) {
        viewModelScope.launch {
            val body = CreateLoginRequest(
                username = userName.value!!,
                password = password.value!!,
                requestToken = requestToken
            )

            networkCallAsFlow { api.createLogin(body) }.collect { result ->
                handleApiResult(
                    result,
                    onSuccess = { data ->
                        createSession(data.requestToken)
                    }
                )
            }
        }
    }


    private fun createSession(requestToken: String) {
        viewModelScope.launch {
            networkCallAsFlow { api.createSessionId(CreateSessionRequest(requestToken = requestToken)) }
                .collect { result ->
                    handleApiResult(
                        result,
                        onSuccess = { data ->
                            viewModelScope.launch {
                                dataStore.saveSessionId(sessionId = data.sessionId)
                                loginSuccess.value = Unit
                                Log.d("LOG_DATA", "createSession: ${dataStore.getSessionId()}")
                            }
                        }
                    )
                }
        }
    }
}
