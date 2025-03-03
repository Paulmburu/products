package com.example.products.utils

import android.os.Build
import java.util.Locale
import android.provider.Settings
import com.example.products.ProductsApplication.Companion.appInstance
import java.util.UUID


object DeviceUtils {
    fun deviceLanguage() = Locale.getDefault().language

    fun deviceId() = Settings.Secure.getString(appInstance.contentResolver, Settings.Secure.ANDROID_ID) //default //"mburu.paul@proton.me" // "xisevi@afia.pro"//"830c5888487@cringemonster.com"//"digali5044@cdeter.com"//"zuzolyve@afia.pro"//"kabigy@lyft.live"


    fun deviceInfo() =
        "${Build.MANUFACTURER} ${Build.MODEL} ${Build.VERSION.RELEASE} ${Build.VERSION_CODES::class.java.fields[Build.VERSION.SDK_INT].name}"

    fun deviceType() = "ANDROID"

    fun secureRandom() = UUID.randomUUID().toString()

}