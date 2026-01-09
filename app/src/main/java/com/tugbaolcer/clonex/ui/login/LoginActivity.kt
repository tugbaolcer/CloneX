package com.tugbaolcer.clonex.ui.login

import android.content.Intent
import com.tugbaolcer.clonex.R
import com.tugbaolcer.clonex.base.CloneXBaseActivity
import com.tugbaolcer.clonex.databinding.ActivityLoginBinding
import com.tugbaolcer.clonex.ui.main.MainActivity
import com.tugbaolcer.clonex.utils.CustomTabsHelper
import dagger.hilt.android.AndroidEntryPoint

/**
 * created view by Tugba OLCER
 * date: 3.1.25
 */

@AndroidEntryPoint
class LoginActivity : CloneXBaseActivity<LoginViewModel, ActivityLoginBinding>() {
    override val layoutResourceId: Int
        get() = R.layout.activity_login

    override fun init() {
        observerViewModel()
    }

    override fun initTopBar(title: Int?) {
        binding.layoutTopbar.setupLogin()
    }

    override fun retrieveNewData() {}

    override fun bindingData() {
        binding.vm = viewModel
        binding.lifecycleOwner = this
    }

    fun observerViewModel(){
        viewModel.authUrl.observe(this) { url ->
            CustomTabsHelper.openUrl(this@LoginActivity, url)
        }

        viewModel.loginSuccess.observe(this@LoginActivity){
            openMainScreen()
        }
    }

    private fun openMainScreen() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    override val viewModelClass: Class<LoginViewModel>
        get() = LoginViewModel::class.java

}