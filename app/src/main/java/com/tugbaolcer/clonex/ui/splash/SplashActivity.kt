package com.tugbaolcer.clonex.ui.splash

import android.animation.Animator
import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.lifecycleScope
import com.tugbaolcer.clonex.R
import com.tugbaolcer.clonex.base.CloneXBaseActivity
import com.tugbaolcer.clonex.databinding.ActivitySplahBinding
import com.tugbaolcer.clonex.ui.MainActivity
import com.tugbaolcer.clonex.ui.onboarding.OnboardingActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class SplashActivity : CloneXBaseActivity<SplashViewModel, ActivitySplahBinding>() {

    override val layoutResourceId: Int
        get() = R.layout.activity_splah

    private lateinit var mediaPlayer: MediaPlayer

    override fun init() {
        enableEdgeToEdge()
        hideNavigationBar()
        setupMediaPlayer()
        setupLottieAnimation()
    }

    private fun hideNavigationBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            // API 30 ve üzeri için WindowInsetsController kullanımı
            window.setDecorFitsSystemWindows(false)
            window.insetsController?.let { controller ->
                controller.hide(WindowInsets.Type.systemBars()) // Status ve navigation bar'ı gizler
                controller.systemBarsBehavior =
                    WindowInsetsController.BEHAVIOR_DEFAULT // Uyarı olmadan kaydırma hareketine izin verir
            }
        } else {
            // API 30'dan düşük seviyeler için eski yöntem
            @Suppress("DEPRECATION")
            window.decorView.systemUiVisibility = (
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            or View.SYSTEM_UI_FLAG_FULLSCREEN
                            or View.SYSTEM_UI_FLAG_IMMERSIVE
                    )
        }
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
