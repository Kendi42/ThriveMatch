package com.example.thrivematch.ui.authentication_fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.findNavController
import com.example.thrivematch.R
import com.example.thrivematch.databinding.FragmentLoginBinding

class LoginFragment : Fragment(R.layout.fragment_login) {
    private lateinit var binding: FragmentLoginBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding=FragmentLoginBinding.bind(view)

        binding.tvDontHaveAccount.setOnClickListener{
            findNavController().navigate(R.id.action_loginFragment_to_signupFragment)
        }

    }

}