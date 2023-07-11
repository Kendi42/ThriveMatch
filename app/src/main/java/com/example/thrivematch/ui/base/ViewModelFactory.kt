package com.example.thrivematch.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.thrivematch.data.repository.AccountSetupRepository
import com.example.thrivematch.data.repository.AuthRepository
import com.example.thrivematch.data.repository.BaseRepository
import com.example.thrivematch.data.repository.HomeRepository
import com.example.thrivematch.ui.account_setup.SharedAccountSetupViewModel
import com.example.thrivematch.ui.authentication.AuthenticationViewModel
import com.example.thrivematch.ui.home.HomeViewModel

class ViewModelFactory (
    private val repository: BaseRepository
): ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when{
            modelClass.isAssignableFrom(AuthenticationViewModel::class.java)-> AuthenticationViewModel(repository as AuthRepository) as T
            modelClass.isAssignableFrom(SharedAccountSetupViewModel::class.java)-> SharedAccountSetupViewModel(repository as AccountSetupRepository) as T
            modelClass.isAssignableFrom(HomeViewModel::class.java)-> HomeViewModel(repository as HomeRepository) as T

            else-> throw IllegalArgumentException("ViewModel Class Not Found")
        }

    }
}