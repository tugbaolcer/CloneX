package com.tugbaolcer.clonex.ui.main

import androidx.lifecycle.lifecycleScope
import com.tugbaolcer.clonex.R
import com.tugbaolcer.clonex.base.CloneXBaseActivity
import com.tugbaolcer.clonex.base.CloneXBaseRecyclerView
import com.tugbaolcer.clonex.databinding.ActivityMainBinding
import com.tugbaolcer.clonex.model.GetGenresResponse
import com.tugbaolcer.clonex.utils.ItemDecorationVertical
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : CloneXBaseActivity<MainViewModel, ActivityMainBinding>() {
    override val layoutResourceId: Int
        get() = R.layout.activity_main

    private val genresAdapter by lazy {
        object : CloneXBaseRecyclerView<GetGenresResponse.Genre>() {}
    }

    override fun init() {

        setupRecyclerView()
        retrieveNewData()

    }

    private fun setupRecyclerView() {
        binding.recyclerDummy.apply {
            adapter = genresAdapter
            addItemDecoration(ItemDecorationVertical(16))
        }

        genresAdapter.clickListener = { genre, action ->

        }
    }

    override fun initTopBar(title: Int?) {}

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