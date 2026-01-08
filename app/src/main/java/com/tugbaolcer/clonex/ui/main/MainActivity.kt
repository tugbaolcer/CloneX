package com.tugbaolcer.clonex.ui.main

import androidx.lifecycle.lifecycleScope
import com.tugbaolcer.clonex.R
import com.tugbaolcer.clonex.base.CloneXBaseActivity
import com.tugbaolcer.clonex.base.CloneXBaseRecyclerView
import com.tugbaolcer.clonex.databinding.ActivityMainBinding
import com.tugbaolcer.clonex.model.GetGenresResponse
import com.tugbaolcer.clonex.utils.ItemDecorationVertical
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

    private val genresAdapter by lazy {
        object : CloneXBaseRecyclerView<GetGenresResponse.Genre>() {}
    }

    override fun init() {

        setupRecyclerView()
        retrieveNewData()

        setupBottomNavigation()

        if (supportFragmentManager.findFragmentById(R.id.fragment_container) == null) {
            navigateTo(NavigationTab.Home)
        }

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

    private fun setupRecyclerView() {
        binding.recyclerDummy.apply {
            adapter = genresAdapter
            addItemDecoration(ItemDecorationVertical(16))
        }

        genresAdapter.clickListener = { genre, action ->

        }
    }

    override fun initTopBar(title: Int?) {
        binding.appTopBar.setupWithLogin()
    }

    override fun retrieveNewData() {
        viewModel.getGenreMovieList{
            lifecycleScope.launch {
                genresAdapter.submitList(it)
            }
        }
    }

    override fun bindingData() {
        binding.vm = viewModel
        binding.lifecycleOwner = this
    }

    override val viewModelClass: Class<MainViewModel>
        get() = MainViewModel::class.java

}