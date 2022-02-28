package com.example.workmate.repository

import com.example.workmate.api.APIRequest
import com.example.workmate.api.users.IUserAPI
import com.example.workmate.api.ServiceBuilder
import com.example.workmate.entity.User
import com.example.workmate.response.ImageResponse
import com.example.workmate.response.UserResponse
import okhttp3.MultipartBody

class UserRepository : APIRequest() {

    private val userAPI = ServiceBuilder.buildServices(IUserAPI::class.java);

    suspend fun register(user: User): UserResponse {
        return apiRequest {
            userAPI.register(user)
        }
    }

    //images update
    suspend fun updateImage(id: String, body: MultipartBody.Part): ImageResponse {
        return apiRequest {
            userAPI.updateImage(id, body)
        }
    }

    suspend fun login(email: String, password: String): UserResponse {
        return apiRequest {
            userAPI.login(email, password)
        }
    }

    suspend fun update(user: User): UserResponse {
        return apiRequest {
            userAPI.updateUser(
                ServiceBuilder.token!!,
                user
            )
        }
    }

    suspend fun deleteProfile(uid: String): UserResponse {
        return apiRequest {
            userAPI.deleteUser(
                ServiceBuilder.token!!,
                uid
            )
        }
    }


    suspend fun profile(): UserResponse {
        return apiRequest {
            userAPI.profile(ServiceBuilder.token!!)
        }
    }

    suspend fun changePassword(oldPassword: String, newPassword: String): UserResponse {
        return apiRequest {
            userAPI.changePassword(
                ServiceBuilder.token!!,
                oldPassword,
                newPassword
            )
        }
    }
}