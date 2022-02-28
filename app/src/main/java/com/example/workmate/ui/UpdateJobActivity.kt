package com.example.workmate.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.workmate.R
import com.example.workmate.api.ServiceBuilder
import com.example.workmate.databinding.ActivityUpdateJobBinding
import com.example.workmate.entity.Job
import com.example.workmate.repository.JobRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException

class UpdateJobActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateJobBinding
    var id = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateJobBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        val intent = intent

        if (intent.extras != null) {
            val job = intent.getParcelableExtra<Job>("job")
            binding.etCompany.setText(job?.company.toString())
            binding.etJobLevel.setText(job?.job_title)
            binding.etCompanyDetails.setText(job?.company_details)
            binding.etExperience.setText(job?.experience_required)
            binding.etJobTime.setText(job?.job_time.toString())
            binding.etJobTitle.setText(job?.job_title.toString())
            id = job?._id.toString()

//            Toast.makeText(this, "${ServiceBuilder.jid}", Toast.LENGTH_SHORT).show()
        }
        binding.btnUpdateJob.setOnClickListener {
            updateJob()
        }

    }

    private fun updateJob() {
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
            job_time = jobTime
        )

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val jobRepo = JobRepository()
                val response = jobRepo.updateJob(job, ServiceBuilder.jid.toString())
                if (response.success == true) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            this@UpdateJobActivity,
                            "Job Updated",
                            Toast.LENGTH_SHORT
                        ).show()
                        startActivity(Intent(this@UpdateJobActivity, JobActivity::class.java))

                    }
                }

            } catch (ex: IOException) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        this@UpdateJobActivity,
                        "${ex.localizedMessage}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

        }
    }
}