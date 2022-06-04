package com.dicoding.project.githubuser.data.local

import android.content.Context
import android.content.SharedPreferences

class PrefHelper(context: Context) {

    private val PREF_NAME = "Dark_Mode"
    private var sharedPref: SharedPreferences
    val editor: SharedPreferences.Editor

    init {
        sharedPref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        editor = sharedPref.edit()
    }

    fun put(key: String, value: Boolean){
        editor.putBoolean(key, value).apply()
    }

    fun getBoolean(key: String): Boolean{
        return sharedPref.getBoolean(key, false)
    }
}