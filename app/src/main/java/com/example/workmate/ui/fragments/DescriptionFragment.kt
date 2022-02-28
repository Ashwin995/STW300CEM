package com.example.workmate.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.workmate.R
import com.example.workmate.databinding.FragmentAppliedJobsBinding
import com.example.workmate.databinding.FragmentDescriptionBinding

class DescriptionFragment(
    private val opt: String
) : Fragment() {

    private var _binding: FragmentDescriptionBinding? = null
    private val binding get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentDescriptionBinding.inflate(inflater, container, false);
        val view = binding?.root
        binding?.apply {
            desc.text = opt
        }

        return view

    }

}