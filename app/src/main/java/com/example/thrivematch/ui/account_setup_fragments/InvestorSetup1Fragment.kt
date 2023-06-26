package com.example.thrivematch.ui.account_setup_fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.findNavController
import com.example.thrivematch.R
import com.example.thrivematch.databinding.FragmentInvestorSetup1Binding

class InvestorSetup1Fragment : Fragment(R.layout.fragment_investor_setup1) {

    private lateinit var binding: FragmentInvestorSetup1Binding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding= FragmentInvestorSetup1Binding.bind(view)
        //Progress Bar
        binding.progressBarInvestor1.progress= 50

        binding.btnBackInvestor1.setOnClickListener{
            findNavController().navigate(R.id.action_investorSetup1Fragment_to_accountTypeFragment)
        }
        binding.btnNextInvestor1.setOnClickListener {
            findNavController().navigate(R.id.action_investorSetup1Fragment_to_investorSetup2Fragment)
        }

    }

}