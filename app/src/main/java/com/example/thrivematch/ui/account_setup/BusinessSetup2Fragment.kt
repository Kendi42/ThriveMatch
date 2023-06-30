package com.example.thrivematch.ui.account_setup

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import com.example.thrivematch.R
import com.example.thrivematch.databinding.FragmentBusinessSetup2Binding
import com.example.thrivematch.util.CommonSharedPreferences
import com.example.thrivematch.util.Constants


class BusinessSetup2Fragment : Fragment(R.layout.fragment_business_setup2) {
    private lateinit var binding:FragmentBusinessSetup2Binding
    private lateinit var commonSharedPreferences: CommonSharedPreferences


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentBusinessSetup2Binding.bind(view)
        commonSharedPreferences= CommonSharedPreferences(requireContext())


        //Progress Bar
        binding.progressBarBusiness2.progress= 75

        // Navigation Buttons
        binding.btnNextBusiness2.setOnClickListener {
            saveFormData()

            findNavController().navigate(R.id.action_businessSetup2Fragment_to_businessSetup3Fragment)
        }

        binding.btnBackBusiness2.setOnClickListener {
            findNavController().navigate(R.id.action_businessSetup2Fragment_to_businessSetup1Fragment)
        }

        // Disabling Next Button until data if filled
        updateNextButtonState()
        binding.etPhoneNumber.addTextChangedListener{updateNextButtonState()}
        binding.etBusinessEmail.addTextChangedListener{updateNextButtonState()}
        binding.etBusinessAddress.addTextChangedListener{updateNextButtonState()}
        binding.etPoBox.addTextChangedListener{updateNextButtonState()}

        // Edit Texts Data Persistence
        binding.etPhoneNumber.setText(if (commonSharedPreferences.getStringData(Constants.BUSINESSPHONE) == "") "" else commonSharedPreferences.getStringData(Constants.BUSINESSPHONE))
        binding.etBusinessEmail.setText(if (commonSharedPreferences.getStringData(Constants.BUSINESSEMAIL) == "") "" else commonSharedPreferences.getStringData(Constants.BUSINESSEMAIL))
        binding.etBusinessAddress.setText(if (commonSharedPreferences.getStringData(Constants.BUSINESSADDRESS) == "") "" else commonSharedPreferences.getStringData(Constants.BUSINESSADDRESS))
        binding.etPoBox.setText(if (commonSharedPreferences.getStringData(Constants.BUSINESSPOBOX) == "") "" else commonSharedPreferences.getStringData(Constants.BUSINESSPOBOX))


    }

    private fun updateNextButtonState() {
        val isDataFilled = binding.etPhoneNumber.text.toString().isNotBlank() &&
                binding.etBusinessEmail.text.toString().isNotBlank() &&
                binding.etBusinessAddress.text.toString().isNotBlank() &&
                binding.etPoBox.text.toString().isNotBlank()

        binding.btnNextBusiness2.isEnabled = isDataFilled
    }

    private fun saveFormData() {
        commonSharedPreferences.saveStringData(Constants.BUSINESSPHONE, binding.etPhoneNumber.text.toString())
        commonSharedPreferences.saveStringData(Constants.BUSINESSEMAIL, binding.etBusinessEmail.text.toString())
        commonSharedPreferences.saveStringData(Constants.BUSINESSADDRESS, binding.etBusinessAddress.text.toString())
        commonSharedPreferences.saveStringData(Constants.BUSINESSPOBOX, binding.etPoBox.text.toString())

    }
}