package com.example.thrivematch.ui.authentication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.findNavController
import com.example.thrivematch.R
import com.example.thrivematch.databinding.FragmentWelcomePageBinding

class WelcomePageFragment : Fragment(R.layout.fragment_welcome_page) {
    private lateinit var binding:FragmentWelcomePageBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding= FragmentWelcomePageBinding.bind(view)

        binding.btnToSignupFromWelcome.setOnClickListener{
            findNavController().navigate(R.id.action_welcomePageFragment_to_signupFragment)
        }

        binding.btnToLoginFromWelcome.setOnClickListener{
            findNavController().navigate(R.id.action_welcomePageFragment_to_loginFragment)
        }

    }

}