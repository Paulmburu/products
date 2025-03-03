package com.example.products.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.products.data.local.entities.ProductEntity
import com.example.products.data.remote.ProductApiService
import com.example.products.data.repository.ProductRepository
import com.example.products.utils.ConnectivityProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject


@HiltViewModel
class ProductViewModel @Inject constructor(
    private val apiService: ProductApiService,
    connectivityProvider: ConnectivityProvider,
    private val repository: ProductRepository,
) : ViewModel() {

    private val _selectedProduct = MutableLiveData<ProductEntity?>()
    val selectedProduct: LiveData<ProductEntity?> get() = _selectedProduct

    fun selectProduct(product: ProductEntity) {
        _selectedProduct.postValue(product)
    }

    fun clearSelectedProduct() {
        _selectedProduct.postValue(null)
    }

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    val productList = _searchQuery.flatMapLatest { query ->
        if (query.isEmpty()) {
            repository.getProducts()
        } else {
            repository.searchProducts(query)
        }
    }.cachedIn(viewModelScope)

    fun searchProducts(query: String) {
        _searchQuery.value = query
    }

}