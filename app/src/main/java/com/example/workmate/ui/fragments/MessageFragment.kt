package com.example.workmate.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.workmate.R
import com.example.workmate.databinding.FragmentDescriptionBinding
import com.example.workmate.databinding.FragmentMessageBinding

class MessageFragment : Fragment(R.layout.fragment_message) {

    private var _binding: FragmentMessageBinding? = null
    private val binding get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMessageBinding.inflate(inflater, container, false);
        val view = binding?.root
        binding?.apply {
            msg.text =
                "Shoppers are also interested in a company’s mission. They’ll use the About Us page to determine if they share core values with the business and to decide if they want to shop with the business or not. \n" +
                        "\n" +
                        "In contrast to a landing page, your About Us page is the ideal place to accommodate a number of objectives:"
        }

        return view
    }

}