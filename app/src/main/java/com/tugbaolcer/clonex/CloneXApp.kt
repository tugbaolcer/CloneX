package com.tugbaolcer.clonex

import android.app.Application
import androidx.appcompat.app.AlertDialog
import com.tugbaolcer.clonex.utils.ACCESS_TOKEN
import com.tugbaolcer.clonex.utils.REFRESH_TOKEN
import com.tugbaolcer.clonex.utils.Utils
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class CloneXApp : Application() {

    companion object {
        var mAlertDialog: AlertDialog? = null
    }

    fun saveToken(accessToken: String, refreshToken: String?) {
        val tokenPrefs = Utils.getEncryptedTokenSharedPrefs(this.applicationContext)
        tokenPrefs.edit().apply {
            putString(ACCESS_TOKEN, accessToken)
            putString(REFRESH_TOKEN, refreshToken)
            apply()
        }
    }
}