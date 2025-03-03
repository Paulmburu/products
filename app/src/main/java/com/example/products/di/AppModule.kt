package com.example.products.di

import android.content.Context
import androidx.room.Room
import com.example.products.data.local.dao.ProductDao
import com.example.products.data.local.database.AppDatabase
import com.example.products.data.remote.ProductApiService
import com.example.products.data.repository.ProductRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "app_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideProductDao(database: AppDatabase): ProductDao = database.productDao()

    @Provides
    @Singleton
    fun provideRepository(database: AppDatabase, apiService: ProductApiService): ProductRepository {
        return ProductRepository(database, apiService)
    }


}
