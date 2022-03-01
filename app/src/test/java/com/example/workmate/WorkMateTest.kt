package com.example.workmate

import com.example.workmate.api.ServiceBuilder
import com.example.workmate.entity.User
import com.example.workmate.repository.UserRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import kotlin.math.exp


class WorkMateTest {

    @Test
    fun login() = runBlocking {

        val username = "Hari@gmail.com"
        val password = "Hari234"
        val userRepo = UserRepository()
        val response = userRepo.login(username, password)
        val expectedResult = true
        val actualResult = response.success
        Assert.assertEquals(expectedResult, actualResult)
    }


    @Test
    fun loginFail() = runBlocking {
//Test fails on providing false username or password
        val username = "Ashwin1@gmail.com"
        val password = "Ash123"
        val userRepo = UserRepository()
        val response = userRepo.login(username, password)
        val expectedResult = true
        val actualResult = response.success
        Assert.assertEquals(expectedResult, actualResult)
    }

    @Test
    fun register() = runBlocking {

        val fullname = "Sita Lama"
        val phone = "9843910041"
        val email = "Sita@gmail.com"
        val password = "Ash123"
        val user = User(name = fullname, email = email, phone = phone, password = password)
        val userRepo = UserRepository()
        val response = userRepo.register(user)
        val expectedResult = true
        val actualResult = response.success
        Assert.assertEquals(expectedResult, actualResult)
    }

    @Test
    fun viewProfile() = runBlocking {

        //Test fails because token is required to see profile, only valid user
        //can see profile
        val username = "Ash7@gmail.com"
        val password = "Ash123"
        val userRepo = UserRepository()
        val data = userRepo.login(username, password)
        ServiceBuilder.token = data.token // token not provided
        val response = userRepo.profile()
        val expectedResult = true
        val actualResult = response.success
        Assert.assertEquals(expectedResult, actualResult)
    }
    
}