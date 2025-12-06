package com.tugbaolcer.clonex.base

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.tugbaolcer.clonex.R
import com.tugbaolcer.clonex.utils.AppTopBar
import com.tugbaolcer.clonex.utils.ProgressDialog
import com.tugbaolcer.clonex.utils.showErrorAlert
import kotlinx.coroutines.launch


abstract class CloneXBaseActivity<VM : CloneXBaseViewModel, B : ViewDataBinding> :
    AppCompatActivity() {

    protected abstract val layoutResourceId: Int
    abstract val viewModelClass: Class<VM>

    protected val viewModel: VM by lazy {
        ViewModelProvider(this)[viewModelClass]
    }

    protected lateinit var binding: B

    abstract fun init()
    abstract fun initTopBar(title: Int? = null)
    abstract fun retrieveNewData()
    abstract fun bindingData()

    private var progressDialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, layoutResourceId)
        progressDialog = ProgressDialog(this)

        observeBaseStates()

        bindingData()
        init()
        initTopBar()

        AppTopBar.apply {
            topBarBackgroundColor.observe(this@CloneXBaseActivity) {
                window.statusBarColor = ContextCompat.getColor(this@CloneXBaseActivity, it)
            }
        }
    }

    private fun observeBaseStates() {
        lifecycleScope.launch {
            viewModel.progressVisibility.collect { isVisible ->
                if (isVisible) progressDialog?.show()
                else progressDialog?.dismiss()
            }
        }

        lifecycleScope.launch {
            viewModel.errorMessage.collect { errorMessage ->
                errorMessage?.let { message ->
                    showMessageOnRetrieveError(message)
                    viewModel.clearErrorMessage()
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) finish()
        return super.onOptionsItemSelected(item)
    }

    fun showMessageOnRetrieveError(errorPair: Pair<Int?, String?>) {
        val errorMessage = when {
            errorPair.first == 500 -> getString(R.string.Common_Request_ErrorMessage)
            else -> errorPair.second ?: getString(R.string.Common_Request_ErrorMessage)
        }
        showErrorAlert(errorMessage)
    }
}