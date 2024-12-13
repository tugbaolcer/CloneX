package com.tugbaolcer.clonex.di.module

import androidx.lifecycle.ViewModelProvider
import com.tugbaolcer.clonex.di.ViewModelFactory
import dagger.Binds
import dagger.Module

@Suppress("unused")
@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

//    @Binds
//    @IntoMap
//    @ViewModelKey(MainViewModel::class)
//    abstract fun vmMain(viewModel: MainViewModel): ViewModel
}