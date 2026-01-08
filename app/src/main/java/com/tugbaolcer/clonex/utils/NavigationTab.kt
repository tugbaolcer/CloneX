package com.tugbaolcer.clonex.utils

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import com.tugbaolcer.clonex.R
import com.tugbaolcer.clonex.ui.home.HomeFragment
import com.tugbaolcer.clonex.ui.newpopular.NewPopularFragment
import com.tugbaolcer.clonex.ui.profil.ProfileFragment

sealed class NavigationTab(
    @IdRes val menuId: Int,
    val tag: String,
    val fragmentFactory: () -> Fragment
) {
    object Home : NavigationTab(R.id.nav_home, HOME, { HomeFragment() })
    object NewAndPopular : NavigationTab(R.id.nav_new, NEW_POPULAR, { NewPopularFragment() })
    object MyNetflix : NavigationTab(R.id.nav_profile, PROFILE, { ProfileFragment() })

    companion object {
        private val tabs by lazy {
            listOf(Home, NewAndPopular, MyNetflix)
        }
        fun fromMenuId(id: Int) = tabs.find { it.menuId == id }
    }
}