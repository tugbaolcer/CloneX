package com.tugbaolcer.clonex.ui.profil

import com.tugbaolcer.clonex.R
import com.tugbaolcer.clonex.base.CloneXBaseFragment
import com.tugbaolcer.clonex.databinding.FragmentProfileBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ProfileFragment: CloneXBaseFragment<ProfileViewModel, FragmentProfileBinding>() {
    override val layoutResourceId: Int
        get() = R.layout.fragment_profile

    override val viewModelClass: Class<ProfileViewModel>
        get() = ProfileViewModel::class.java

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