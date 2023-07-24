package com.example.thrivematch.util

import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.thrivematch.data.network.Resource
import com.example.thrivematch.ui.authentication.LoginFragment
import com.example.thrivematch.ui.authentication.SignupFragment
import com.example.thrivematch.ui.base.BaseFragment
import com.google.android.material.snackbar.Snackbar
import org.json.JSONObject


fun View.snackbar(message: String, action: (() -> Unit)? = null) {
        val snackbar = Snackbar.make(this, message, Snackbar.LENGTH_LONG)
        action?.let {
            snackbar.setAction("Retry") {
                it()
            }
        }
        snackbar.show()
    }


    fun Fragment.handleApiError(
        failure: Resource.Failure,
        retry: (() -> Unit)? = null
    ) {
        when {
            failure.isNetworkError -> requireView().snackbar(
                "Please check your internet connection",
                retry
            )
            failure.errorCode == 400 ->{
                if (this is SignupFragment) {
                    val errorBody = failure.errorBody?.string()
                    val jsonObject = JSONObject(errorBody.toString())
                    val message = jsonObject.getString("message")
                    requireView().snackbar(message)
                    Log.i("Looking for message",  message )
                }

            }
            failure.errorCode == 401 -> {
                if (this is LoginFragment) {
                    requireView().snackbar("You've entered incorrect email or password")
                } else {
                    requireView().snackbar("Something went wrong. Try again")
                }
            }
            else -> {
                val error = failure.errorBody?.string().toString()
                requireView().snackbar(error)

            }


        }

    }
