package com.tugbaolcer.clonex.utils

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.Toolbar
import androidx.lifecycle.MutableLiveData
import com.tugbaolcer.clonex.R
import com.tugbaolcer.clonex.databinding.LayoutTopbarBinding

/**
 * created view by Tugba OLCER
 * date: 1.1.25
 */

class AppTopBar : Toolbar {

    @JvmOverloads
    constructor(context: Context) : super(context)

    @JvmOverloads
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    @JvmOverloads
    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        // ...
    }

    var topBarBinding = LayoutTopbarBinding.inflate(LayoutInflater.from(context), this, true)

    companion object {
        var topBarBackgroundColor = MutableLiveData<Int>(R.color.dark_navy)
    }

    fun setupWithOnboarding(
        onMenuButtonClick: () -> Unit,
        onRightButtonClick: () -> Unit
    ) {
        setup(
            pageType = PAGE_TYPE_ONBOARDING,
            onMenuButtonClick = onMenuButtonClick,
            onRightButtonClick = onRightButtonClick
        )
    }

    fun setupWithLogin() {
        setup(
            pageType = PAGE_TYPE_LOGIN,
            onMenuButtonClick = null,
            onRightButtonClick = null
        )
    }

    fun setup(
        pageType: String,
        onMenuButtonClick: (() -> Unit)? = null,
        onRightButtonClick: (() -> Unit)? = null,
    ) {
        topBarBinding.apply {

            if (pageType == PAGE_TYPE_ONBOARDING) {
                groupOnboarding.visibility = View.VISIBLE
                groupLogin.visibility = View.GONE

                dropdownMenu.setOnSingleClickListener {
                    onMenuButtonClick?.invoke()
                }

                btnSignUp.setOnSingleClickListener {
                    onRightButtonClick?.invoke()
                }

            } else {
                groupOnboarding.visibility = View.GONE
                groupLogin.visibility = View.VISIBLE
            }

        }
    }

}