package com.example.products.storage

import android.content.Context
import android.content.SharedPreferences
import com.example.products.utils.DeviceUtils
import com.example.products.ProductsApplication
import com.google.android.datatransport.BuildConfig
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken

//class PreferencesManager(context: Context) {
//    private val sharedPreferences: SharedPreferences =
//        context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
//
//    fun saveData(key: String, value: String) {
//        val editor = sharedPreferences.edit()
//        editor.putString(key, value)
//        editor.apply()
//    }
//
//    fun getData(key: String, defaultValue: String): String {
//        return sharedPreferences.getString(key, defaultValue) ?: defaultValue
//    }
//}

const val PREF_SESSION_ID = "PREF_SESSION_ID"
const val PREF_CARD_INFO = "PREF_CARD_INFO"
const val PREF_GROUPED_PRODUCTS = "PREF_GROUPED_PRODUCTS"
const val PREF_SELECTED_PRODUCT = "PREF_SELECTED_PRODUCT"
const val PREF_SELECTED_PRODUCT_CODE = "PREF_SELECTED_PRODUCT_CODE"
const val PREF_MASTER_TMK = "PREF_MASTER_TMK"
const val PREF_TERMINAL_TPK = "PREF_TERMINAL_TPK"
const val PREF_MASTER_KEY_COUNTRY_CODE = "PREF_MASTER_KEY_COUNTRY_CODE"
const val PREF_CREATE_USER_USERNAME = "PREF_CREATE_USER_USERNAME"
const val PREF_CREATE_USER_PIN = "PREF_CREATE_USER_PIN"
const val PREF_CURRENT_USER_PIN = "PREF_CURRENT_USER_PIN"
const val PREF_SELECTED_USER = "PREF_SELECTED_USER"
const val PREF_PRODUCT_AMOUNT = "PREF_PRODUCT_AMOUNT"
const val PREF_SELECTED_ITEM = "PREF_SELECTED_ITEM"
const val PREF_RFID = "PREF_RFID"
const val PREF_VEHICLE_REG_NO = "PREF_VEHICLE_REG_NO"
const val PREF_ODOMETER = "PREF_ODOMETER"
const val PREF_HAS_FETCHED_BN_CARD_INFO = "PREF_HAS_FETCHED_BN_CARD_INFO"
const val PREF_PRINTED_BN = "PREF_PRINTED_BN"
const val PREF_TOP_UP_AMOUNT = "PREF_TOP_UP_AMOUNT"
const val PREF_ACTIVATION_PHONE = "PREF_ACTIVATION_PHONE"
const val PREF_SAVED_PREF = "PREF_SAVED_PREF"
const val PREF_SAVED_PUMP_NUMBER = "PREF_SAVED_PUMP_NUMBER"


class PersistenceProvider(
    context: Context
) {
    private val PREF_FILE =
        ProductsApplication.appInstance.packageName + "_" + BuildConfig.BUILD_TYPE + "_" + DeviceUtils.deviceId()

    val prefs: SharedPreferences = context.getSharedPreferences(PREF_FILE, 0)

    var sessionId: String?
        get() = prefs.getString(PREF_SESSION_ID, "")
        set(value) = prefs.edit().putString(PREF_SESSION_ID, value).apply()


    var selectedProducts: List<String>?
        get() = get(PREF_SELECTED_PRODUCT)
        set(value) = set(value, PREF_SELECTED_PRODUCT)

    var selectedProductCode: List<String>?
        get() = get(PREF_SELECTED_PRODUCT_CODE)
        set(value) = set(value, PREF_SELECTED_PRODUCT_CODE)

    var masterTmk: String?
        get() = get(PREF_MASTER_TMK)
        set(value) = set(value, PREF_MASTER_TMK)

    var masterKeyCountryCode: String?
        get() = get(PREF_MASTER_KEY_COUNTRY_CODE)
        set(value) = set(value, PREF_MASTER_KEY_COUNTRY_CODE)

    var terminalTpk: String?
        get() = get(PREF_TERMINAL_TPK)
        set(value) = set(value, PREF_TERMINAL_TPK)


    // Create User
    var newUsername: String?
        get() = get(PREF_CREATE_USER_USERNAME)
        set(value) = set(value, PREF_CREATE_USER_USERNAME)

    var createUserPIN: String?
        get() = get(PREF_CREATE_USER_PIN)
        set(value) = set(value, PREF_CREATE_USER_PIN)

    var currentUserPIN: String?
        get() = get(PREF_CURRENT_USER_PIN)
        set(value) = set(value, PREF_CURRENT_USER_PIN)

    var selectedUser: String?
        get() = get(PREF_SELECTED_USER)
        set(value) = set(value, PREF_SELECTED_USER)

    var productAmount: String?
        get() = get(PREF_PRODUCT_AMOUNT)
        set(value) = set(value, PREF_PRODUCT_AMOUNT)

    var selectedItem: String?
        get() = get(PREF_SELECTED_ITEM)
        set(value) = set(value, PREF_SELECTED_ITEM)


    var serialNO: String?
        get() = get(PREF_RFID)
        set(value) = set(value, PREF_RFID)

    var vehicleRegNo: String?
        get() = get(PREF_VEHICLE_REG_NO)
        set(value) = set(value, PREF_VEHICLE_REG_NO)

    var odometer: String?
        get() = get(PREF_ODOMETER)
        set(value) = set(value, PREF_ODOMETER)

    var hasFetchedBNCardInfo: Boolean?
        get() = get(PREF_HAS_FETCHED_BN_CARD_INFO)
        set(value) = set(value, PREF_HAS_FETCHED_BN_CARD_INFO)

    var hasPrintedBN: Boolean?
        get() = get(PREF_PRINTED_BN)
        set(value) = set(value, PREF_PRINTED_BN)

    var topUpAmount: String?
        get() = get(PREF_TOP_UP_AMOUNT)
        set(value) = set(value, PREF_TOP_UP_AMOUNT)

    var activationPhone: String?
        get() = get(PREF_ACTIVATION_PHONE)
        set(value) = set(value, PREF_ACTIVATION_PHONE)

    var savedRef: String?
        get() = get(PREF_SAVED_PREF)
        set(value) = set(value, PREF_SAVED_PREF)


    var pumpNumber: String?
        get() = get(PREF_SAVED_PUMP_NUMBER)
        set(value) = set(value, PREF_SAVED_PUMP_NUMBER)

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