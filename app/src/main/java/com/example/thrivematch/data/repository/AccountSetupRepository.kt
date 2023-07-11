package com.example.thrivematch.data.repository

import com.example.thrivematch.data.models.BusinessModel
import com.example.thrivematch.data.models.InvestorModel
import com.example.thrivematch.data.network.AccountSetupAPI
import com.example.thrivematch.data.network.AuthAPI
import com.example.thrivematch.data.request.LoginRequest
import com.example.thrivematch.data.request.SignupRequest

class AccountSetupRepository(
    private val api: AccountSetupAPI
): BaseRepository() {

    suspend fun investorSetup(investorData: InvestorModel ) = safeApiCall{
        api.investorAccountSetup(InvestorModel(investorType = investorData.investorType, name=investorData.name, description = investorData.description, selectedInterests = investorData.selectedInterests, photo = investorData.photo))
    }

    suspend fun businessSetup (businessData: BusinessModel) = safeApiCall{
        api.businessAccountSetup(BusinessModel(businessName = businessData.businessName, industry = businessData.industry, dateFounded = businessData.dateFounded, companyDescription = businessData.companyDescription, phoneNumber = businessData.phoneNumber,
                                                email = businessData.email, address=businessData.address, poBox = businessData.poBox, photo = businessData.photo))
    }
}