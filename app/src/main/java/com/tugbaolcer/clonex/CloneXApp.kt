package com.tugbaolcer.clonex

import android.app.Application
import androidx.appcompat.app.AlertDialog
import com.tugbaolcer.clonex.utils.ACCESS_TOKEN
import com.tugbaolcer.clonex.utils.REFRESH_TOKEN
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class CloneXApp : Application() {

    companion object {
        var mAlertDialog: AlertDialog? = null
    }

}