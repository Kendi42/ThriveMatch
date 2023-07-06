package com.example.thrivematch.ui.side_nav_fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.thrivematch.R
import com.example.thrivematch.databinding.FragmentDocumentUploadBinding
import com.example.thrivematch.databinding.FragmentMoreInfoBinding
import com.example.thrivematch.ui.adapters.PdfDownloadRecyclerViewAdapter
import com.example.thrivematch.ui.home.HomeViewModel

class DocumentUploadFragment : Fragment(R.layout.fragment_document_upload) {
    private lateinit var binding: FragmentDocumentUploadBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PdfDownloadRecyclerViewAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding= FragmentDocumentUploadBinding.bind(view)
        val sampleDocuments = listOf("Business Proposal", "Unique Value Proposition", "Financial Statements 2022-2023", "Company Manifesto", "Organizational Structure", "History of Company")

        recyclerView = binding.recyclerView
        adapter = PdfDownloadRecyclerViewAdapter(sampleDocuments)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

    }

}