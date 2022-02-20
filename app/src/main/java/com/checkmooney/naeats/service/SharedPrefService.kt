package com.checkmooney.naeats.service

import android.content.Context
import javax.inject.Inject

class SharedPrefService @Inject constructor(private val context: Context) {
    private val sharedPreferences =
        context.getSharedPreferences(context.packageName + ".prefs", Context.MODE_PRIVATE)

    fun setString(key: String, value: String) {
        with(sharedPreferences.edit()) {
            putString(key, value)
            apply()
        }
    }

    fun getString(key: String): String? {
        return sharedPreferences.getString(key, null)
    }
}
