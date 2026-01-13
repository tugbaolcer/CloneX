package com.tugbaolcer.clonex.ui.main

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.tugbaolcer.clonex.R
import com.tugbaolcer.clonex.base.CloneXBaseActivity
import com.tugbaolcer.clonex.databinding.ActivityMainBinding
import com.tugbaolcer.clonex.ui.categories.CategoriesBottomSheet
import com.tugbaolcer.clonex.utils.HomeTopBarContract
import com.tugbaolcer.clonex.utils.NavigationTab
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

interface AppNavigator {
    fun navigateTo(tab: NavigationTab)
}

@AndroidEntryPoint
class MainActivity : CloneXBaseActivity<MainViewModel, ActivityMainBinding>(), AppNavigator {
    override val layoutResourceId: Int
        get() = R.layout.activity_main

    private var categoriesSheet: CategoriesBottomSheet? = null


    override fun init() {

        setupBottomNavigation()

        if (supportFragmentManager.findFragmentById(R.id.fragment_container) == null) {
            navigateTo(NavigationTab.Home)
        }

        observeChipUIState()

    }

    private fun setupBottomNavigation() {
        binding.bottomNavigation.apply {
            itemIconTintList = null

            setOnItemSelectedListener { item ->
                NavigationTab.fromMenuId(item.itemId)?.let { tab ->
                    navigateTo(tab)
                    true
                } ?: false
            }
        }
    }

    override fun navigateTo(tab: NavigationTab) {
        val currentFragment = supportFragmentManager.fragments.find { it.isVisible }
        val targetFragment = supportFragmentManager.findFragmentByTag(tab.tag)

        if (currentFragment == targetFragment && targetFragment != null) return

        supportFragmentManager.beginTransaction().apply {
            setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
            currentFragment?.let { hide(it) }

            if (targetFragment == null) {
                add(R.id.fragment_container, tab.fragmentFactory(), tab.tag)
            } else {
                show(targetFragment)
            }

            setReorderingAllowed(true)
            commit()
        }
    }

    private fun observeChipUIState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    when (state) {
                        is ChipUIState.Home -> {
                            navigateTo(NavigationTab.Home)
                        }
                        is ChipUIState.Series -> {
                            // Diziler fragmentına yönlendir veya listeyi filtrele
                        }
                        is ChipUIState.Movies -> {
                            // Filmler fragmentına yönlendir veya listeyi filtrele
                        }

                        is ChipUIState.Categories -> { openCategories() }

                        else -> closeCategories()
                    }
                }
            }
        }
    }

    private fun openCategories() {
        if (categoriesSheet?.isAdded == true) return

        categoriesSheet = CategoriesBottomSheet()
        categoriesSheet?.show(
            supportFragmentManager,
            "CategoriesBottomSheet"
        )
    }

    private fun closeCategories() {
        categoriesSheet?.dismiss()
        categoriesSheet = null
    }

    override fun initTopBar(title: Int?) {
        binding.appTopBar.setupHome(
            userName = "Tuğba",
            contract = object : HomeTopBarContract {
                override fun onSearchClicked() {

                }

                override fun onDownloadClicked() {

                }
            }
        )
    }

    override fun retrieveNewData() {}

    override fun bindingData() {
        binding.vm = viewModel
        binding.lifecycleOwner = this
    }

    override val viewModelClass: Class<MainViewModel>
        get() = MainViewModel::class.java

}