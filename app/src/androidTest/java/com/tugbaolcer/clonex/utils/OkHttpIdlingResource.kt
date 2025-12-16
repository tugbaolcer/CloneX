package com.tugbaolcer.clonex.utils

import androidx.test.espresso.IdlingResource
import okhttp3.OkHttpClient

class OkHttpIdlingResource(
    private val name: String,
    private val client: OkHttpClient
) : IdlingResource {
    @Volatile private var callback: IdlingResource.ResourceCallback? = null

    init {
        client.dispatcher.idleCallback = Runnable {
            callback?.onTransitionToIdle()
        }
    }

    override fun getName(): String = name

    override fun isIdleNow(): Boolean {
        val idle = client.dispatcher.runningCallsCount() == 0
        if (idle) {
            callback?.onTransitionToIdle()
        }
        return idle
    }

    override fun registerIdleTransitionCallback(callback: IdlingResource.ResourceCallback?) {
        this.callback = callback
    }
}