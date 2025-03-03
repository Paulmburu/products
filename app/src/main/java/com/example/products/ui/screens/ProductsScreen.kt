package com.example.products.ui.screens

//import androidx.compose.foundation.lazy.items

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.products.ui.components.ProductAppBar
import com.example.products.ui.components.ProductItem
import com.example.products.ui.viewmodel.ProductViewModel

@Composable
fun ProductsScreenRoute(
    viewModel: ProductViewModel,
    navigateToProductDetails: () -> Unit = {},
) {
    ProductsScreen(
        viewModel = viewModel,
        navigateToProductDetails = navigateToProductDetails,
    )
}


@Composable
fun ProductsScreen(
    viewModel: ProductViewModel,
    navigateToProductDetails: () -> Unit = {},
) {
    val searchQuery by viewModel.searchQuery.collectAsState()
    var isSearchActive by remember { mutableStateOf(false) }
    val selectedProduct by viewModel.selectedProduct.observeAsState()

    LaunchedEffect(selectedProduct) {
        selectedProduct?.let {
            navigateToProductDetails()
            viewModel.clearSelectedProduct()
        }
    }


    Scaffold(topBar = {
        ProductAppBar(isSearchActive = isSearchActive,
            searchQuery = searchQuery,
            onQueryChange = { viewModel.searchProducts(it) },
            onSearchToggle = { isSearchActive = !isSearchActive })
    }) { paddingValues ->
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)) {
            val products = viewModel.productList.collectAsLazyPagingItems()
            val isInitialLoading =
                products.loadState.refresh is LoadState.Loading && products.itemCount == 0
            val isInitialError = products.loadState.refresh is LoadState.Error



            when {
                isInitialLoading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }

                isInitialError -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(
                            text = "Failed to load products. Please try again.",
                            textAlign = TextAlign.Center
                        )
                    }
                }

                else -> {
                    LazyColumn {
                        items(products.itemCount) { index ->
                            products[index]?.let {
                                ProductItem(product = it, onClick = { clickedProduct ->
                                    viewModel.selectProduct(clickedProduct)
                                    navigateToProductDetails.invoke()
                                })
                            }
                        }
                    }
                }
            }
        }
    }
}

