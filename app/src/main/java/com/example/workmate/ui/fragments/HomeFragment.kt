package com.example.workmate.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.workmate.adapter.AppliedJobsAdapter
import com.example.workmate.adapter.JobsForYouAdapter
import com.example.workmate.adapter.PopularJobsAdapter
import com.example.workmate.databinding.FragmentHomeBinding
import com.example.workmate.entity.Job
import com.example.workmate.repository.JobRepository
import com.example.workmate.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding

    private var listOfJobs = arrayListOf<Job>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding?.root
        loadProfile()
        loadRecycler()
        return view
    }

    private fun loadRecycler() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val jobRepo = JobRepository()
                val response = jobRepo.getAllJobs()
                if (response.success == true) {
                    withContext(Dispatchers.Main) {
                        val arrListOfJob = response.data as ArrayList<Job>

                        loadView(arrListOfJob)
//                        Toast.makeText(requireContext(), "$arrListOfJob", Toast.LENGTH_LONG).show()

                    }
                }

            } catch (ex: IOException) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        requireContext(),
                        "${ex.localizedMessage}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

    }

    private fun loadView(arr: ArrayList<Job>) {
        val popularJobsAdapter = PopularJobsAdapter(arr, requireContext())
        val justForYouAdapter = JobsForYouAdapter(arr, requireContext())

        binding?.apply {
            //Popular Jobs
            rvPopularJobs.adapter = popularJobsAdapter
            rvPopularJobs.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            //Jobs for you
            rvJustForYou.adapter = justForYouAdapter
            rvJustForYou.layoutManager = LinearLayoutManager(requireContext())

        }
    }

    private fun loadProfile() {

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val userRepo = UserRepository()
                val response = userRepo.profile()
                if (response.success == true) {
                    withContext(Dispatchers.Main) {
                        binding?.apply {
                            tvProfileName.setText(response.data?.name);
                        }

                    }
                }
            } catch (ex: IOException) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(requireContext(), ex.localizedMessage, Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        listOfJobs.clear()
    }

}