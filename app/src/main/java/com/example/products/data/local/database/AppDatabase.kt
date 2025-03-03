package com.example.products.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.products.data.local.dao.ProductDao
import com.example.products.data.local.entities.ProductEntity

@Database(entities = [ProductEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
}

