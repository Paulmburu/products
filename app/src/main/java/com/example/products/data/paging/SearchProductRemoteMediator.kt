package com.example.products.data.paging


import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.example.products.data.local.database.AppDatabase
import com.example.products.data.local.entities.ProductEntity
import com.example.products.data.remote.ProductApiService
import androidx.room.withTransaction
@OptIn(ExperimentalPagingApi::class)
class SearchProductRemoteMediator(
    private val database: AppDatabase,
    private val apiService: ProductApiService,
    private val query: String // New search parameter
) : RemoteMediator<Int, ProductEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ProductEntity>
    ): MediatorResult {
        return try {
            val page = when (loadType) {
                LoadType.REFRESH -> 0
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val itemCount = state.pages.sumOf { it.data.size }
                    itemCount / state.config.pageSize // Correctly calculate next page
                }
            }

            val response = apiService.searchProducts(query, state.config.pageSize, page * state.config.pageSize)

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    database.productDao().clearAll()
                }
                val products = response.products?.map {
                    ProductEntity(it.id ?: 0, it.title.orEmpty(), it.description.orEmpty(), it.price ?: 0.0, it.thumbnail.orEmpty())
                } ?: emptyList()
                database.productDao().insertAll(products)
            }

            MediatorResult.Success(endOfPaginationReached = response.products.isNullOrEmpty())
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }
}
