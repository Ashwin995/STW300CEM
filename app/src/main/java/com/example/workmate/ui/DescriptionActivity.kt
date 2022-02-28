package com.example.workmate.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.workmate.R
import com.example.workmate.api.ServiceBuilder
import com.example.workmate.databinding.ActivityDescriptionBinding
import com.example.workmate.entity.Job
import com.example.workmate.repository.JobRepository
import com.example.workmate.ui.fragments.CompanyFragment
import com.example.workmate.ui.fragments.DescriptionFragment
import com.example.workmate.ui.fragments.MessageFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException

class DescriptionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDescriptionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityDescriptionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        val intent = intent
        var desc = ""
        var companyMessage = ""
        var employer = ""

        if (intent.extras !== null) {
            val job = intent.getParcelableExtra<Job>("job")
            if (job != null) {
                desc = job.experience_required.toString() +
                        "\n\n" + job.job_level + "\n\n" + job.job_time

                companyMessage = job.company_details.toString()
                employer = job.employer.toString()



                binding.tvCompanyName.text = job.company.toString()
                binding.tvJobName.text = job.job_title.toString()
//                jobId = job._id.toString()
                Log.d("Tag1", employer.toString())
                Log.d("Tag2", ServiceBuilder.LoggedInUser!!)

                if (employer == ServiceBuilder.LoggedInUser) {
                    binding.btnApplyJob.visibility = View.INVISIBLE
                }

//                Toast.makeText(this, "${ServiceBuilder.jfuid}", Toast.LENGTH_SHORT).show()

            }
        }

        val descriptionFragment = DescriptionFragment(desc)
        val messageFragment = MessageFragment()
        val companyFragment = CompanyFragment(companyMessage)

        binding.btnApplyJob.setOnClickListener {
            doApplyJob()
        }




        setCurrentFragment(descriptionFragment)
        binding.btnNavView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.miDescription -> setCurrentFragment(descriptionFragment)
                R.id.miCompany -> setCurrentFragment(companyFragment)
                R.id.miMessage -> setCurrentFragment(messageFragment)
            }
            true
        }
    }

    private fun doApplyJob() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val jobRepo = JobRepository()
                val response = jobRepo.updateJobToApply(ServiceBuilder.jfuid.toString())
                if (response.success == true) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            this@DescriptionActivity,
                            "Job Applied Successfully",
                            Toast.LENGTH_SHORT
                        ).show()
                        startActivity(Intent(this@DescriptionActivity, JobActivity::class.java))

                    }
                }

            } catch (ex: IOException) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        this@DescriptionActivity,
                        "${ex.localizedMessage}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

        }
    }

    private fun setCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flLayout, fragment)
            commit()
        }


}