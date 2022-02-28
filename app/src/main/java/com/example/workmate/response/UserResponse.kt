package com.example.workmate.response

import com.example.workmate.entity.User
import java.io.Serializable

data class UserResponse(
    val msg: String? = null,
    val data: User? = null,
    val success: Boolean? = null,
    val errors: Error? = null,
    val token: String? = null
)
