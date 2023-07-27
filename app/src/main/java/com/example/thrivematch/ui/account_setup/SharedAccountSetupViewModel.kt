package com.example.thrivematch.ui.account_setup

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.thrivematch.data.models.BusinessModel
import com.example.thrivematch.data.models.InvestorModel
import com.example.thrivematch.data.network.Resource
import com.example.thrivematch.data.repository.AccountSetupRepository
import com.example.thrivematch.data.response.AccountSetupResponse
import com.example.thrivematch.data.response.LoginResponse
import com.example.thrivematch.ui.base.BaseViewModel
import kotlinx.coroutines.launch

class SharedAccountSetupViewModel(private val repository: AccountSetupRepository): BaseViewModel(repository) {
    private val _accountSetupResponse : MutableLiveData<Resource<AccountSetupResponse>> = MutableLiveData()
    val accountSetupResponse: LiveData<Resource<AccountSetupResponse>>
        get()= _accountSetupResponse

    //Investor Account Setup
    var investorData = MutableLiveData<InvestorModel>()
    fun setInvestorData(submittedInvestorData: InvestorModel) = viewModelScope.launch {
        investorData.value = submittedInvestorData
        _accountSetupResponse.value = repository.investorSetup(submittedInvestorData)
    }

    //Business Account Setup
    var businessData = MutableLiveData<BusinessModel>()
    fun setBusinessData(submittedBusinessData: BusinessModel) = viewModelScope.launch {
        businessData.value = submittedBusinessData
        _accountSetupResponse.value = repository.businessSetup(submittedBusinessData)
    }


}