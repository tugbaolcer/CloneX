package com.tugbaolcer.clonex.ui.splash

import com.tugbaolcer.clonex.base.CloneXBaseViewModel
import com.tugbaolcer.clonex.di.datastore.SecureDataStore
import com.tugbaolcer.clonex.network.AppApi
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    val api: AppApi,
    private val dataStore: SecureDataStore
) : CloneXBaseViewModel(api) {

    suspend fun hasSession(): Boolean = dataStore.getSessionId() != null

}
