package com.example.thrivematch.ui.more_info

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.thrivematch.R
import com.example.thrivematch.data.models.PdfFileModel
import com.example.thrivematch.data.network.HomeDataAPI
import com.example.thrivematch.data.repository.HomeRepository
import com.example.thrivematch.data.roomdb.database.AppDatabase
import com.example.thrivematch.databinding.FragmentMoreInfoBinding
import com.example.thrivematch.ui.adapters.PdfDownloadRecyclerViewAdapter
import com.example.thrivematch.ui.adapters.PendingMatchRecyclerViewAdapter
import com.example.thrivematch.ui.base.BaseFragment
import com.example.thrivematch.ui.home.HomeViewModel
import java.util.*

class StartupDetailsFragment: BaseFragment<HomeViewModel, FragmentMoreInfoBinding, HomeRepository>() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PdfDownloadRecyclerViewAdapter
    private var fileDetails: LinkedList<PdfFileModel> = LinkedList()
    private val sharedViewModel: DocumentSharingSharedViewModel by activityViewModels()

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
        else{
            findNavController().navigate(R.id.action_moreInfoFragment_to_homeFragment)
            Toast.makeText(requireActivity(), "Selected Card is Empty", Toast.LENGTH_SHORT).show()
        }

        fileDetails= sharedViewModel.getDocumentList()

        recyclerView = binding.rvPdfs
        adapter = PdfDownloadRecyclerViewAdapter(fileDetails, requireActivity())
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        binding.btnMoreInfoMatch.setOnClickListener {

        }

    }

    override fun getViewModel()= HomeViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    )=FragmentMoreInfoBinding.inflate(inflater, container, false)

    override fun getFragmentRepository()=HomeRepository(remoteDataSource.buildApi(HomeDataAPI::class.java), AppDatabase.invoke(requireContext()))

}