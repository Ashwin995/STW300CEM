package com.example.workmate.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.workmate.R
import com.example.workmate.databinding.ActivityJobBinding
import com.example.workmate.ui.fragments.AppliedJobsFragment
import com.example.workmate.ui.fragments.MyJobsFragment

class JobActivity : AppCompatActivity() {
    private lateinit var binding: ActivityJobBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJobBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        val jobAddedFragment = MyJobsFragment()
        val appliedJobFragent = AppliedJobsFragment()

        setCurrentFragment(jobAddedFragment)
        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.miPosted -> setCurrentFragment(jobAddedFragment)
                R.id.miAppliedJob -> setCurrentFragment(appliedJobFragent)
            }
            true
        }

        binding.btnAddJob.setOnClickListener {
            startActivity(Intent(this, AddJobActivity::class.java))
        }

    }

    private fun setCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment, fragment)
            commit()
        }
}
