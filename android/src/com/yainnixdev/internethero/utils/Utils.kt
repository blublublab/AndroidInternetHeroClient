package com.yainnixdev.internethero.utils

import android.app.Activity
import android.content.SharedPreferences
import android.graphics.Point
import android.os.Build
import android.util.DisplayMetrics
import android.view.WindowInsets
import android.widget.Toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive

//Shared Prefs
lateinit var sharedPrefs: SharedPreferences
fun SharedPreferences.putAny(name: String, any: Any?): SharedPreferences {
    when(any){
        is String -> edit().putString(name, any).apply()
        is Int -> edit().putInt(name, any).apply()
        is Boolean -> edit().putBoolean(name, any).apply()
    }
    return this
}
fun SharedPreferences.getString(key: String) = this.getString(key, "") ?: ""
fun SharedPreferences.getInt(key: String) = this.getInt(key, 0)
fun SharedPreferences.getBoolean(key: String) = this.getBoolean(key, false)


//Toast util
private var toastMessage : Toast? = null
fun Activity.toast(text: String) {
    toastMessage?.cancel()
    toastMessage = Toast.makeText(this, text, Toast.LENGTH_SHORT)
    toastMessage?.show()
}





