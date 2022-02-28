package com.example.workmate.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.workmate.R
import com.example.workmate.adapter.AppliedJobsAdapter
import com.example.workmate.adapter.MyJobAdapter
import com.example.workmate.databinding.FragmentAppliedJobsBinding
import com.example.workmate.entity.Job
import com.example.workmate.repository.JobRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException


class AppliedJobsFragment : Fragment() {

    private var _binding: FragmentAppliedJobsBinding? = null
    private val binding get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAppliedJobsBinding.inflate(layoutInflater, container, false)

        loadRecycler()
        return binding?.root
    }

    private fun loadRecycler() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val jobRepo = JobRepository()
                val response = jobRepo.getAllJobsPerEmployeeApplied()
                if (response.success == true) {
                    withContext(Dispatchers.Main) {
                        val arrListOfJob = response.data as ArrayList<Job>
                        loadView(arrListOfJob)
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
        val adapter = AppliedJobsAdapter(requireContext(), arr)

        binding?.apply {
            recyclerAppliedJob.adapter = adapter
            recyclerAppliedJob.layoutManager = LinearLayoutManager(requireContext())
        }
    }

}