package com.example.thrivematch.ui.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.thrivematch.ui.adapters.PendingMatchRecyclerViewAdapter
import com.example.thrivematch.R
import com.example.thrivematch.data.models.PendingMatchModel
import com.example.thrivematch.databinding.FragmentLikedBinding

class LikedFragment : Fragment(R.layout.fragment_liked) {
    private lateinit var binding: FragmentLikedBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PendingMatchRecyclerViewAdapter
    private val viewModel: HomeViewModel by activityViewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding= FragmentLikedBinding.bind(view)
        val itemList = viewModel.getLikedCards()
        Log.i("LikedItems LF", itemList.toString())

        recyclerView = binding.rvPendingMatches
        adapter = PendingMatchRecyclerViewAdapter(itemList)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

    }

}