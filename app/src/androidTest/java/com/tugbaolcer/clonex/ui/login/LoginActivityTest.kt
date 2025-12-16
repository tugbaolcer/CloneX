package com.tugbaolcer.clonex.ui.login

import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.tugbaolcer.clonex.base.BaseUiTest
import com.tugbaolcer.clonex.robots.loginRobot
import com.tugbaolcer.clonex.server.loginSuccess
import com.tugbaolcer.clonex.server.requestTokenSuccess
import com.tugbaolcer.clonex.server.sessionSuccess
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Test
import org.junit.runner.RunWith

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class LoginActivityTest : BaseUiTest() {

    @Test
    fun login_success_navigates_to_main() {

        mockWebServerRule.server.apply {
            enqueue(requestTokenSuccess())
            enqueue(loginSuccess())
            enqueue(sessionSuccess())
        }

        ActivityScenario.launch(LoginActivity::class.java)

        loginRobot {
            waitForLoginScreen()
            login(
                username = "test_user",
                password = "123456"
            )
        }

//        intended(hasComponent(MainActivity::class.java.name))
    }
}
