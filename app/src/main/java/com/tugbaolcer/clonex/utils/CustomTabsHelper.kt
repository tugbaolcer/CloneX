package com.tugbaolcer.clonex.utils

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import com.tugbaolcer.clonex.R
import androidx.core.net.toUri

object CustomTabsHelper {

    fun openUrl(context: Context, url: String) {
        try {
            val intent = buildCustomTabsIntent(context)
            intent.launchUrl(context, url.toUri())
        } catch (_: ActivityNotFoundException) {
            context.startActivity(Intent(Intent.ACTION_VIEW, url.toUri()))
        }
    }

    /**
     * Build a polished, themed, animated CustomTabsIntent.
     */
    private fun buildCustomTabsIntent(context: Context): CustomTabsIntent {
        val builder = CustomTabsIntent.Builder()

        builder.setShowTitle(true)
        builder.setShareState(CustomTabsIntent.SHARE_STATE_OFF)

        builder.setToolbarColor(ContextCompat.getColor(context, R.color.dark_navy))

        // Animations
        builder.setStartAnimations(
            context,
            android.R.anim.slide_in_left,
            android.R.anim.slide_out_right
        )
        builder.setExitAnimations(
            context,
            android.R.anim.slide_in_left,
            android.R.anim.slide_out_right
        )

        return builder.build()
    }
}
