package com.example.products.data.remote

import com.example.products.domain.model.ProductListResponse

import retrofit2.http.GET
import retrofit2.http.Query

interface ProductApiService {

    @GET("products")
    suspend fun getProducts(
        @Query("limit") limit: Int, @Query("skip") skip: Int
    ): ProductListResponse


    @GET("products")
    suspend fun searchProducts(
        @Query("q") query: String, @Query("limit") limit: Int, @Query("skip") skip: Int,
    ): ProductListResponse

}