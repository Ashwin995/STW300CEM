package com.example.workmate.api.users

import com.example.workmate.entity.User
import com.example.workmate.response.ImageResponse
import com.example.workmate.response.UserResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface IUserAPI {

    @POST("users")
    suspend fun register(
        @Body user: User
    ): Response<UserResponse>

    @PUT("users")
    suspend fun updateUser(
        @Header("Authorization") token: String,
        @Body user: User
    ): Response<UserResponse>

    @DELETE("/users/{uid}")
    suspend fun deleteUser(
        @Header("Authorization") token: String,
        @Path("id") uid: String,
    ): Response<UserResponse>

    @Multipart
    @POST("users/uploads/{id}")
    suspend fun updateImage(
        @Path("id") id: String,
        @Part file: MultipartBody.Part
    ): Response<ImageResponse>

    @GET("auth")
    suspend fun profile(
        @Header("Authorization") token: String
    ): Response<UserResponse>

    @FormUrlEncoded
    @POST("auth")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Response<UserResponse>


    @FormUrlEncoded
    @POST("users/update_password")
    suspend fun changePassword(
        @Header("Authorization") token: String,
        @Field("oldPassword") oldPassword: String,
        @Field("newPassword") newPassword: String
    ): Response<UserResponse>

}