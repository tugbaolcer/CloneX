package com.tugbaolcer.clonex.ui.home

import com.tugbaolcer.clonex.R
import com.tugbaolcer.clonex.base.CloneXBaseFragment
import com.tugbaolcer.clonex.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment: CloneXBaseFragment<HomeViewModel, FragmentHomeBinding>() {
    override val layoutResourceId: Int
        get() = R.layout.fragment_home

    override val viewModelClass: Class<HomeViewModel>
        get() = HomeViewModel::class.java

    override fun init() {}

    override fun initTopBar() {}

    override fun retrieveData() {}

    override fun bindingData() {
        binding.apply {
            vm = viewModel
            lifecycleOwner = viewLifecycleOwner
        }
    }
}