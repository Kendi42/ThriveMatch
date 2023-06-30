package com.example.thrivematch.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import com.example.thrivematch.R
import com.example.thrivematch.databinding.FragmentMatchedBinding


class MatchedFragment : Fragment(R.layout.fragment_matched) {
    private lateinit var binding: FragmentMatchedBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding= FragmentMatchedBinding.bind(view)

    }
}