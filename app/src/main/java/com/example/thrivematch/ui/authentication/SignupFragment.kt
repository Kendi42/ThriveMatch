package com.example.thrivematch.ui.authentication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.thrivematch.R
import com.example.thrivematch.databinding.FragmentSignupBinding

class SignupFragment : Fragment(R.layout.fragment_signup) {
    private lateinit var binding: FragmentSignupBinding
    private val authViewModel: AuthenticationViewModel by viewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSignupBinding.bind(view)

        binding.tvAlreadyHaveAccount.setOnClickListener{
            findNavController().navigate(R.id.action_signupFragment_to_loginFragment)
        }

        binding.btnSignup.setOnClickListener{
            signup()
            findNavController().navigate(R.id.action_signupFragment_to_accountTypeFragment)
        }
    }

    private fun signup() {
        val name= binding.etSignupName.text.toString().trim()
        val email= binding.etSignupEmail.text.toString().trim()
        val password= binding.etSignupPassword.text.toString().trim()
//        val confirmPassword= binding.etSignupConfirmPassword.text.toString().trim()
        authViewModel.signup(name, email, password)    }

}