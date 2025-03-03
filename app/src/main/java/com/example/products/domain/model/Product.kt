package com.example.products.domain.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ProductListResponse(
    val products: List<ProductDTO>?,
    val total: Int?,
    val skip: Int?,
    val limit: Int?
)

@JsonClass(generateAdapter = true)
data class ProductDTO(
    val id: Int?,
    val title: String?,
    val description: String?,
    val price: Double?,
    val images: List<String>?,
    val thumbnail: String?,
)