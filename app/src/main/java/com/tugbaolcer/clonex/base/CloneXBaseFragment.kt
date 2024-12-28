package com.tugbaolcer.clonex.base

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.tugbaolcer.clonex.utils.ProgressDialog
import dagger.android.support.AndroidSupportInjection
import kotlinx.coroutines.launch
import javax.inject.Inject

abstract class CloneXBaseFragment<VM : CloneXBaseViewModel, B : ViewDataBinding> : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var viewModel: VM

    protected abstract val layoutResourceId: Int
    protected abstract fun init()

    abstract fun initTopBar()

    abstract fun retrieveData()
    abstract fun bindingData()

    abstract val viewModelClass: Class<VM>

    private var progressDialog: ProgressDialog? = null

    protected lateinit var binding: B

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, layoutResourceId, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this, viewModelFactory)[viewModelClass]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (isAdded) {
            Log.d("Fragment", "$javaClass Fragment is added .")
            bindingData()
            init()
            initTopBar()
            retrieveData()

            progressDialog = ProgressDialog(requireContext())

            lifecycleScope.launch {
                viewModel.progressVisibility.collect { isVisible ->
                    if (isVisible) progressDialog?.show()
                    else progressDialog?.dismiss()
                }
            }

        } else {
            Log.d("Fragment", "$javaClass Fragment is not added.")
        }
    }
}