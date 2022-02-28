package com.example.workmate.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.workmate.adapter.AppliedJobsAdapter
import com.example.workmate.adapter.MyJobAdapter
import com.example.workmate.api.ServiceBuilder
import com.example.workmate.databinding.FragmentMyJobsBinding
import com.example.workmate.entity.Job
import com.example.workmate.repository.JobRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException


class MyJobsFragment : Fragment() {

    private var _binding: FragmentMyJobsBinding? = null
    private val binding get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMyJobsBinding.inflate(layoutInflater, container, false);
        loadRecycler()
        return binding?.root
    }


    private fun loadRecycler() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val jobRepo = JobRepository()
                val response = jobRepo.getJobPerUser()
                if (response.success == true) {
                    withContext(Main) {
                        val arrListOfJob = response.data as ArrayList<Job>

                        loadView(arrListOfJob)
                    }
                }

            } catch (ex: IOException) {
                withContext(Main) {
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
        val adapter = MyJobAdapter(requireContext(), arr)

        binding?.apply {
            recyclerAddedJob.adapter = adapter
            recyclerAddedJob.layoutManager = LinearLayoutManager(requireContext())
        }
    }

}