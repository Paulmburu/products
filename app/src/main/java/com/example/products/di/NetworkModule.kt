package com.example.products.di


import android.content.Context
import com.example.products.data.remote.CreateAuthInterceptor
import com.example.products.data.remote.ProductApiService
import com.example.products.utils.ConnectivityProvider
import com.example.products.utils.Contants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun providesConnectionProvider(@ApplicationContext context: Context): ConnectivityProvider {
        return ConnectivityProvider(context)
    }

    @Provides
    fun providesOkhttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY

//                when (BuildConfig.BUILD_TYPE) {
//                "release" -> HttpLoggingInterceptor.Level.NONE
//                else -> {
//                    HttpLoggingInterceptor.Level.BODY
//                }
//            }
        }

        val authInterceptor = CreateAuthInterceptor()

        return OkHttpClient.Builder()
            .connectTimeout(60L, TimeUnit.SECONDS)
            .readTimeout(60L, TimeUnit.SECONDS)
            .addInterceptor(authInterceptor)
            .addInterceptor(loggingInterceptor)
            .build()

    }

    @Provides
    fun providesRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    }

    @Provides
    fun providesWeatherApi(retrofit: Retrofit): ProductApiService {
        return retrofit.create(ProductApiService::class.java)
    }

}

