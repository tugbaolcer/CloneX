package com.tugbaolcer.clonex.base

import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.IdlingResource
import com.tugbaolcer.clonex.server.MockWebServerRule
import com.tugbaolcer.clonex.utils.OkHttpIdlingResource
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import okhttp3.OkHttpClient
import org.junit.After
import org.junit.Before
import org.junit.Rule
import javax.inject.Inject

@HiltAndroidTest
abstract class BaseUiTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val mockWebServerRule = MockWebServerRule()

    @Inject
    lateinit var okHttpClient: OkHttpClient

    private var resource: IdlingResource? = null

    @Before
    fun setup() {
        hiltRule.inject()
        resource = OkHttpIdlingResource("okhttp", okHttpClient)
        IdlingRegistry.getInstance().register(resource)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(resource)
    }
}
