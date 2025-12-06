package com.tugbaolcer.clonex.ui.onboarding

import androidx.lifecycle.MutableLiveData
import com.tugbaolcer.clonex.base.CloneXBaseViewModel
import com.tugbaolcer.clonex.network.AppApi
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * created view by Tugba OLCER
 * date: 1.1.25
 */

@HiltViewModel
class TabOnBoardingViewModel @Inject constructor(val api: AppApi) : CloneXBaseViewModel(api) {
    enum class OnBoardingType{
        STEP_ONE,
        STEP_TWO,
        STEP_THREE,
        STEP_FOUR
    }
    var onBoardingType = MutableLiveData<OnBoardingType?>()
}