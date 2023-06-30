package com.example.thrivematch.ui.more_info

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import com.example.thrivematch.R
import com.example.thrivematch.databinding.FragmentMoreInfoBinding

class StartupDetailsFragment : Fragment(R.layout.fragment_more_info) {
    private lateinit var binding:FragmentMoreInfoBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding= FragmentMoreInfoBinding.bind(view)



    }

}