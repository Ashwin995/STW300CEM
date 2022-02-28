package com.example.workmate.ui

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.workmate.adapter.JobsForYouAdapter
import com.example.workmate.adapter.PopularJobsAdapter
import com.example.workmate.databinding.ActivityDboardBinding
import com.example.workmate.entity.Job
import kotlin.random.Random

class DboardActivity : AppCompatActivity() {


    private lateinit var binding: ActivityDboardBinding

    private var listOfJobs = arrayListOf<Job>()
    private var arrayCategory = arrayListOf<String>(
        "Popular",
        "Most Searched",
        "Recent"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityDboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        addJobs()
//        addJobsForYou()
        val popularCategoryAdapter = PopularJobsAdapter(listOfJobs, this)

        val jobsForYouAdapter = JobsForYouAdapter(listOfJobs, this)


        val spinnerAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayCategory);
        binding.apply {


            rvPopularJobs.layoutManager =
                LinearLayoutManager(this@DboardActivity, LinearLayoutManager.HORIZONTAL, false)

            rvJustForYou.layoutManager =
                LinearLayoutManager(this@DboardActivity)

            rvPopularJobs.adapter = popularCategoryAdapter
            rvJustForYou.adapter = jobsForYouAdapter
            spinnerJob.adapter = spinnerAdapter


        }
    }

    private fun generateRandomColor(): Int {
        val rnd = Random.Default
        return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
    }

    private fun addJobs() {
        val marketing = Job(job_title = "Marketing")
        listOfJobs.add(marketing)

        val accountant = Job(job_title = "Accountant")
        listOfJobs.add(accountant)

        val kotlin = Job(job_title = "Kotlin Developer")
        listOfJobs.add(kotlin)

        val java = Job(job_title = "Java Developer")
        listOfJobs.add(java)

        val qa = Job(job_title = "Quality Assurance")
        listOfJobs.add(qa)

        val contentWriter = Job(job_title = "Content Writer")
        listOfJobs.add(contentWriter)
    }


    private fun addJobsForYou() {
        val marketing = Job(
            job_title = "Marketing"
        )
        listOfJobs.add(marketing)

        val accountant = Job(
            job_title = "Accountant"
        )
        listOfJobs.add(accountant)

        val kotlin = Job(
            job_title = "Kotlin Developer"
        )
        listOfJobs.add(kotlin)

        val java = Job(
            job_title = "Java Developer"
        )
        listOfJobs.add(java)

        val qa = Job(
            job_title = "Quality Assurance"

        )
        listOfJobs.add(qa)

        val contentWriter = Job(
            job_title = "Content Writer"
        )
        listOfJobs.add(contentWriter)
    }
}