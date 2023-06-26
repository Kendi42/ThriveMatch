package com.example.thrivematch.ui.account_setup_fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.findNavController
import com.example.thrivematch.R
import com.example.thrivematch.databinding.FragmentBusinessSetup1Binding
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat
import java.util.*


class BusinessSetup1Fragment : Fragment(R.layout.fragment_business_setup1) {

    private lateinit var binding: FragmentBusinessSetup1Binding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding= FragmentBusinessSetup1Binding.bind(view)

        //Progress Bar
        binding.progressBarBusiness1.progress= 50

        binding.btnBackBusiness1.setOnClickListener{
            findNavController().navigate(R.id.action_businessSetup1Fragment_to_accountTypeFragment)
        }
        binding.btnNextBusiness1.setOnClickListener {
            findNavController().navigate(R.id.action_businessSetup1Fragment_to_businessSetup2Fragment)
        }

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

    }



}