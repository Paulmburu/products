package com.example.products.ui.navigation


import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.products.ui.screens.ProductDetailsScreenRoute
import com.example.products.ui.screens.ProductsScreenRoute
import com.example.products.ui.viewmodel.ProductViewModel

@Composable
fun Navigation(
    navController: NavHostController,
) {

    val viewModel = hiltViewModel<ProductViewModel>()
    NavHost(navController, startDestination = Screens.ProductsScreen.route) {
        composable(Screens.ProductsScreen.route) {
            ProductsScreenRoute(viewModel = viewModel, navigateToProductDetails = {
                navController.navigate(Screens.ProductDetailsScreen.route)
            })
        }

        composable(Screens.ProductDetailsScreen.route) {
            ProductDetailsScreenRoute(
                viewModel = viewModel,
                navigateBack = { navController.navigateUp() })
        }

    }
}