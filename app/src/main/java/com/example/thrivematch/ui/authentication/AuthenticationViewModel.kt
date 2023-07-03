package com.example.thrivematch.ui.authentication

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.thrivematch.data.network.Resource
import com.example.thrivematch.data.repository.AuthRepository
import com.example.thrivematch.data.response.LoginResponse
import kotlinx.coroutines.launch

class AuthenticationViewModel(private val repository: AuthRepository): ViewModel(){
    private val _loginResponse : MutableLiveData<Resource<LoginResponse>> = MutableLiveData()
    val loginResponse: LiveData<Resource<LoginResponse>>
        get()= _loginResponse

    fun login(
        email: String,
        password: String
    )= viewModelScope.launch{
        _loginResponse.value = repository.login(email=email, password = password)
        Log.i("Login Response", _loginResponse.toString())

    }

    fun signup(
        name: String,
        email: String,
        password: String
    ) {
        Log.i("Signup Data", "$name, $email, $password")
    }

}