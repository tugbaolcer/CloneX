package com.tugbaolcer.clonex.ui.onboarding

import android.content.Intent
import android.text.Html
import android.view.MenuInflater
import android.widget.PopupMenu
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.tugbaolcer.clonex.R
import com.tugbaolcer.clonex.base.CloneXBaseActivity
import com.tugbaolcer.clonex.databinding.ActivityOnboardingBinding
import com.tugbaolcer.clonex.ui.login.LoginActivity
import com.tugbaolcer.clonex.utils.OnboardingTopBarContract
import dagger.hilt.android.AndroidEntryPoint

/**
 * created view by Tugba OLCER
 * date: 1.1.25
 */

@AndroidEntryPoint
class OnboardingActivity : CloneXBaseActivity<OnboardingViewModel, ActivityOnboardingBinding>() {
    override val layoutResourceId: Int
        get() = R.layout.activity_onboarding

    override fun init() {
        binding.apply {
            val pagerAdapter = OnBoardingPagerAdapter(this@OnboardingActivity)
            viewPagerOnboarding.adapter = pagerAdapter
            viewPagerOnboarding.registerOnPageChangeCallback(viewPagerPageChangeListener)

            initSliderDots(0)
        }
    }

    override fun initTopBar(title: Int?) {
        binding.layoutTopbar.setupOnboarding(
            showMenu = true,
            showSignUp = true,
            contract = object : OnboardingTopBarContract {
                override fun onMenuClicked() {
                    openMenu()
                }

                override fun onSignUpClicked() {
                    navigateToSignUp()
                }
            }
        )
    }

    override fun retrieveNewData() {}

    override fun bindingData() {}

    inner class OnBoardingPagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {

        override fun getItemCount() = 4

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> TabOnBoardingFragment.newInstance(step_type = 0)

                1 -> TabOnBoardingFragment.newInstance(step_type = 1)

                2 -> TabOnBoardingFragment.newInstance(step_type = 2)

                3 -> TabOnBoardingFragment.newInstance(step_type = 3)

                else -> TabOnBoardingFragment.newInstance(step_type = 0)

            }
        }

    }

    private fun initSliderDots(currentPage: Int) {
        binding.sliderDots.post {
            val sliderDots = 4
            val dots: Array<TextView?> = arrayOfNulls(sliderDots)
            binding.sliderDots.removeAllViews()

            for (i in dots.indices) {
                dots[i] = TextView(this)
                dots[i]!!.text = Html.fromHtml("&#8226;")
                dots[i]!!.textSize = 36f
                dots[i]!!.setTextColor(ContextCompat.getColor(this, R.color.light_gray))
                binding.sliderDots.addView(dots[i])
            }
            if (dots.isNotEmpty())
                dots[currentPage]!!.setTextColor(
                    ContextCompat.getColor(
                        this,
                        R.color.white
                    )
                )
        }

    }

    private var viewPagerPageChangeListener = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            initSliderDots(position)
        }
    }


    private var loginLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) { }
        }

    private fun openMenu() {
        val popup =
            PopupMenu(this, binding.layoutTopbar.topBarBinding.onboardingLayout.dropdownMenu)
        val inflater: MenuInflater = popup.menuInflater
        inflater.inflate(R.menu.topbar_menu_view, popup.menu)
        popup.show()
    }

    private fun navigateToSignUp() {
        loginLauncher.launch(Intent(this@OnboardingActivity, LoginActivity::class.java))
    }


    override val viewModelClass: Class<OnboardingViewModel>
        get() = OnboardingViewModel::class.java
}