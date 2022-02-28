package com.example.workmate.repository

import com.example.workmate.api.APIRequest
import com.example.workmate.api.ServiceBuilder
import com.example.workmate.api.jobs.IJobAPI
import com.example.workmate.entity.Job
import com.example.workmate.response.JobResponse

class JobRepository : APIRequest() {

    private val jobAPI = ServiceBuilder.buildServices(IJobAPI::class.java)

    suspend fun getAllJobs(): JobResponse {
        return apiRequest {
            jobAPI.getAllJobs(ServiceBuilder.token!!)
        }
    }

    suspend fun getJobPerUser(): JobResponse {
        return apiRequest {
            jobAPI.getJobPerUser(ServiceBuilder.token!!)
        }
    }

    suspend fun getAllJobsPerEmployeeApplied(): JobResponse {
        return apiRequest {
            jobAPI.getAllJobsPerEmployeeApplied(ServiceBuilder.token!!)
        }
    }

    suspend fun addJob(job: Job): JobResponse {
        return apiRequest {
            jobAPI.addJob(ServiceBuilder.token!!, job)
        }
    }

    suspend fun updateJob(job: Job, id: String): JobResponse {
        return apiRequest {
            jobAPI.updateJob(ServiceBuilder.token!!, id, job)
        }
    }

    suspend fun updateJobToApply(jid: String): JobResponse {
        return apiRequest {
            jobAPI.updateJobToApply(ServiceBuilder.token!!, jid)
        }
    }


    suspend fun deleteJob(jobId: String): JobResponse {
        return apiRequest {
            jobAPI.deleteJob(ServiceBuilder.token!!, jobId)
        }
    }

}