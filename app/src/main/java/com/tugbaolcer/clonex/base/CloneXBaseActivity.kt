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
import com.tugbaolcer.clonex.utils.LoadingStateDelegate

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

    open val screenMode: ScreenMode = ScreenMode.EDGE_TO_EDGE

    open fun provideShimmerView(): View? = null
    open fun provideContentView(): View? = null

    private val loadingDelegate by lazy {
        LoadingStateDelegate(
            lifecycleOwner = this,
            viewModel = viewModel,
            contextProvider = { this },
            shimmerProvider = { provideShimmerView() }
            ,
            contentProvider = { provideContentView()}
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, layoutResourceId)

        loadingDelegate.setup()

        bindingData()
        init()
        initTopBar()

        setupScreenMode()

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) finish()
        return super.onOptionsItemSelected(item)
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