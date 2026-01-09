package com.tugbaolcer.clonex.utils

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.Toolbar
import com.tugbaolcer.clonex.databinding.LayoutTopbarBinding

/**
 * created view by Tugba OLCER
 * date: 9.1.26
 */

class AppTopBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : Toolbar(context, attrs, defStyleAttr) {

    val topBarBinding =
        LayoutTopbarBinding.inflate(LayoutInflater.from(context), this, true)


    fun setupOnboarding(
        showMenu: Boolean,
        showSignUp: Boolean,
        contract: OnboardingTopBarContract
    ) {
        hideAll()

        with(topBarBinding.onboardingLayout) {
            root.visibility = VISIBLE
            dropdownMenu.visibility = if (showMenu) VISIBLE else GONE
            btnSignUp.visibility = if (showSignUp) VISIBLE else GONE

            dropdownMenu.setOnSingleClickListener {
                contract.onMenuClicked()
            }

            btnSignUp.setOnSingleClickListener {
                contract.onSignUpClicked()
            }
        }
    }

    fun setupLogin() {
        topBarBinding.loginLayout.root.visibility = VISIBLE
    }

    fun setupHome(
        userName: String,
        contract: HomeTopBarContract
    ) {
        hideAll()

        with(topBarBinding.homeLayout) {
            root.visibility = VISIBLE
            tvTitle.text = "$userName için"

            ivSearch.setOnSingleClickListener {
                contract.onSearchClicked()
            }

            ivDownload.setOnSingleClickListener {
                contract.onDownloadClicked()
            }
        }
    }

    private fun hideAll() {
        topBarBinding.onboardingLayout.root.visibility = GONE
        topBarBinding.loginLayout.root.visibility = GONE
        topBarBinding.homeLayout.root.visibility = GONE
    }
}
