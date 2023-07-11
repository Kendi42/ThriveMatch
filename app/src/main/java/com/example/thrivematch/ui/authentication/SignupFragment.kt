package com.example.thrivematch.ui.authentication

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.thrivematch.R
import com.example.thrivematch.data.network.AuthAPI
import com.example.thrivematch.data.network.Resource
import com.example.thrivematch.data.repository.AuthRepository
import com.example.thrivematch.databinding.FragmentSignupBinding
import com.example.thrivematch.ui.base.BaseFragment
import com.example.thrivematch.util.FormValidation
import com.example.thrivematch.util.handleApiError

class SignupFragment : BaseFragment<AuthenticationViewModel, FragmentSignupBinding, AuthRepository>(){
    private lateinit var formValidation: FormValidation

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        formValidation= FormValidation()
        binding.signupProgressBar.isVisible= false

        viewModel.signupResponse.observe(viewLifecycleOwner, Observer{
            binding.signupProgressBar.isVisible= false
            when(it){
                is Resource.Success ->{
                    Toast.makeText(requireContext(),"Signup Success", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_signupFragment_to_accountTypeFragment)
                }
                is Resource.Failure ->handleApiError(it){ signup() }
            }
        })

        binding.tvAlreadyHaveAccount.setOnClickListener{
            findNavController().navigate(R.id.action_signupFragment_to_loginFragment)
        }

        binding.btnSignup.setOnClickListener{
//            signup()
            findNavController().navigate(R.id.action_signupFragment_to_accountTypeFragment)

        }
    }

    private fun signup() {
        val name= binding.etSignupName.text.toString().trim()
        val email= binding.etSignupEmail.text.toString().trim()
        val password= binding.etSignupPassword.text.toString().trim()
        val confirmPassword= binding.etSignupConfirmPassword.text.toString().trim()

        if(formValidation.checkIfEmailIsValid(email)== null && formValidation.checkPasswordsMatch(pass=password, confPass = confirmPassword)== null &&formValidation.validPassword(passwordText = password)== null){
            binding.signupProgressBar.isVisible= true
            viewModel.signup(name, email, password)
        }
        else{
            binding.etSignupEmail.error = formValidation.checkIfEmailIsValid(email)
            binding.etSignupConfirmPassword.error= formValidation.checkPasswordsMatch(pass=password, confPass = confirmPassword)
            binding.etSignupPassword.error= formValidation.validPassword(password)
        }
    }

    override fun getViewModel()= AuthenticationViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentSignupBinding.inflate(inflater, container, false)

    override fun getFragmentRepository()= AuthRepository(remoteDataSource.buildApi(AuthAPI::class.java))

}