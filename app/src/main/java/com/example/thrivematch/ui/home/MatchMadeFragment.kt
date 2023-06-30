package com.example.thrivematch.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import com.example.thrivematch.R
import com.example.thrivematch.databinding.FragmentMatchMadeBinding

class MatchMadeFragment : Fragment(R.layout.fragment_match_made) {
    private lateinit var binding: FragmentMatchMadeBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding= FragmentMatchMadeBinding.bind(view)

    }



}