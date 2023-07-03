package com.example.thrivematch.ui.authentication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.thrivematch.R
import com.example.thrivematch.data.network.AuthAPI
import com.example.thrivematch.data.network.Resource
import com.example.thrivematch.data.repository.AuthRepository
import com.example.thrivematch.databinding.FragmentLoginBinding
import com.example.thrivematch.ui.HomeActivity
import com.example.thrivematch.ui.account_setup.SharedAccountSetupViewModel
import com.example.thrivematch.ui.base.BaseFragment
import com.example.thrivematch.util.CommonSharedPreferences
import com.example.thrivematch.util.Constants
import com.example.thrivematch.util.FormValidation
import kotlinx.coroutines.launch

class LoginFragment : BaseFragment<AuthenticationViewModel,FragmentLoginBinding, AuthRepository >() {
    private lateinit var formValidation:FormValidation
    private lateinit var commonSharedPreferences: CommonSharedPreferences

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        formValidation= FormValidation()
        commonSharedPreferences = CommonSharedPreferences(requireContext())

        binding.loginProgressBar.isVisible= false

        viewModel.loginResponse.observe(viewLifecycleOwner, Observer {
            binding.loginProgressBar.isVisible= false
            when(it){
                is Resource.Success ->{
                    Toast.makeText(requireContext(),"Login Success", Toast.LENGTH_SHORT).show()
                    commonSharedPreferences.saveStringData(Constants.AUTHTOKEN, it.value.token)
                    Log.i("Login Success", it.toString())
                    val intent = Intent(requireActivity(), HomeActivity::class.java)
                    startActivity(intent)
                }
                is Resource.Failure -> {
                    Toast.makeText(requireContext(), "Login Failure. Try again", Toast.LENGTH_SHORT).show()
                    Log.e("Login Failure", it.toString())

                }
            }
        })

        binding.tvDontHaveAccount.setOnClickListener{
            findNavController().navigate(R.id.action_loginFragment_to_signupFragment)
        }
        binding.btnLogin.setOnClickListener {
            login()

        }

    }

    private fun login() {
        binding.loginProgressBar.isVisible= true
        val email= binding.etLoginEmail.text.toString().trim()
        val password= binding.etLoginPassword.text.toString().trim()

        if(formValidation.checkIfEmailIsValid(email)==null){
            viewModel.login(email, password)
        }
        else{
            binding.etLoginEmail.error= formValidation.checkIfEmailIsValid(email)
        }
    }

    override fun getViewModel() = AuthenticationViewModel::class.java


    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    )= FragmentLoginBinding.inflate(inflater, container, false)


    override fun getFragmentRepository()= AuthRepository(remoteDataSource.buildApi(AuthAPI::class.java))
}