package com.example.products.ui.navigation

sealed class Screens(var route: String, var title: String) {

    object ProductsScreen : Screens("/productsScreen", "ProductsScreen")

    object ProductDetailsScreen : Screens("/productDetailsScreen", "ProductDetailsScreen")


}