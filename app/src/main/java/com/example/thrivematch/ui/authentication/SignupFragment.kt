package com.example.thrivematch.ui.authentication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.thrivematch.R
import com.example.thrivematch.databinding.FragmentSignupBinding
import com.example.thrivematch.util.FormValidation

class SignupFragment : Fragment(R.layout.fragment_signup) {
    private lateinit var binding: FragmentSignupBinding
    private val authViewModel: AuthenticationViewModel by viewModels()
    private lateinit var formValidation: FormValidation


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSignupBinding.bind(view)
        formValidation= FormValidation()

        binding.tvAlreadyHaveAccount.setOnClickListener{
            findNavController().navigate(R.id.action_signupFragment_to_loginFragment)
        }

        binding.btnSignup.setOnClickListener{
            signup()
        }
    }

    private fun signup() {
        val name= binding.etSignupName.text.toString().trim()
        val email= binding.etSignupEmail.text.toString().trim()
        val password= binding.etSignupPassword.text.toString().trim()
        val confirmPassword= binding.etSignupConfirmPassword.text.toString().trim()

        if(formValidation.checkIfEmailIsValid(email)== null && formValidation.checkPasswordsMatch(pass=password, confPass = confirmPassword)== null &&formValidation.validPassword(passwordText = password)== null){
            authViewModel.signup(name, email, password)
            Toast.makeText(requireActivity(), "Successful Signup", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_signupFragment_to_accountTypeFragment)
        }
        else{
            binding.etSignupEmail.error = formValidation.checkIfEmailIsValid(email)
            binding.etSignupConfirmPassword.error= formValidation.checkPasswordsMatch(pass=password, confPass = confirmPassword)
            binding.etSignupPassword.error= formValidation.validPassword(password)
        }
    }

}