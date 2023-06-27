package com.example.thrivematch.ui.home_fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.thrivematch.PendingMatchRecyclerViewAdapter
import com.example.thrivematch.R
import com.example.thrivematch.data.CardSwipeItem
import com.example.thrivematch.data.PendingMatchModel
import com.example.thrivematch.databinding.FragmentLikedBinding

class LikedFragment : Fragment(R.layout.fragment_liked) {
    private lateinit var binding: FragmentLikedBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PendingMatchRecyclerViewAdapter
    private lateinit var itemList: List<PendingMatchModel>
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding= FragmentLikedBinding.bind(view)


        // Initialize your data list
        itemList = createPendingMatchItems()

        // Find the RecyclerView in your fragment layout
        recyclerView = binding.rvPendingMatches

        // Create an instance of your adapter and pass in the data list
        adapter = PendingMatchRecyclerViewAdapter(itemList)

        // Set the adapter for the RecyclerView
        recyclerView.adapter = adapter

        // Optionally, you can also set the layout manager for the RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

    }

    private fun createPendingMatchItems():  List<PendingMatchModel> {
        val itemList = mutableListOf<PendingMatchModel>()

        itemList.add(
            PendingMatchModel(
                name = "Jozzby",
                imageURL = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS1aHK5iVcTAAOzjFUaxsUjJrp1atZtWHmwSHqrg7TXlQ&s"
            )
        )

        itemList.add(
            PendingMatchModel(
                name = "Bloom Energy",
                imageURL = "https://img.freepik.com/free-vector/green-alternative-energy-power-logo_126523-2775.jpg?size=626&ext=jpg&ga=GA1.2.1090819380.1686834206&semt=ais"
            )
        )



        return itemList



    }
}