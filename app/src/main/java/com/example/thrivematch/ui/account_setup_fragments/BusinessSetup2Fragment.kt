package com.example.thrivematch.ui.account_setup_fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.findNavController
import com.example.thrivematch.R
import com.example.thrivematch.databinding.FragmentBusinessSetup2Binding


class BusinessSetup2Fragment : Fragment(R.layout.fragment_business_setup2) {
    private lateinit var binding:FragmentBusinessSetup2Binding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentBusinessSetup2Binding.bind(view)
        //Progress Bar
        binding.progressBarBusiness2.progress= 75

        binding.btnNextBusiness2.setOnClickListener {
            findNavController().navigate(R.id.action_businessSetup2Fragment_to_businessSetup3Fragment)
        }

        binding.btnBackBusiness2.setOnClickListener {
            findNavController().navigate(R.id.action_businessSetup2Fragment_to_businessSetup1Fragment)
        }
    }
}