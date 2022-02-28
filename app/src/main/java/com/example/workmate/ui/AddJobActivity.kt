package com.example.workmate.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.workmate.api.ServiceBuilder
import com.example.workmate.databinding.ActivityAddJobBinding
import com.example.workmate.entity.Job
import com.example.workmate.repository.JobRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException

class AddJobActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddJobBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddJobBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
//        Toast.makeText(this, "${ServiceBuilder.LoggedInUser}", Toast.LENGTH_SHORT).show()

        binding.btnPublishJob.setOnClickListener {
            addJob()
        }
    }

    private fun addJob() {
        val companyName = binding.etCompany.text.toString()
        val jobTitle = binding.etJobTitle.text.toString()
        val experienceRequired = binding.etExperience.text.toString()
        val companyDetails = binding.etCompanyDetails.text.toString()
        val jobLevel = binding.etJobTitle.text.toString()
        val jobTime = binding.etJobTime.text.toString()
        val job = Job(
            job_title = jobTitle,
            company = companyName,
            experience_required = experienceRequired,
            company_details = companyDetails,
            job_level = jobLevel,
            job_time = jobTime,
            employer = ServiceBuilder.LoggedInUser
        )

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val jobRepo = JobRepository()
                val response = jobRepo.addJob(job)
                if (response.success == true) {
                    withContext(Main) {
                        Toast.makeText(
                            this@AddJobActivity,
                            "Job Added",
                            Toast.LENGTH_SHORT
                        ).show()
                        startActivity(Intent(this@AddJobActivity, JobActivity::class.java))

                    }
                }

            } catch (ex: IOException) {
                withContext(Main) {
                    Toast.makeText(
                        this@AddJobActivity,
                        "${ex.localizedMessage}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

        }
    }
}