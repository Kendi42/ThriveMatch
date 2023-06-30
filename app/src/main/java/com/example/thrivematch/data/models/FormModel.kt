package com.example.thrivematch.data.models

data class InvestorModel(
    var investorType: String,
    var name: String,
    var description: String,
    var selectedInterests: MutableList<String>?  = null,
    var photo: String?
)

data class BusinessModel(
    var businessName: String,
    var industry: String,
    var dateFounded: String,
    var companyDescription: String,
    var phoneNumber: String,
    var email: String,
    var address: String,
    var poBox: String,
    var photo: String?
)


