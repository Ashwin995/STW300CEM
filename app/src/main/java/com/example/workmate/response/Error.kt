package com.example.workmate.response

data class Error(
    val value: String? = null,
    val msg: String? = null,
    val param: String? = null,
    val location: String? = null

)