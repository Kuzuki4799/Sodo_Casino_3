package com.rmtkds.sunwin.utils

import android.content.Context
import android.preference.PreferenceManager
import com.google.gson.Gson
import com.google.gson.JsonParser
import com.google.gson.reflect.TypeToken
import java.util.ArrayList

object ShareUtils {

    fun checkFirstFun(context: Context?): Boolean {
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        return if (preferences.getBoolean("first_run", true)) {
            preferences.edit().putBoolean("first_run", false).apply()
            true
        } else false
    }

    fun <T> put(context: Context?, key: String?, value: T) {
        if (value == null || context == null) return
        val type = object : TypeToken<T>() {}.type
        val json = Gson().toJson(value, type)
        val editor = PreferenceManager.getDefaultSharedPreferences(context).edit()
        editor.putString(key, json)
        editor.apply()
    }

    operator fun <T> get(context: Context, key: String, type: Class<T>): T {
        val json = PreferenceManager.getDefaultSharedPreferences(context).getString(key, "")
        return Gson().fromJson(json, type)
    }

    fun <T> putArray(context: Context?, key: String, value: ArrayList<T>?) {
        if (value == null || context == null) return
        val json = Gson().toJson(value, object : TypeToken<ArrayList<T>?>() {}.type)
        val className = ArrayList::class.java.name + key
        println("xxxxxx$className")
        val editor = PreferenceManager.getDefaultSharedPreferences(context).edit()
        editor.putString(className, json)
        editor.apply()
    }

    fun <T> getArray(context: Context, t: Class<T>): ArrayList<T> {
        val list = ArrayList<T>()
        val className = ArrayList::class.java.name + t.name
        val json = PreferenceManager.getDefaultSharedPreferences(context).getString(className, "")
        println("getArray:$json")
        val gSon = Gson()
        val array = JsonParser().parse(json).asJsonArray
        for (jsonElement in array) list.add(gSon.fromJson(jsonElement, t))
        return list
    }

    fun putString(context: Context?, key: String?, value: String?) {
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        if (value == null) preferences.edit().remove(key).apply()
        else preferences.edit().putString(key, value).apply()
    }

    fun getString(context: Context?, key: String?, defValue: String?): String? {
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        return if (preferences.contains(key)) {
            preferences.getString(key, defValue)
        } else defValue
    }

    fun putInt(context: Context?, key: String?, value: Int) {
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        preferences.edit().putInt(key, value).apply()
    }

    fun getInt(context: Context?, key: String?, defValue: Int): Int {
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        return if (preferences.contains(key)) {
            preferences.getInt(key, defValue)
        } else defValue
    }

    fun putLong(context: Context?, key: String?, value: Long) {
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        preferences.edit().putLong(key, value).apply()
    }

    fun getLong(context: Context?, key: String?, defValue: Long): Long {
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        return if (preferences.contains(key)) {
            preferences.getLong(key, defValue)
        } else defValue
    }

    fun putBoolean(context: Context?, key: String?, value: Boolean) {
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        preferences.edit().putBoolean(key, value).apply()
    }

    fun getBoolean(context: Context?, key: String?, defValue: Boolean): Boolean {
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        return if (preferences.contains(key)) {
            preferences.getBoolean(key, defValue)
        } else defValue
    }

    fun putFloat(context: Context?, key: String?, value: Float) {
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        preferences.edit().putFloat(key, value).apply()
    }

    fun getFloat(context: Context?, key: String?, defValue: Float): Float {
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        return if (preferences.contains(key)) {
            preferences.getFloat(key, defValue)
        } else defValue
    }

    fun clearData(context: Context?) {
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        preferences.edit().clear().apply()
    }
}