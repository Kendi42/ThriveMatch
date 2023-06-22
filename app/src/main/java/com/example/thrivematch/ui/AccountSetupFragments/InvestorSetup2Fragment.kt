package com.example.thrivematch.ui.AccountSetupFragments

import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.setPadding
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
            chip.setPadding(50, 80, 50, 80)
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

            binding.btnBackInvestor2.setOnClickListener {
                findNavController().navigate(R.id.action_investorSetup2Fragment_to_investorSetup1Fragment)
            }


        }


    }
}