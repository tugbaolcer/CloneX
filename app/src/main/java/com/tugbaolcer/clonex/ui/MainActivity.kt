package com.tugbaolcer.clonex.ui

import androidx.lifecycle.lifecycleScope
import com.tugbaolcer.clonex.R
import com.tugbaolcer.clonex.base.CloneXBaseActivity
import com.tugbaolcer.clonex.base.CloneXBaseRecyclerView
import com.tugbaolcer.clonex.databinding.ActivityMainBinding
import com.tugbaolcer.clonex.utils.ItemDecorationVertical
import kotlinx.coroutines.launch

class MainActivity : CloneXBaseActivity<MainViewModel, ActivityMainBinding>() {
    override val layoutResourceId: Int
        get() = R.layout.activity_main

    override fun init() {

        viewModel.genresMovieAdapter = CloneXBaseRecyclerView(mutableListOf()) { item, _ ->

        }
        binding.recyclerDummy.adapter = viewModel.genresMovieAdapter
        binding.recyclerDummy.addItemDecoration(ItemDecorationVertical(16))

        retrieveNewData()

    }

    override fun initTopBar(title: Int?) {}

    override fun retrieveNewData() {
        viewModel.getGenreMovieList()
        lifecycleScope.launch {
            viewModel.genreMovieDataList.collect { items ->
                viewModel.genresMovieAdapter.setItems(items.toMutableList())
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