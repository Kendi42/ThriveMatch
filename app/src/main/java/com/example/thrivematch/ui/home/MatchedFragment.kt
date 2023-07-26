package com.example.thrivematch.ui.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.thrivematch.data.models.MatchedModel
import com.example.thrivematch.data.network.HomeDataAPI
import com.example.thrivematch.data.repository.HomeRepository
import com.example.thrivematch.data.roomdb.database.AppDatabase
import com.example.thrivematch.databinding.FragmentMatchedBinding
import com.example.thrivematch.ui.adapters.MatchedRecyclerViewAdapter
import com.example.thrivematch.ui.base.BaseFragment
import com.example.thrivematch.util.CommonSharedPreferences
import java.util.*


class MatchedFragment : BaseFragment<HomeViewModel, FragmentMatchedBinding, HomeRepository>(),
    MatchedRecyclerViewAdapter.OnItemClickListener{
        override fun onItemClick(item: MatchedModel) {
//        viewModel.setSelectedCard(CardSwipeItemModel(item.name))
        }


    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MatchedRecyclerViewAdapter
    private var matchedList = ArrayList<MatchedModel>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding= FragmentMatchedBinding.bind(view)

        recyclerView= binding.rvMatchesMade

        viewModel.matchedCardsList.observe(viewLifecycleOwner, Observer { matchedCards ->
            matchedCards?.let {
                matchedList = matchedCards as ArrayList<MatchedModel>
                adapter = MatchedRecyclerViewAdapter(matchedList, requireActivity(),this)
                adapter.notifyDataSetChanged()
                recyclerView.adapter = adapter
                recyclerView.layoutManager = LinearLayoutManager(requireContext())
            }
        })

        binding.searchViewMatched.setOnClickListener {
            binding.searchViewMatched.isIconified = false
            binding.searchViewMatched.requestFocus()
        }

        binding.searchViewMatched.setOnQueryTextListener(object:
            SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                filterList(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterList(newText)
                return true
            }

        })


    }
    private fun filterList(newText: String?) {
        if( newText!=null){
            val filteredList = ArrayList<MatchedModel>()
            for(i in matchedList ){
                if(i.name.toLowerCase(Locale.ROOT).contains(newText.toLowerCase())){
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
    )= FragmentMatchedBinding.inflate(inflater, container, false)

    override fun getFragmentRepository()= HomeRepository(remoteDataSource.buildApi(HomeDataAPI::class.java), AppDatabase.invoke(requireContext()), CommonSharedPreferences(requireActivity()))

}