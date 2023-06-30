package com.example.thrivematch.ui.account_setup

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.thrivematch.R
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.thrivematch.databinding.FragmentInvestorSetup1Binding
import com.example.thrivematch.util.CommonSharedPreferences
import com.example.thrivematch.util.Constants

class InvestorSetup1Fragment : Fragment(R.layout.fragment_investor_setup1) {

    private lateinit var binding: FragmentInvestorSetup1Binding
//    private lateinit var viewModel:AccountSetupViewModel
//    private val sharedViewModelt: SharedViewModelTeToStoreImageData by activityViewModels()

    private lateinit var commonSharedPreferences: CommonSharedPreferences

    override fun onResume() {
        super.onResume()
        // Drop Down Menu
        val investorTypes = listOf("Individual Investor", "Investing Company")
        val adapter= ArrayAdapter(requireContext(), R.layout.dropdown_item, investorTypes)
        binding.autocompleteInvestorType.setAdapter(adapter)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding= FragmentInvestorSetup1Binding.bind(view)
//        viewModel = ViewModelProvider(this)[AccountSetupViewModel::class.java]
        commonSharedPreferences= CommonSharedPreferences(requireContext())

        //Progress Bar
        binding.progressBarInvestor1.progress= 50

        //Navigation Buttons
        binding.btnBackInvestor1.setOnClickListener{
            findNavController().navigate(R.id.action_investorSetup1Fragment_to_accountTypeFragment)
        }
        binding.btnNextInvestor1.setOnClickListener {
            saveFormData()
            findNavController().navigate(R.id.action_investorSetup1Fragment_to_investorSetup2Fragment)
        }

        // Disabling Next Button until data if filled
        updateNextButtonState()
        binding.autocompleteInvestorType.addTextChangedListener{updateNextButtonState()}
        binding.etInvestorName.addTextChangedListener{updateNextButtonState()}
        binding.etInvestorDescription.addTextChangedListener{updateNextButtonState()}


        // Edit Texts Data Persistence
        binding.autocompleteInvestorType.setText(if (commonSharedPreferences.getStringData(Constants.INVESTORTYPE) == "") "" else commonSharedPreferences.getStringData(Constants.INVESTORTYPE))
        binding.etInvestorName.setText(if (commonSharedPreferences.getStringData(Constants.INVESTORNAME) == "") "" else commonSharedPreferences.getStringData(Constants.INVESTORNAME))
        binding.etInvestorDescription.setText(if (commonSharedPreferences.getStringData(Constants.INVESTORDESCRIPTION) == "") "" else commonSharedPreferences.getStringData(Constants.INVESTORDESCRIPTION))
    }

    private fun updateNextButtonState() {
        val isDataFilled = binding.autocompleteInvestorType.text.isNotBlank() &&
                binding.etInvestorName.text.toString().isNotBlank() &&
                binding.etInvestorDescription.text.toString().isNotBlank()

        binding.btnNextInvestor1.isEnabled = isDataFilled
    }

    private fun saveFormData() {
        commonSharedPreferences.saveStringData(Constants.INVESTORTYPE, binding.autocompleteInvestorType.text.toString())
        commonSharedPreferences.saveStringData(Constants.INVESTORNAME, binding.etInvestorName.text.toString())
        commonSharedPreferences.saveStringData(Constants.INVESTORDESCRIPTION, binding.etInvestorDescription.text.toString())
        commonSharedPreferences.saveStringData(Constants.INVESTORDESCRIPTION, binding.etInvestorDescription.text.toString())

    }

}