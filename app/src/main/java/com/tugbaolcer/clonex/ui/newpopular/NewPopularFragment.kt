package com.tugbaolcer.clonex.ui.newpopular

import com.tugbaolcer.clonex.R
import com.tugbaolcer.clonex.base.CloneXBaseFragment
import com.tugbaolcer.clonex.databinding.FragmentNewPopulerBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class NewPopularFragment: CloneXBaseFragment<NewPopularViewModel, FragmentNewPopulerBinding>() {
    override val layoutResourceId: Int
        get() = R.layout.fragment_new_populer

    override val viewModelClass: Class<NewPopularViewModel>
        get() = NewPopularViewModel::class.java

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