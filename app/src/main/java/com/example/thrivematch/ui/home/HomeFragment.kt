package com.example.thrivematch.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.thrivematch.R
import com.example.thrivematch.data.models.CardSwipeItemModel
import com.example.thrivematch.databinding.FragmentHomeBinding
import com.example.thrivematch.ui.adapters.CardSwipeAdapter

class HomeFragment : Fragment(R.layout.fragment_home) {
    private lateinit var binding: FragmentHomeBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding=FragmentHomeBinding.bind(view)
        
        val cardStackView = binding.cardStackView
        val cardItems= createSampleCardItems()
        val adapter= CardSwipeAdapter(cardSwipeItem=cardItems, listener = {
            setOnCardItemClicked(it)
        })
        cardStackView.adapter= adapter


        binding.ivLeftSwipeIndicator.setOnClickListener {
            Toast.makeText(requireActivity(), "Disliked", Toast.LENGTH_SHORT).show()
        }
        binding.ivRightSwipeIndicator.setOnClickListener {
            Toast.makeText(requireActivity(), "Liked", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setOnCardItemClicked(it: CardSwipeItemModel) {
            Toast.makeText(requireActivity(), it.name, Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_homeFragment_to_moreInfoFragment)
    }


    private fun createSampleCardItems(): List<CardSwipeItemModel> {

        val cardItems = mutableListOf<CardSwipeItemModel>()
        // Adding sample data to the list
         cardItems.add(
             CardSwipeItemModel(
                 name = "Bloom Energy",
                 industry = "Sustainable Energy",
                 description = "Harnessing the limitless potential of the sun, we're empowering individuals and businesses with clean, reliable, and sustainable energy solutions.",
                 imageURL = "https://img.freepik.com/free-vector/green-alternative-energy-power-logo_126523-2775.jpg?size=626&ext=jpg&ga=GA1.2.1090819380.1686834206&semt=ais"

             )
         )

        cardItems.add(
            CardSwipeItemModel(
                name = "Jozzby",
                industry = "Technology",
                description = "We ignite the tech industry with disruptive solutions. Invest in Jozzby and fuel the future of limitless possibilities.",
                imageURL = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS1aHK5iVcTAAOzjFUaxsUjJrp1atZtWHmwSHqrg7TXlQ&s"
            )
        )

        cardItems.add(
            CardSwipeItemModel(
                name = "BTech",
                industry = "Technology",
                description = "We ignite the tech industry with disruptive solutions. Invest in Btech and fuel the future of limitless possibilities.",
                imageURL = "https://cdn.dribbble.com/userupload/7733577/file/original-a2f0a453abc9ef61612d721aeb8a39da.jpg?compress=1&resize=2048x1536"
            )
        )

        // Add more dating card items to the list

        return cardItems

    }
}