package com.example.thrivematch.ui.account_setup

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.thrivematch.data.models.BusinessModel
import com.example.thrivematch.data.models.InvestorModel
import kotlinx.coroutines.launch

class SharedAccountSetupViewModel: ViewModel() {

    //Investor Account Setup
    var investorData = MutableLiveData<InvestorModel>()
    fun setInvestorData(submittedInvestorData: InvestorModel) = viewModelScope.launch {
        investorData.value = submittedInvestorData
        Log.i("Investor Data", investorData.value.toString())
    }

    //Business Account Setup
    var businessData = MutableLiveData<BusinessModel>()
    fun setBusinessData(submittedBusinessData: BusinessModel) = viewModelScope.launch {
        businessData.value = submittedBusinessData
        Log.i("Business Data", businessData.value.toString())

    }
}