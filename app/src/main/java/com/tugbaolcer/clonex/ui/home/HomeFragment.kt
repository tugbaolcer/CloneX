package com.tugbaolcer.clonex.ui.home

import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
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
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = trendingListAdapter
        }

        binding.rvMovies.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = movieListAdapter
        }

        binding.rvTv.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = tvListAdapter
        }
    }


    override fun initTopBar() {}

    override fun retrieveData() {
        viewModel.getTrendingAll()

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.trendingList.collect {
                    trendingListAdapter.submitList(it)
                    Log.e("DEBUG", "TRENDING SIZE = ${it.size}")
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.movieList.collect {
                    movieListAdapter.submitList(it)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.tvList.collect {
                    tvListAdapter.submitList(it)
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