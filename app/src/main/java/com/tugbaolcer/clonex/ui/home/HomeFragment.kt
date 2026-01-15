package com.tugbaolcer.clonex.ui.home

import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.tugbaolcer.clonex.R
import com.tugbaolcer.clonex.base.CloneXBaseFragment
import com.tugbaolcer.clonex.base.CloneXBaseRecyclerView
import com.tugbaolcer.clonex.databinding.FragmentHomeBinding
import com.tugbaolcer.clonex.model.GetTrendingAllResponse
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment: CloneXBaseFragment<HomeViewModel, FragmentHomeBinding>() {
    override val layoutResourceId: Int
        get() = R.layout.fragment_home

    private val trendingListAdapter by lazy {
        object : CloneXBaseRecyclerView<GetTrendingAllResponse.TrendingItem>() {}
    }

    private val movieListAdapter by lazy {
        object : CloneXBaseRecyclerView<GetTrendingAllResponse.TrendingItem>() {}
    }

    private val tvListAdapter by lazy {
        object : CloneXBaseRecyclerView<GetTrendingAllResponse.TrendingItem>() {}
    }

    override val viewModelClass: Class<HomeViewModel>
        get() = HomeViewModel::class.java

    override fun init() {
        setupRecyclerView()
        retrieveData()
    }

    private fun setupRecyclerView() {
        binding.rvTrending.apply {
            adapter = trendingListAdapter
//            addItemDecoration(ItemDecorationHorizontal(16))
        }

        binding.rvMovies.apply {
            adapter = movieListAdapter
//            addItemDecoration(ItemDecorationHorizontal(8))
        }

        binding.rvTv.apply {
            adapter = tvListAdapter
//            addItemDecoration(ItemDecorationHorizontal(8))
        }
    }

    override fun initTopBar() {}

    override fun retrieveData() {
        viewModel.getTrendingAll()

        lifecycleScope.launch {
            launch {
                viewModel.trendingList.collect { list ->
                    trendingListAdapter.submitList(list)
                    Log.d("LOG_DATA", "list: $list")
                }
            }
            launch {
                viewModel.movieList.collect { list ->
                    movieListAdapter.submitList(list)
                }
            }
            launch {
                viewModel.tvList.collect { list ->
                    tvListAdapter.submitList(list)
                }
            }
        }
    }

    override fun bindingData() {
        binding.apply {
            vm = viewModel
            lifecycleOwner = viewLifecycleOwner
        }
    }
}