package com.example.thrivematch.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.thrivematch.ui.adapters.PendingMatchRecyclerViewAdapter
import com.example.thrivematch.R
import com.example.thrivematch.data.models.PendingMatchModel
import com.example.thrivematch.data.network.HomeDataAPI
import com.example.thrivematch.data.repository.HomeRepository
import com.example.thrivematch.data.roomdb.database.AppDatabase
import com.example.thrivematch.databinding.FragmentLikedBinding
import com.example.thrivematch.ui.base.BaseFragment

class LikedFragment : BaseFragment<HomeViewModel, FragmentLikedBinding, HomeRepository>() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PendingMatchRecyclerViewAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding= FragmentLikedBinding.bind(view)
        recyclerView = binding.rvPendingMatches
        viewModel.likedCardsList.observe(viewLifecycleOwner, Observer {likedCards->
            likedCards?.let {
                Log.i("LikedCardsFragm", likedCards.toString())
                adapter = PendingMatchRecyclerViewAdapter(likedCards)
                adapter.notifyDataSetChanged()
                recyclerView.adapter = adapter
                recyclerView.layoutManager = LinearLayoutManager(requireContext())
            }
        })
    }

    override fun getViewModel()= HomeViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    )= FragmentLikedBinding.inflate(inflater, container, false)

    override fun getFragmentRepository()= HomeRepository(remoteDataSource.buildApi(HomeDataAPI::class.java), AppDatabase.invoke(requireContext()))

}