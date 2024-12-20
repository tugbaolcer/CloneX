package com.tugbaolcer.clonex.base

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


abstract class CloneXBaseActivity<VM : CloneXBaseViewModel, B : ViewDataBinding> : AppCompatActivity(),
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


    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        this.savedInstanceState = savedInstanceState

        binding = DataBindingUtil.setContentView(this, layoutResourceId)

        viewModel = ViewModelProvider(this, viewModelFactory)[viewModelClass]

        bindingData()
        init()
        initTopBar()

        lifecycleScope.launch {
            viewModel.uiState.collectLatest { state ->
                when (state) {
                    is UIState.SuccessResult -> {
                        // Default UI state
                    }
                    is UIState.Loading -> {
                        toggleLoadingIndicator(isLoading = true)
                    }
                    is UIState.ErrorResult -> {
                        toggleLoadingIndicator(isLoading = false)
                        showError(state.message)
                    }
                }
            }
        }
    }

    open fun showError(message: String?) {
        // Default error handling (can be overridden)
    }

    open fun toggleLoadingIndicator(isLoading: Boolean) {
        // Default loading indicator toggle (can be overridden)
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
}