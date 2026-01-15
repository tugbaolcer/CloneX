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
import com.tugbaolcer.clonex.R
import com.tugbaolcer.clonex.utils.ProgressDialog
import com.tugbaolcer.clonex.utils.showErrorAlert
import kotlinx.coroutines.launch


abstract class CloneXBaseFragment<VM : CloneXBaseViewModel, B : ViewDataBinding> : Fragment() {

    protected abstract val layoutResourceId: Int
    abstract val viewModelClass: Class<VM>

    protected val viewModel: VM by lazy {
        ViewModelProvider(this)[viewModelClass]
    }

    protected lateinit var binding: B

    private var progressDialog: ProgressDialog? = null

    protected abstract fun init()
    abstract fun initTopBar()
    abstract fun retrieveData()
    abstract fun bindingData()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, layoutResourceId, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (!isAdded) return

        Log.d("Fragment", "$javaClass Fragment is added.")

        setupBaseObservers()

        bindingData()
        init()
        initTopBar()
    }

    private fun setupBaseObservers() {
        progressDialog = ProgressDialog(requireContext())

        lifecycleScope.launch {
            viewModel.progressVisibility.collect { isVisible ->
                if (isVisible) progressDialog?.show()
                else progressDialog?.dismiss()
            }
        }

        lifecycleScope.launch {
            viewModel.errorMessage.collect { errorMessage ->
                errorMessage?.let { (code, message) ->
                    val finalMessage =
                        if (code == 500) getString(R.string.Common_Request_ErrorMessage) else message

                    requireContext().showErrorAlert(finalMessage)
                    viewModel.clearErrorMessage()
                }
            }
        }
    }
}
