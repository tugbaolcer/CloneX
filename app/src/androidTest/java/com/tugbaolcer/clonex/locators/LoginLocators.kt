package com.tugbaolcer.clonex.locators

import android.view.View
import androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.tugbaolcer.clonex.R
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf

object LoginLocators {

    fun usernameInput(): Matcher<View> =
        allOf(
            withId(R.id.et_custom),
            isDescendantOfA(withId(R.id.tv_username))
        )

    fun passwordInput(): Matcher<View> =
        allOf(
            withId(R.id.et_custom),
            isDescendantOfA(withId(R.id.tv_password))
        )

    val loginButton: Matcher<View> =
        withId(R.id.btn_login)
}
