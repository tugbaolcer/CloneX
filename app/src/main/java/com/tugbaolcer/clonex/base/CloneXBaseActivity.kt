package com.tugbaolcer.clonex.base

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.tugbaolcer.clonex.R
import com.tugbaolcer.clonex.utils.ProgressDialog
import com.tugbaolcer.clonex.utils.showErrorAlert
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import kotlinx.coroutines.launch
import javax.inject.Inject


abstract class CloneXBaseActivity<VM : CloneXBaseViewModel, B : ViewDataBinding> :
    AppCompatActivity(),
    HasAndroidInjector {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    protected abstract val layoutResourceId: Int

    protected lateinit var viewModel: VM

    protected lateinit var binding: B
    protected abstract fun init()
    abstract fun initTopBar(title: Int? = null)
    abstract fun retrieveNewData()
    abstract fun bindingData()

    abstract val viewModelClass: Class<VM>

    private var savedInstanceState: Bundle? = null

    private var progressDialog: ProgressDialog? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        this.savedInstanceState = savedInstanceState

        binding = DataBindingUtil.setContentView(this, layoutResourceId)

        viewModel = ViewModelProvider(this, viewModelFactory)[viewModelClass]

        progressDialog = ProgressDialog(this)

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

        bindingData()
        init()
        initTopBar()

    }

    override fun androidInjector(): AndroidInjector<Any> {
        return dispatchingAndroidInjector
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
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