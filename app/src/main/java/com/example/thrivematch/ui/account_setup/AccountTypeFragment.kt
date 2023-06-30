package com.example.thrivematch.ui.account_setup

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.findNavController
import com.example.thrivematch.R
import com.example.thrivematch.databinding.FragmentAccountTypeBinding

class AccountTypeFragment : Fragment(R.layout.fragment_account_type) {
    private lateinit var binding: FragmentAccountTypeBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding=FragmentAccountTypeBinding.bind(view)

        //Progress Bar
        binding.progressBar.progress= 25

        //Investor Choice
        binding.cvInvestorCard.setOnClickListener{
            findNavController().navigate(R.id.action_accountTypeFragment_to_investorSetup1Fragment)
        }
        binding.imgBtnToInvestorAccount.setOnClickListener{
            findNavController().navigate(R.id.action_accountTypeFragment_to_investorSetup1Fragment)
        }

        // Business Choice
        binding.cvBusinessCard.setOnClickListener{
            findNavController().navigate(R.id.action_accountTypeFragment_to_businessSetup1Fragment)
        }
        binding.imgBtnToBusinessAccount.setOnClickListener{
            findNavController().navigate(R.id.action_accountTypeFragment_to_businessSetup1Fragment)
        }
    }

}