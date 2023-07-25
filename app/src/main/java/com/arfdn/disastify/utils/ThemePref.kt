package com.arfdn.disastify.utils

import android.content.Context

fun setThemePreference(context: Context, theme: String) {
    val sharedPref = context.getSharedPreferences("ThemePref", Context.MODE_PRIVATE)
    with (sharedPref.edit()) {
        putString("theme", theme)
        apply()
    }
}

fun getThemePreference(context: Context): String {
    val sharedPref = context.getSharedPreferences("ThemePref", Context.MODE_PRIVATE)
    return sharedPref.getString("theme", "light") ?: "light" // Default to "light" if no preference found
}