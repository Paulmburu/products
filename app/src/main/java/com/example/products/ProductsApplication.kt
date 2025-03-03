package com.example.products

import android.app.Application
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class ProductsApplication : Application() {

    companion object {
        lateinit var appInstance: ProductsApplication private set
    }

    override fun onCreate() {
        super.onCreate()
        appInstance = this
    }
}