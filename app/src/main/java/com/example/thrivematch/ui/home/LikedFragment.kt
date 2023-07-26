package com.example.thrivematch.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.thrivematch.ui.adapters.PendingMatchRecyclerViewAdapter
import com.example.thrivematch.R
import com.example.thrivematch.data.models.CardSwipeItemModel
import com.example.thrivematch.data.models.PendingMatchModel
import com.example.thrivematch.data.network.HomeDataAPI
import com.example.thrivematch.data.repository.HomeRepository
import com.example.thrivematch.data.roomdb.database.AppDatabase
import com.example.thrivematch.databinding.FragmentLikedBinding
import com.example.thrivematch.ui.base.BaseFragment
import com.example.thrivematch.util.CommonSharedPreferences
import java.util.*
import java.util.Locale.ROOT
import java.util.Locale.filter
import kotlin.collections.ArrayList

class LikedFragment : BaseFragment<HomeViewModel, FragmentLikedBinding, HomeRepository>(),

    PendingMatchRecyclerViewAdapter.OnItemClickListener{
    override fun onItemClick(item: PendingMatchModel) {
//        viewModel.setSelectedCard(CardSwipeItemModel(item.name))

    }

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PendingMatchRecyclerViewAdapter
    private var likedList = ArrayList<PendingMatchModel>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding= FragmentLikedBinding.bind(view)
        recyclerView = binding.rvPendingMatches
        adapter = PendingMatchRecyclerViewAdapter(likedList, this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        viewModel.likedCardsList.observe(viewLifecycleOwner, Observer {likedCards->
            likedCards?.let {
                Log.i("LikedCardsFragm", likedCards.toString())
                likedList.clear()
                likedList.addAll(likedCards)
                adapter.notifyDataSetChanged()
            }
        })

        binding.searchView.setOnClickListener {
            binding.searchView.isIconified = false
            binding.searchView.requestFocus()
        }
        binding.searchView.setOnQueryTextListener(object:
        SearchView.OnQueryTextListener, androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                filterList(query)
                return true
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                filterList(newText)
                return true
            }
        }

        )

    }
    private fun filterList(newText: String?) {
        if( newText!=null){
            val filteredList = ArrayList<PendingMatchModel>()
            for(i in likedList ){
                if(i.name.lowercase(Locale.ROOT).contains(newText.lowercase(Locale.ROOT))){
                    filteredList.add(i)
                }
            }
            if(filteredList.isNotEmpty()){
                adapter.setFilteredList(filteredList)

            }
        }
    }

    override fun getViewModel()= HomeViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    )= FragmentLikedBinding.inflate(inflater, container, false)

    override fun getFragmentRepository()= HomeRepository(remoteDataSource.buildApi(HomeDataAPI::class.java), AppDatabase.invoke(requireContext()), CommonSharedPreferences(requireActivity()))

}