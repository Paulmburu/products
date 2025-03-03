package com.example.products.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.products.data.local.entities.ProductEntity


@Dao
interface ProductDao {
    @Query("SELECT * FROM products")
    fun getAllProducts(): PagingSource<Int, ProductEntity>

    @Query("SELECT * FROM products WHERE title LIKE '%' || :query || '%'")
    fun searchProducts(query: String): PagingSource<Int, ProductEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(products: List<ProductEntity>)

    @Query("DELETE FROM products")
    fun clearAll(): Int
}
