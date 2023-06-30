package com.example.thrivematch.util

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


private const val FIELDAPP_PREFERENCE_DATA = "my_preference"
private const val FIELDAPP_PASTROLIST_DATA = "FIELDAPP_PASTROLIST_PREFERENCE"

class CommonSharedPreferences(context: Context) {
    val applicationContext: Context = context.applicationContext

    private var sharedPreferences: SharedPreferences =
        context.getSharedPreferences(
            FIELDAPP_PASTROLIST_DATA,
            Context.MODE_PRIVATE
        )

    fun saveStringData(key: String, value: String) {
        val editor = sharedPreferences.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun getStringData(key: String): String {
        Log.e(
            "",
            "GET_STRING_DATA${
                sharedPreferences.getString(
                    key,
                    ""
                ).toString()
            }"
        )
        return sharedPreferences.getString(key, "").toString()
    }

    fun saveStringArray(key: String, value: MutableList<String>) {
        val editor = sharedPreferences.edit()
        val jsonString = Gson().toJson(value)
        editor.putString(key, jsonString)
        editor.apply()
    }

    fun getStringArray(key: String): MutableList<String>? {
        val jsonString = sharedPreferences.getString(key, null)
        return if (jsonString != null) {
            val type = object : TypeToken<MutableList<String>>() {}.type
            Gson().fromJson(jsonString, type)
        } else {
            null
        }
    }

    fun clearSharedPreferences(){
        sharedPreferences.edit().clear().apply()
        Log.i("CommonSharedPreferences", sharedPreferences.toString())

    }
}
