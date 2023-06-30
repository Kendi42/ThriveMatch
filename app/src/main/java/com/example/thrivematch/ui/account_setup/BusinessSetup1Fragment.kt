package com.example.thrivematch.ui.account_setup

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.ArrayAdapter
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import com.example.thrivematch.R
import com.example.thrivematch.databinding.FragmentBusinessSetup1Binding
import com.example.thrivematch.util.CommonSharedPreferences
import com.example.thrivematch.util.Constants
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat
import java.util.*


class BusinessSetup1Fragment : Fragment(R.layout.fragment_business_setup1) {

    private lateinit var binding: FragmentBusinessSetup1Binding
    private lateinit var commonSharedPreferences: CommonSharedPreferences


    override fun onResume() {
        super.onResume()
        // Drop Down Menu
        val industryOptions = listOf("Agriculture", "Technology", "Construction",
            "Energy", "Transportation", "Ecommerce",
            "Healthcare", "Education", "Media",
            "Finance", "Hospitality", "Art")
        val adapter= ArrayAdapter(requireContext(), R.layout.dropdown_item, industryOptions)
        binding.autocompleteIndustryOptions.setAdapter(adapter)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding= FragmentBusinessSetup1Binding.bind(view)

        commonSharedPreferences= CommonSharedPreferences(requireContext())


        //Progress Bar
        binding.progressBarBusiness1.progress= 50

        // Navigation Buttons
        binding.btnBackBusiness1.setOnClickListener{
            findNavController().navigate(R.id.action_businessSetup1Fragment_to_accountTypeFragment)
        }
        binding.btnNextBusiness1.setOnClickListener {
            saveFormData()
            findNavController().navigate(R.id.action_businessSetup1Fragment_to_businessSetup2Fragment)
        }


        // Date Picker
        binding.etDateFounded.setOnClickListener {
            val datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select date")
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .build()

            datePicker.addOnPositiveButtonClickListener { selectedDate ->
                val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
                calendar.timeInMillis = selectedDate
                val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                val formattedDate = dateFormat.format(calendar.time)
                binding.etDateFounded.setText(formattedDate)
            }

            datePicker.show(parentFragmentManager, "DatePicker")
        }

        // Edit Texts Data Persistence
        binding.etBusinessName.setText(if (commonSharedPreferences.getStringData(Constants.BUSINESSNAME) == "") "" else commonSharedPreferences.getStringData(Constants.BUSINESSNAME))
        binding.autocompleteIndustryOptions.setText(if (commonSharedPreferences.getStringData(Constants.INDUSTRY) == "") "" else commonSharedPreferences.getStringData(Constants.INDUSTRY))
        binding.etDateFounded.setText(if (commonSharedPreferences.getStringData(Constants.DATEFOUNDED) == "") "" else commonSharedPreferences.getStringData(Constants.DATEFOUNDED))
        binding.etBusinessDescription.setText(if (commonSharedPreferences.getStringData(Constants.COMPANYDESCRIPTION) == "") "" else commonSharedPreferences.getStringData(Constants.COMPANYDESCRIPTION))


        // Disabling Next Button until data if filled
        updateNextButtonState()
        binding.etBusinessName.addTextChangedListener{updateNextButtonState()}
        binding.autocompleteIndustryOptions.addTextChangedListener{updateNextButtonState()}
        binding.etDateFounded.addTextChangedListener{updateNextButtonState()}
        binding.etBusinessDescription.addTextChangedListener{updateNextButtonState()}


    }

    private fun updateNextButtonState() {
        val isDataFilled = binding.autocompleteIndustryOptions.text.isNotBlank() &&
                binding.etBusinessName.text.toString().isNotBlank() &&
                binding.etDateFounded.text.toString().isNotBlank() &&
                binding.etBusinessDescription.text.toString().isNotBlank()

        binding.btnNextBusiness1.isEnabled = isDataFilled    }

    private fun saveFormData() {
        commonSharedPreferences.saveStringData(Constants.BUSINESSNAME, binding.etBusinessName.text.toString())
        commonSharedPreferences.saveStringData(Constants.INDUSTRY, binding.autocompleteIndustryOptions.text.toString())
        commonSharedPreferences.saveStringData(Constants.DATEFOUNDED, binding.etDateFounded.text.toString())
        commonSharedPreferences.saveStringData(Constants.COMPANYDESCRIPTION, binding.etBusinessDescription.text.toString())
    }


}