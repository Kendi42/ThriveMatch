package com.example.thrivematch.ui.home_fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.thrivematch.R
import com.example.thrivematch.databinding.FragmentLikedBinding

class LikedFragment : Fragment(R.layout.fragment_liked) {
    private lateinit var binding: FragmentLikedBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding= FragmentLikedBinding.bind(view)

    }
}