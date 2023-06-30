package com.example.thrivematch.ui.authentication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.thrivematch.R
import com.example.thrivematch.databinding.FragmentLoginBinding
import com.example.thrivematch.ui.HomeActivity
import com.example.thrivematch.ui.account_setup.SharedAccountSetupViewModel

class LoginFragment : Fragment(R.layout.fragment_login) {
    private lateinit var binding: FragmentLoginBinding
    private val authViewModel: AuthenticationViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding=FragmentLoginBinding.bind(view)

        binding.tvDontHaveAccount.setOnClickListener{
            findNavController().navigate(R.id.action_loginFragment_to_signupFragment)
        }

        binding.btnLogin.setOnClickListener {
            login()
            val intent = Intent(requireContext(), HomeActivity::class.java)
            startActivity(intent)
        }

    }

    private fun login() {
        val email= binding.etLoginEmail.text.toString().trim()
        val password= binding.etLoginPassword.text.toString().trim()
        authViewModel.login(email, password)
    }

}