package com.example.thrivematch.ui.authentication

import android.content.Context
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
import com.example.thrivematch.data.roomdb.database.AppDatabase
import com.example.thrivematch.databinding.FragmentLoginBinding
import com.example.thrivematch.ui.HomeActivity
import com.example.thrivematch.ui.account_setup.SharedAccountSetupViewModel
import com.example.thrivematch.ui.base.BaseFragment
import com.example.thrivematch.util.CommonSharedPreferences
import com.example.thrivematch.util.Constants
import com.example.thrivematch.util.Constants.AUTH_TOKEN
import com.example.thrivematch.util.Constants.USER_ID
import com.example.thrivematch.util.FormValidation
import com.example.thrivematch.util.handleApiError
import kotlinx.coroutines.launch

class LoginFragment : BaseFragment<AuthenticationViewModel,FragmentLoginBinding, AuthRepository >() {
    private lateinit var formValidation:FormValidation
    private lateinit var commonSharedPreferences: CommonSharedPreferences

    override fun onAttach(context: Context) {
        super.onAttach(context)
        commonSharedPreferences= CommonSharedPreferences(requireContext())
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        formValidation= FormValidation()
        binding.loginProgressBar.isVisible= false

        viewModel.loginResponse.observe(viewLifecycleOwner, Observer {
            binding.loginProgressBar.isVisible= false
            when(it){
                is Resource.Success ->{
                    Toast.makeText(requireContext(),"Login Success", Toast.LENGTH_SHORT).show()
                    commonSharedPreferences.saveStringData(Constants.AUTHTOKEN, it.value.token)
                    AUTH_TOKEN = it.value.token
                    if(it.value.user.id !=null){USER_ID = it.value.user.id!!}
                    Log.i("Token saved is", it.value.token)
                    Log.i("Login Success", it.toString())
                    val startupAccount = it.value.user.hasCreatedStartUp
                    val investorAccount = it.value.user.hasCreatedInvestor
                    val individualInvestorAccount = it.value.user.hasCreatedIndividualInvestor
                    if(!startupAccount && !investorAccount && !individualInvestorAccount){
                        Toast.makeText(requireContext(),"Complete Account Setup", Toast.LENGTH_SHORT).show()
                        findNavController().navigate(R.id.action_loginFragment_to_accountTypeFragment)
                    }
                    else{
                        val intent = Intent(requireActivity(), HomeActivity::class.java)
                        startActivity(intent)
                    }
                }
                is Resource.Failure -> handleApiError(it){ login() }

                is Resource.Loading->{}
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
        val email= binding.etLoginEmail.text.toString().trim()
        val password= binding.etLoginPassword.text.toString().trim()

        if(formValidation.checkIfEmailIsValid(email)==null){
            binding.loginProgressBar.isVisible= true
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

    override fun getFragmentRepository()= AuthRepository(remoteDataSource.buildApi(AuthAPI::class.java), AppDatabase.invoke(requireContext()))
}