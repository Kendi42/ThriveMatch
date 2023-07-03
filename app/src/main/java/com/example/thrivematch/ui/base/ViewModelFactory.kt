package com.example.thrivematch.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.thrivematch.data.repository.AuthRepository
import com.example.thrivematch.data.repository.BaseRepository
import com.example.thrivematch.ui.authentication.AuthenticationViewModel

class ViewModelFactory (
    private val repository: BaseRepository
): ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when{
            modelClass.isAssignableFrom(AuthenticationViewModel::class.java)-> AuthenticationViewModel(repository as AuthRepository) as T

            else-> throw IllegalArgumentException("ViewModel Class Not Found")
        }

    }
}