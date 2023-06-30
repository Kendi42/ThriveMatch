package com.example.thrivematch.ui.account_setup

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.view.children
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.thrivematch.R
import com.example.thrivematch.databinding.FragmentInvestorSetup2Binding
import com.example.thrivematch.util.CommonSharedPreferences
import com.example.thrivematch.util.Constants
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import kotlin.math.log

class InvestorSetup2Fragment : Fragment(R.layout.fragment_investor_setup2) {

    private lateinit var binding: FragmentInvestorSetup2Binding
    private val chipClickedMap: MutableMap<Chip, Boolean> = HashMap()
    private lateinit var commonSharedPreferences: CommonSharedPreferences
    private var selectedInterests: MutableList<String> = mutableListOf()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentInvestorSetup2Binding.bind(view)
        commonSharedPreferences= CommonSharedPreferences(requireContext())
        val investorInterests = commonSharedPreferences.getStringArray(Constants.INVESTORINTERESTS)
        if (investorInterests != null) {
            selectedInterests.clear() // Clear the list before adding interests
            selectedInterests.addAll(investorInterests)
        }

        //Progress Bar
        binding.progressBarInvestor2.progress= 75

        // Navigation Buttons
        binding.btnNextInvestor2.isEnabled = false
        binding.btnNextInvestor2.setOnClickListener {
            saveFormData()
            findNavController().navigate(R.id.action_investorSetup2Fragment_to_investorSetup3Fragment)
        }

        binding.btnBackInvestor2.setOnClickListener {
            saveFormData()
            findNavController().navigate(R.id.action_investorSetup2Fragment_to_investorSetup1Fragment)
        }


        //Todo: This should probably be done differently
        val tags = arrayOf(
            "Agriculture", "Technology", "Construction",
            "Energy", "Transportation", "Ecommerce",
            "Healthcare", "Education", "Media",
            "Finance", "Hospitality", "Art"
        )

        for (tag in tags) {
            val chip = Chip(requireContext())
            // Styling Chips
            chip.text = tag
            chip.textSize = 16f
            chip.setPadding(30, 50, 30, 50)
            chip.setChipBackgroundColorResource(R.color.colorSecondaryLight)

            if (selectedInterests.contains(tag)) {
                selectChip(chip)
                chipClickedMap[chip] ?: true
            }
            binding.btnNextInvestor2.isEnabled = selectedInterests.size >= 3

            chip.setOnClickListener {
                val isChipClicked = chipClickedMap[chip] ?: false
                if (isChipClicked) {
                    chip.setChipBackgroundColorResource(R.color.colorSecondaryLight)
                    chipClickedMap[chip] = false
                    selectedInterests.remove(chip.text.toString()) // Remove deselected interest
                } else {

                    selectChip(chip)
                }
                binding.btnNextInvestor2.isEnabled = selectedInterests.size >= 3
            }
            // Add chip to the ChipGroup
            binding.chipGroupInterests.addView(chip)
        }

        }

    private fun selectChip(chip: Chip) {
        chip.setChipBackgroundColorResource(R.color.colorSecondary)
        chipClickedMap[chip] = true
        val content = chip.text.toString()
        if (!selectedInterests.contains(content)) {
            selectedInterests.add(content) // Add selected interest if it doesn't exist
        }

    }

    private fun saveFormData() {
        commonSharedPreferences.saveStringArray(Constants.INVESTORINTERESTS, selectedInterests)
    }

}
