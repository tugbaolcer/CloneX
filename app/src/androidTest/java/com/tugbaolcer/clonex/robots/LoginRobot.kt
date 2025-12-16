package com.tugbaolcer.clonex.robots

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import com.tugbaolcer.clonex.locators.LoginLocators

class LoginRobot {

    fun enterUsername(username: String) {
        onView(LoginLocators.usernameInput())
            .perform(typeText(username), closeSoftKeyboard())
    }

    fun enterPassword(password: String) {
        onView(LoginLocators.passwordInput())
            .perform(typeText(password), closeSoftKeyboard())
    }

    fun clickLogin() {
        onView(LoginLocators.loginButton)
            .perform(click())
    }

    fun login(username: String, password: String) {
        enterUsername(username)
        enterPassword(password)
        clickLogin()
    }

    fun waitForLoginScreen() {
        onView(LoginLocators.usernameInput()).check(matches(isDisplayed()))
        onView(LoginLocators.passwordInput()).check(matches(isDisplayed()))
    }
}

// DSL (Domain Specific Language) yapısı için
fun loginRobot(func: LoginRobot.() -> Unit) = LoginRobot().apply { func() }