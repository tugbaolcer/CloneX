package com.tugbaolcer.clonex.di.component

import android.app.Application
import com.tugbaolcer.clonex.CloneXApp
import com.tugbaolcer.clonex.di.module.ActivityModule
import com.tugbaolcer.clonex.di.module.AppModule
import com.tugbaolcer.clonex.di.module.NetworkModule
import com.tugbaolcer.clonex.di.module.ViewModelModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        AppModule::class,
        ActivityModule::class,
        ViewModelModule::class,
        NetworkModule::class
    ]
)
interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(app: CloneXApp)
}