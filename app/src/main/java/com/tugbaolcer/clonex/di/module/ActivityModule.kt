package com.tugbaolcer.clonex.di.module

import com.tugbaolcer.clonex.ui.MainActivity
import com.tugbaolcer.clonex.ui.onboarding.OnboardingActivity
import com.tugbaolcer.clonex.ui.splash.SplashActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {

    @ContributesAndroidInjector(modules = [FragmentModule::class])
    abstract fun contributeMainActivity(): MainActivity

    @ContributesAndroidInjector(modules = [FragmentModule::class])
    abstract fun contributeSplashActivity(): SplashActivity

    @ContributesAndroidInjector(modules = [FragmentModule::class])
    abstract fun contributeOnboardingActivity(): OnboardingActivity
}