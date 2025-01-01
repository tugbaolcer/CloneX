package com.tugbaolcer.clonex.ui.onboarding

import androidx.lifecycle.MutableLiveData
import com.tugbaolcer.clonex.base.CloneXBaseViewModel
import com.tugbaolcer.clonex.network.AppApi
import javax.inject.Inject

/**
 * created view by Tugba OLCER
 * date: 1.1.25
 */

class TabOnBoardingViewModel @Inject constructor(private val api: AppApi)  : CloneXBaseViewModel() {
    enum class OnBoardingType{
        STEP_ONE,
        STEP_TWO,
        STEP_THREE,
        STEP_FOUR
    }
    var onBoardingType = MutableLiveData<OnBoardingType?>()
}