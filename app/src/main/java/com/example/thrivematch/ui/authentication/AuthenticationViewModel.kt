package com.example.thrivematch.ui.authentication

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.thrivematch.data.network.Resource
import com.example.thrivematch.data.repository.AuthRepository
import com.example.thrivematch.data.response.LoginResponse
import com.example.thrivematch.data.response.SignupResponse
import com.example.thrivematch.ui.base.BaseViewModel
import kotlinx.coroutines.launch

class AuthenticationViewModel(private val repository: AuthRepository): BaseViewModel(repository){
    private val _loginResponse : MutableLiveData<Resource<LoginResponse>> = MutableLiveData()
    val loginResponse: LiveData<Resource<LoginResponse>>
        get()= _loginResponse

    private val _signupResponse : MutableLiveData<Resource<SignupResponse>> = MutableLiveData()
    val signupResponse: LiveData<Resource<SignupResponse>>
        get()= _signupResponse



    fun login(
        email: String,
        password: String
    )= viewModelScope.launch{
        _loginResponse.value = repository.login(email=email, password = password)
        Log.i("Login Response", _loginResponse.value.toString())

    }
    fun signup(
        name: String,
        email: String,
        password: String
    ) = viewModelScope.launch{
        _signupResponse.value = repository.signup(name=name, email=email, password = password)
        Log.i("Signup Data", "$name, $email, $password")
    }

}