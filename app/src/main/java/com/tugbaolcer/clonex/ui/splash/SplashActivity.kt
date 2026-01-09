package com.tugbaolcer.clonex.ui.splash

import android.animation.Animator
import android.content.Intent
import android.media.MediaPlayer
import androidx.lifecycle.lifecycleScope
import com.tugbaolcer.clonex.R
import com.tugbaolcer.clonex.base.CloneXBaseActivity
import com.tugbaolcer.clonex.base.ScreenMode
import com.tugbaolcer.clonex.databinding.ActivitySplahBinding
import com.tugbaolcer.clonex.ui.main.MainActivity
import com.tugbaolcer.clonex.ui.onboarding.OnboardingActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class SplashActivity : CloneXBaseActivity<SplashViewModel, ActivitySplahBinding>() {

    override val layoutResourceId: Int
        get() = R.layout.activity_splah

    override val screenMode = ScreenMode.FULLSCREEN

    private lateinit var mediaPlayer: MediaPlayer

    override fun init() {
        setupMediaPlayer()
        setupLottieAnimation()
    }

    private fun setupMediaPlayer() {
        mediaPlayer = MediaPlayer.create(this, R.raw.effect)
    }

    private fun setupLottieAnimation() {
        binding.lottieLayerName.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {
                mediaPlayer.start()
            }

            override fun onAnimationEnd(animation: Animator) {
                lifecycleScope.launch {
                    if (viewModel.hasSession()) {
                        startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                    } else {
                        startActivity(Intent(this@SplashActivity, OnboardingActivity::class.java))
                    }
                }
            }

            override fun onAnimationCancel(animation: Animator) {}

            override fun onAnimationRepeat(animation: Animator) {}
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()
    }

    override fun initTopBar(title: Int?) {}

    override fun retrieveNewData() {}

    override fun bindingData() {}

    override val viewModelClass: Class<SplashViewModel>
        get() = SplashViewModel::class.java
}
