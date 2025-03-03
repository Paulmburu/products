package com.example.products.storage

import android.content.Context
import android.content.SharedPreferences
import com.example.products.utils.DeviceUtils
import com.example.products.ProductsApplication
import com.google.android.datatransport.BuildConfig
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken



const val PREF_SESSION_ID = "PREF_SESSION_ID"


class PersistenceProvider(
    context: Context
) {
    private val PREF_FILE =
        ProductsApplication.appInstance.packageName + "_" + BuildConfig.BUILD_TYPE + "_" + DeviceUtils.deviceId()

    val prefs: SharedPreferences = context.getSharedPreferences(PREF_FILE, 0)

    var sessionId: String?
        get() = prefs.getString(PREF_SESSION_ID, "")
        set(value) = prefs.edit().putString(PREF_SESSION_ID, value).apply()



    fun <T> set(obj: T, key: String) {
        //Convert object to JSON String.
        val jsonString = GsonBuilder().create().toJson(obj)
        //Save that String in SharedPreferences
        prefs.edit().putString(key, jsonString).apply()

    }

    /**
     * Used to retrieve object from the Preferences.
     *
     * @param key Shared Preference key with which object was saved.
     **/
    inline fun <reified T> get(key: String): T? {
        //We read JSON String which was saved.
        val value = prefs.getString(key, null)
        //JSON String was found which means object can be read.
        //We convert this JSON String to model object. Parameter "c" (of
        //type Class < T >" is used to cast.
        return GsonBuilder().create().fromJson(value, T::class.java)
    }

    inline fun <reified T> list(key: String): List<T>? {
        //We read JSON String which was saved.
        val value = prefs.getString(key, null)
        //JSON String was found which means object can be read.
        //We convert this JSON String to model object. Parameter "c" (of
        //type Class < T >" is used to cast.
        return GsonBuilder().create().fromJson(value, object : TypeToken<List<T>>() {}.type)
    }


    fun clearPref() {
        prefs.edit().clear().apply()
        // TODO: This can be alternatively implemented as below to retains state for specific fields
        // val fcmValue = fcm // Get the current value of fcm

        // prefs.edit().clear().apply() // Clear all data in SharedPreferences
    }
}