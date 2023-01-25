package com.ur4n0.castellari.util

import android.R.string
import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import android.util.Log


const val PREFERENCE_NAME = "com.ur4n0.castellari.config_castellari"
const val PREFERENCE_KEY_TO_PATH = "pathToSave"

fun getPreference(context: Context): SharedPreferences{
    return context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE )
}

fun getPathToSave(context: Context): String {
    val preferences: SharedPreferences = getPreference(context)

    val folder: String? = preferences.getString(PREFERENCE_KEY_TO_PATH, "")
    Log.d("keyValueManager.getPathToSave returnValue: ", folder.toString())
    return if(!folder.equals("")) folder.toString() else ""
}

fun updatePathToSave(context: Context, newPath: String){
    val preferences: SharedPreferences = getPreference(context)

    preferences
        .edit()
        .putString(PREFERENCE_KEY_TO_PATH, newPath)
        .apply()

    Log.d("keyValueManager.updatePathToSave updated path to: ", newPath)
}