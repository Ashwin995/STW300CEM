package com.example.workmate

import androidx.test.espresso.Espresso.closeSoftKeyboard
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.example.workmate.ui.LoginActivity
import kotlinx.coroutines.delay
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class LoginTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(LoginActivity::class.java)

    @Test
    fun login() {
        onView(withId(R.id.etEmail)).perform(clearText())
        onView(withId(R.id.etEmail)).perform(typeText("arjun@gmail.com"))
        closeSoftKeyboard()
        Thread.sleep(5000)

        onView(withId(R.id.etPassword)).perform(clearText())
        onView(withId(R.id.etPassword)).perform(typeText("arjun777"))
        closeSoftKeyboard()
        Thread.sleep(5000)
        closeSoftKeyboard()

        Thread.sleep(5000)
        onView(withId(R.id.btnLogin)).perform(click())
        Thread.sleep(5000)

    }
}