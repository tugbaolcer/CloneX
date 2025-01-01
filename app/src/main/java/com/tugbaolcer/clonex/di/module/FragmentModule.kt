package com.tugbaolcer.clonex.di.module

import com.tugbaolcer.clonex.ui.onboarding.TabOnBoardingFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {

    @ContributesAndroidInjector
    abstract fun contributeTabOnBoardingFragment(): TabOnBoardingFragment
}