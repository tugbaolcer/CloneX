package com.tugbaolcer.clonex.ui.categories

import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.tugbaolcer.clonex.base.CloneXBaseBottomSheet
import com.tugbaolcer.clonex.base.CloneXBaseRecyclerView
import com.tugbaolcer.clonex.databinding.FragmentCategoriesBinding
import com.tugbaolcer.clonex.model.GetGenresResponse
import com.tugbaolcer.clonex.ui.main.MainViewModel
import com.tugbaolcer.clonex.utils.ItemDecorationVertical
import com.tugbaolcer.clonex.utils.collectIn
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CategoriesBottomSheet :
    CloneXBaseBottomSheet<FragmentCategoriesBinding>() {

    private val viewModel: MainViewModel by activityViewModels()

    private val genresAdapter by lazy {
        object : CloneXBaseRecyclerView<GetGenresResponse.Genre>() {}
    }

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentCategoriesBinding =
        FragmentCategoriesBinding.inflate(inflater, container, false)

    override fun initView() {
        setupRecyclerView()

        binding.btnClose.setOnClickListener {
            dismiss()
            viewModel.onCloseClicked()
        }

        collectIn(viewModel.categoriesState) {
            genresAdapter.submitList(it)
        }
    }

    private fun setupRecyclerView() {
        binding.rvGenres.apply {
            adapter = genresAdapter
            addItemDecoration(ItemDecorationVertical(24))
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        viewModel.onCloseClicked()
    }

}
