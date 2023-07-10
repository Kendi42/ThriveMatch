package com.example.thrivematch.ui.more_info

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.thrivematch.R
import com.example.thrivematch.databinding.FragmentMoreInfoBinding
import com.example.thrivematch.ui.adapters.PdfDownloadRecyclerViewAdapter
import com.example.thrivematch.ui.adapters.PendingMatchRecyclerViewAdapter
import com.example.thrivematch.ui.home.HomeViewModel

class StartupDetailsFragment : Fragment(R.layout.fragment_more_info) {
    private lateinit var binding:FragmentMoreInfoBinding
    private val viewModel: HomeViewModel by activityViewModels()
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PdfDownloadRecyclerViewAdapter



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding= FragmentMoreInfoBinding.bind(view)
        val selectedCardData= viewModel.getSelectedCard()

        binding.ivMoreInfoBackArrow.setOnClickListener {
            findNavController().navigate(R.id.action_moreInfoFragment_to_homeFragment)
        }
        if(selectedCardData != null){
            Glide.with(requireContext()).load(selectedCardData.imageURL).into(binding.ivMoreinfoPhoto)
            binding.tvMoreInfoName.text = selectedCardData.name
            binding.tvMoreInfoIndustry.text = selectedCardData.industry
            binding.tvMoreInfoDescription.text = selectedCardData.description
        }

        val sampleDocuments = listOf("Business Proposal", "Unique Value Proposition", "Financial Statements 2022-2023", "Company Manifesto", "Organizational Structure", "History of Company")

//        recyclerView = binding.rvPdfs
//        adapter = PdfDownloadRecyclerViewAdapter(sampleDocuments)
//        recyclerView.adapter = adapter
//        recyclerView.layoutManager = LinearLayoutManager(requireContext())
//

        binding.btnMoreInfoMatch.setOnClickListener {
        }

    }

}