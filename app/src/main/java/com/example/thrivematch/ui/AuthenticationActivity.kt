package com.example.thrivematch.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.thrivematch.R
import com.example.thrivematch.util.CommonSharedPreferences
import com.example.thrivematch.util.Constants

class AuthenticationActivity : AppCompatActivity() {
    private lateinit var commonSharedPreferences: CommonSharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authentication)
        commonSharedPreferences = CommonSharedPreferences(this)
        commonSharedPreferences.clearSharedPreferences()
    }

    override fun onDestroy() {
        super.onDestroy()
    }



}