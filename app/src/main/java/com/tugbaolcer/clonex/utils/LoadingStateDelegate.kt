package com.tugbaolcer.clonex.utils

import android.content.Context
import android.view.View
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.facebook.shimmer.ShimmerFrameLayout
import com.tugbaolcer.clonex.R
import com.tugbaolcer.clonex.base.CloneXBaseViewModel
import com.tugbaolcer.clonex.base.LoadingType
import kotlinx.coroutines.launch

class LoadingStateDelegate(
    private val lifecycleOwner: LifecycleOwner,
    private val viewModel: CloneXBaseViewModel,
    private val contextProvider: () -> Context,
    private val shimmerProvider: () -> View?,
    private val contentProvider: () -> View?
) {

    private var progressDialog: ProgressDialog? = null

    fun setup() {
        progressDialog = ProgressDialog(contextProvider())

        lifecycleOwner.lifecycleScope.launch {
            viewModel.loadingState.collect { loadingType ->
                when (loadingType) {
                    LoadingType.PROGRESS_DIALOG -> {
                        toggleShimmer(false)
                        progressDialog?.show()
                    }
                    LoadingType.SHIMMER -> {
                        progressDialog?.dismiss()
                        toggleShimmer(true)
                    }
                    LoadingType.NONE -> {
                        progressDialog?.dismiss()
                        toggleShimmer(false)
                    }
                }
            }
        }

        lifecycleOwner.lifecycleScope.launch {
            viewModel.errorMessage.collect { errorMessage ->
                errorMessage?.let { message ->
                    showMessageOnRetrieveError(message)
                    viewModel.clearErrorMessage()
                }
            }
        }
    }

    fun showMessageOnRetrieveError(errorPair: Pair<Int?, String?>) {
        val errorMessage = when {
            errorPair.first == 500 -> contextProvider().getString(R.string.Common_Request_ErrorMessage)
            else -> errorPair.second ?: contextProvider().getString(R.string.Common_Request_ErrorMessage)
        }
        contextProvider().showErrorAlert(errorMessage)
    }

    private fun toggleShimmer(isVisible: Boolean) {
        val shimmerView = shimmerProvider() ?: return
        val contentView = contentProvider() ?: return

        if (isVisible) {
            contentView.visibility = View.GONE
            shimmerView.visibility = View.VISIBLE
            (shimmerView as? ShimmerFrameLayout)?.startShimmer()
        } else {
            (shimmerView as? ShimmerFrameLayout)?.stopShimmer()
            shimmerView.visibility = View.GONE
            contentView.visibility = View.VISIBLE
        }
    }
}
