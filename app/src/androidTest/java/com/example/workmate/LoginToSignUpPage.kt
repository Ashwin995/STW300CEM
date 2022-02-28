package com.example.workmate

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.example.workmate.ui.LoginActivity
import com.example.workmate.ui.RegisterActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
@LargeTest
class LoginToSignUpPage {

    @get:Rule
    val activityRule = ActivityScenarioRule(LoginActivity::class.java)

    @Test
    fun toToRegister() {

        Espresso.onView(ViewMatchers.withId(R.id.tvgotRegister)).perform(ViewActions.click())
        Thread.sleep(5000)

    }
}