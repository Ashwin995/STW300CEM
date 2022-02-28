package com.example.workmate.response

import com.example.workmate.entity.Job
import java.util.*
import kotlin.collections.ArrayList

data class JobResponse(
    val success: Boolean? = null,
    val data: ArrayList<Job>? = null
)