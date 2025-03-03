package com.example.products.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.products.data.local.database.AppDatabase
import com.example.products.data.local.entities.ProductEntity
import com.example.products.data.paging.ProductRemoteMediator
import com.example.products.data.paging.SearchProductRemoteMediator
import com.example.products.data.remote.ProductApiService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductRepository @Inject constructor(
    private val database: AppDatabase,
    private val apiService: ProductApiService
) {
    @OptIn(ExperimentalPagingApi::class)
    fun getProducts(): Flow<PagingData<ProductEntity>> {
        return Pager(
            config = PagingConfig(pageSize = 30, enablePlaceholders = false),
            remoteMediator = ProductRemoteMediator(database, apiService),
            pagingSourceFactory = { database.productDao().getAllProducts() }
        ).flow
    }

    @OptIn(ExperimentalPagingApi::class)
    fun searchProducts(query: String): Flow<PagingData<ProductEntity>> {
        val pagingSourceFactory = { database.productDao().searchProducts(query) }

        return Pager(
            config = PagingConfig(pageSize = 30, enablePlaceholders = false),
            remoteMediator = SearchProductRemoteMediator(database, apiService, query),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }
}
