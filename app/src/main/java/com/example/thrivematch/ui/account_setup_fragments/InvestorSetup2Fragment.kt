package com.example.thrivematch.ui.account_setup_fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.findNavController
import com.example.thrivematch.R
import com.example.thrivematch.databinding.FragmentInvestorSetup2Binding
import com.google.android.material.chip.Chip

class InvestorSetup2Fragment : Fragment(R.layout.fragment_investor_setup2) {

    private lateinit var binding: FragmentInvestorSetup2Binding
    private val chipClickedMap: MutableMap<Chip, Boolean> = HashMap()



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val selectedInterests: MutableList<String> = mutableListOf()

        binding = FragmentInvestorSetup2Binding.bind(view)

        //Progress Bar
        binding.progressBarInvestor2.progress= 75

        val tags = arrayOf(
            "Agriculture", "Technology", "Construction",
            "Energy", "Transportation", "Ecommerce",
            "Healthcare", "Education", "Media",
            "Finance", "Hospitality", "Art",
            "Education", "Media", "Finance"
        )

        for (tag in tags) {
            val chip = Chip(requireContext())
            chip.text = tag
            chip.textSize= 16f
            chip.setPadding(30, 50, 30, 50)
            chip.setChipBackgroundColorResource(R.color.colorSecondaryLight)

            chip.setOnClickListener {
                val isChipClicked = chipClickedMap[chip] ?: false
                if (isChipClicked) {
                    chip.setChipBackgroundColorResource(R.color.colorSecondaryLight)
                    chipClickedMap[chip] = false
                    selectedInterests.remove(chip.text.toString()) // Remove deselected interest

                } else {
                    chip.setChipBackgroundColorResource(R.color.colorSecondary)
                    chipClickedMap[chip] = true
                    val content = chip.text.toString()
                    selectedInterests.add(content) // Add selected interest
                }
                Log.i("Chips", chipClickedMap.toString())
                Log.i("Selected Interests", selectedInterests.toString())
            }

            // Add chip to the ChipGroup
            binding.chipGroupInterests.addView(chip)
            Log.i("Chips", chipClickedMap.toString())

            binding.btnNextInvestor2.setOnClickListener {
                findNavController().navigate(R.id.action_investorSetup2Fragment_to_investorSetup3Fragment)
            }

            binding.btnBackInvestor2.setOnClickListener {
                findNavController().navigate(R.id.action_investorSetup2Fragment_to_investorSetup1Fragment)
            }


        }


    }
}