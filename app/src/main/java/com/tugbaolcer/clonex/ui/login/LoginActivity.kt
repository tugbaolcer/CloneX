package com.tugbaolcer.clonex.ui.login

import com.tugbaolcer.clonex.R
import com.tugbaolcer.clonex.base.CloneXBaseActivity
import com.tugbaolcer.clonex.databinding.ActivityLoginBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * created view by Tugba OLCER
 * date: 3.1.25
 */

@AndroidEntryPoint
class LoginActivity : CloneXBaseActivity<LoginViewModel, ActivityLoginBinding>() {
    override val layoutResourceId: Int
        get() = R.layout.activity_login

    override fun init() {}

    override fun initTopBar(title: Int?) {
        binding.layoutTopbar.setupWithLogin()
    }

    override fun retrieveNewData() {}

    override fun bindingData() {
        binding.vm = viewModel
        binding.lifecycleOwner = this
    }

    override val viewModelClass: Class<LoginViewModel>
        get() = LoginViewModel::class.java

}