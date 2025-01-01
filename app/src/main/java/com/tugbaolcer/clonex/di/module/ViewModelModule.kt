package com.tugbaolcer.clonex.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tugbaolcer.clonex.di.ViewModelFactory
import com.tugbaolcer.clonex.ui.MainViewModel
import com.tugbaolcer.clonex.ui.onboarding.OnboardingViewModel
import com.tugbaolcer.clonex.ui.onboarding.TabOnBoardingViewModel
import com.tugbaolcer.clonex.ui.splash.SplashViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Suppress("unused")
@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun vmMain(viewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SplashViewModel::class)
    abstract fun vmSplash(viewModel: SplashViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(OnboardingViewModel::class)
    abstract fun vmOnboarding(viewModel: OnboardingViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TabOnBoardingViewModel::class)
    abstract fun vmTabOnBoarding(viewModel: TabOnBoardingViewModel): ViewModel
}