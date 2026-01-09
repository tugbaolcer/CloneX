package com.tugbaolcer.clonex.base

import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.Insets
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.tugbaolcer.clonex.R
import com.tugbaolcer.clonex.utils.ProgressDialog
import com.tugbaolcer.clonex.utils.showErrorAlert
import kotlinx.coroutines.launch

enum class ScreenMode {
    EDGE_TO_EDGE,
    FULLSCREEN,
    NORMAL
}

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

    open val screenMode: ScreenMode = ScreenMode.EDGE_TO_EDGE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, layoutResourceId)
        progressDialog = ProgressDialog(this)

        observeBaseStates()

        bindingData()
        init()
        initTopBar()

        setupScreenMode()

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

    private fun setupScreenMode() {
        when (screenMode) {
            ScreenMode.EDGE_TO_EDGE -> {
                enableEdgeToEdge()
                ViewCompat.setOnApplyWindowInsetsListener(window.decorView) { view, insets ->
                    val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                    onApplyInsets(view, systemBars)
                    insets
                }
            }
            ScreenMode.FULLSCREEN -> {
                enableEdgeToEdge()
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    window.setDecorFitsSystemWindows(false)
                    window.insetsController?.let { controller ->
                        controller.hide(WindowInsets.Type.systemBars())
                        controller.systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
                    }
                } else {
                    @Suppress("DEPRECATION")
                    window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            or View.SYSTEM_UI_FLAG_FULLSCREEN or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
                }
            }
            ScreenMode.NORMAL -> {
                // Standart sistem davranışı
            }
        }
    }

    open fun onApplyInsets(rootView: View, systemBars: Insets) {}
}