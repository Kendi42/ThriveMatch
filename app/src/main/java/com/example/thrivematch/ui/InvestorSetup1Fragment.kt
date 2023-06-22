package com.example.thrivematch.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.thrivematch.R
import com.example.thrivematch.databinding.FragmentInvestorSetup1Binding

class InvestorSetup1Fragment : Fragment(R.layout.fragment_investor_setup1) {

    private lateinit var binding: FragmentInvestorSetup1Binding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding= FragmentInvestorSetup1Binding.bind(view)

        binding.btnBack1.setOnClickListener{
            findNavController().navigate(R.id.action_investorSetup1Fragment_to_accountTypeFragment)
        }

    }

}