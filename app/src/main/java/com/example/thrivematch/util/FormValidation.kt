package com.example.thrivematch.util

import android.util.Patterns

class FormValidation {

     fun validPassword(passwordText: String): String? {
        if(passwordText.length < 8) {
            return "Minimum 8 Character Password"
        }
//        if(!passwordText.matches(".*[A-Z].*".toRegex())) {
//            return "Must Contain 1 Upper-case Character"
//        }
//        if(!passwordText.matches(".*[a-z].*".toRegex())) {
//            return "Must Contain 1 Lower-case Character"
//        }
//        if(!passwordText.matches(".*[@#\$%^&+=!].*".toRegex())) {
//            return "Must Contain 1 Special Character (@#\$%^&+=)"
//        }

        return null
    }

     fun checkIfEmailIsValid(emailInputText: String): String? {
        return if(!Patterns.EMAIL_ADDRESS.matcher(emailInputText).matches()){
            "Invalid Email Address"
        } else{
            null
        }
    }

     fun checkValidPhoneNumber(phoneText: String): String? {
        if(!phoneText.matches(".*[0-9].*".toRegex())){
            return "Only digit characters"
        }
        if(phoneText.length != 10){
            return "Must be 10 Digits"
        }
        return null
    }

     fun checkPasswordsMatch(pass: String, confPass: String): String?{
        return if(pass == confPass){
            null
        } else{
            "Passwords don't match"
        }
    }




}