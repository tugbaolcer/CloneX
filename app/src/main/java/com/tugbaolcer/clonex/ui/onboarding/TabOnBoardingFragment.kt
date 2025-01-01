package com.tugbaolcer.clonex.ui.onboarding

import com.tugbaolcer.clonex.R
import com.tugbaolcer.clonex.base.CloneXBaseFragment
import com.tugbaolcer.clonex.databinding.FragmentTabOnBoardingBinding
import com.tugbaolcer.clonex.utils.withArgs

/**
 * created view by Tugba OLCER
 * date: 1.1.25
 */

class TabOnBoardingFragment : CloneXBaseFragment<TabOnBoardingViewModel, FragmentTabOnBoardingBinding>() {
    override val layoutResourceId: Int
        get() = R.layout.fragment_tab_on_boarding

    companion object {
        fun newInstance(step_type: Int) = TabOnBoardingFragment().withArgs {
            putInt("STEP_TYPE", step_type)
        }
    }

    override fun init() {
        val stepType = requireArguments().getInt("STEP_TYPE", 0)
        viewModel.onBoardingType.value = when (stepType) {
            0 -> TabOnBoardingViewModel.OnBoardingType.STEP_ONE
            1 -> TabOnBoardingViewModel.OnBoardingType.STEP_TWO
            2 -> TabOnBoardingViewModel.OnBoardingType.STEP_THREE
            3 -> TabOnBoardingViewModel.OnBoardingType.STEP_FOUR
            else -> TabOnBoardingViewModel.OnBoardingType.STEP_ONE
        }
    }

    override fun initTopBar() {
    }

    override fun retrieveData() {
    }

    override fun bindingData() {
        binding.vm = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
    }

    override val viewModelClass: Class<TabOnBoardingViewModel>
        get() = TabOnBoardingViewModel::class.java

}