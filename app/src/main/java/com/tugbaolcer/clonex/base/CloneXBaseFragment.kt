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
import com.tugbaolcer.clonex.utils.LoadingStateDelegate


abstract class CloneXBaseFragment<VM : CloneXBaseViewModel, B : ViewDataBinding> : Fragment() {

    protected abstract val layoutResourceId: Int
    abstract val viewModelClass: Class<VM>

    protected val viewModel: VM by lazy {
        ViewModelProvider(this)[viewModelClass]
    }

    protected lateinit var binding: B

    open fun provideShimmerView(): View? = null
    open fun provideContentView(): View? = null

    private val loadingDelegate by lazy {
        LoadingStateDelegate(
            lifecycleOwner = viewLifecycleOwner,
            viewModel = viewModel,
            contextProvider = { requireContext() },
            shimmerProvider = { provideShimmerView() },
            contentProvider = { provideContentView()}
        )
    }

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

        loadingDelegate.setup()

        bindingData()
        init()
        initTopBar()
    }
}
