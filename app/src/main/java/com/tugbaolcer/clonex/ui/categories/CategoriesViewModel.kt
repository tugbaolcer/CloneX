package com.tugbaolcer.clonex.ui.categories

import com.tugbaolcer.clonex.base.CloneXBaseViewModel
import com.tugbaolcer.clonex.network.AppApi
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CategoriesViewModel @Inject constructor(val api: AppApi) : CloneXBaseViewModel(api) {
}