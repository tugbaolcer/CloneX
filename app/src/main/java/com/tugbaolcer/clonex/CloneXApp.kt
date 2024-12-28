package com.tugbaolcer.clonex

import android.app.Application
import androidx.appcompat.app.AlertDialog
import com.tugbaolcer.clonex.di.component.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject


class CloneXApp : Application(), HasAndroidInjector {

    companion object {
        var mAlertDialog: AlertDialog? = null
    }

    override fun androidInjector(): AndroidInjector<Any> {
        return dispatchingAndroidInjector
    }

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    override fun onCreate() {
        super.onCreate()
        DaggerAppComponent
            .builder()
            .application(this)
            .build()
            .inject(this)
    }
}