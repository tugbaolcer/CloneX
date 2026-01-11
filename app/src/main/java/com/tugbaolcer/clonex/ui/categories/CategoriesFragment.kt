package com.tugbaolcer.clonex.ui.categories

import androidx.lifecycle.lifecycleScope
import com.tugbaolcer.clonex.R
import com.tugbaolcer.clonex.base.CloneXBaseFragment
import com.tugbaolcer.clonex.base.CloneXBaseRecyclerView
import com.tugbaolcer.clonex.databinding.FragmentCategoriesBinding
import com.tugbaolcer.clonex.model.GetGenresResponse
import com.tugbaolcer.clonex.utils.ItemDecorationVertical
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class CategoriesFragment: CloneXBaseFragment<CategoriesViewModel, FragmentCategoriesBinding>() {
    override val layoutResourceId: Int
        get() = R.layout.fragment_categories

    override val viewModelClass: Class<CategoriesViewModel>
        get() = CategoriesViewModel::class.java

    private val genresAdapter by lazy {
        object : CloneXBaseRecyclerView<GetGenresResponse.Genre>() {}
    }

    override fun init() {
        setupRecyclerView()
        retrieveData()

    }

    private fun setupRecyclerView() {
        binding.rvGenres.apply {
            adapter = genresAdapter
            addItemDecoration(ItemDecorationVertical(16))
        }

        genresAdapter.clickListener = { genre, action ->

        }
    }

    override fun initTopBar() {}

    override fun retrieveData() {
        viewModel.getGenreMovieList{
            lifecycleScope.launch {
                genresAdapter.submitList(it)
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