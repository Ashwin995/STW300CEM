package com.example.workmate.api.jobs

import com.example.workmate.entity.Job
import com.example.workmate.response.JobResponse
import retrofit2.Response
import retrofit2.http.*

interface IJobAPI {

    @GET("job")
    suspend fun getAllJobs(
        @Header("Authorization") token: String
    ): Response<JobResponse>

    @GET("job/per")
    suspend fun getAllJobsPerEmployeeApplied(
        @Header("Authorization") token: String
    ): Response<JobResponse>

    @GET("job/per_user")
    suspend fun getJobPerUser(
        @Header("Authorization") token: String
    ): Response<JobResponse>

    @POST("job")
    suspend fun addJob(
        @Header("Authorization") token: String,
        @Body job: Job
    ): Response<JobResponse>

    @PUT("job/update/{id}")
    suspend fun updateJob(
        @Header("Authorization") token: String,
        @Path("id") id: String,
        @Body job: Job
    ): Response<JobResponse>

    @PUT("job/apply/{jid}")
    suspend fun updateJobToApply(
        @Header("Authorization") token: String,
        @Path("jid") jid: String
    ): Response<JobResponse>


    @DELETE("job/delete/{id}")
    suspend fun deleteJob(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ): Response<JobResponse>

}