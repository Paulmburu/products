package com.example.products.data.repository

import com.example.products.data.local.database.AppDatabase
import com.example.products.data.local.dao.ProductDao
import com.example.products.data.remote.ProductApiService
import com.example.products.data.local.entities.ProductEntity
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import androidx.paging.*
import org.junit.Assert.assertNotNull



@OptIn(ExperimentalPagingApi::class, ExperimentalCoroutinesApi::class)
class ProductRepositoryTest {

    @get:Rule
    val dispatcherRule = TestCoroutineRule()

    private lateinit var repository: ProductRepository
    private val database: AppDatabase = mockk(relaxed = true)
    private val productDao: ProductDao = mockk(relaxed = true)
    private val apiService: ProductApiService = mockk(relaxed = true)

    @Before
    fun setup() {
        every { database.productDao() } returns productDao
        repository = ProductRepository(database, apiService)
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `test getProducts returns PagingData`() = runBlocking {
        val mockProduct = ProductEntity(1, "Test Product", "Test Description", 100.0, "url")
        val pagingSource = FakePagingSource(listOf(mockProduct))

        every { productDao.getAllProducts() } returns pagingSource

        val result = repository.getProducts().first()
        assertNotNull(result)
    }

    @Test
    fun `test searchProducts returns PagingData`() = runBlocking {
        val query = "test"
        val mockProduct = ProductEntity(2, "Search Product", "Description", 50.0, "url")
        val pagingSource = FakePagingSource(listOf(mockProduct))

        every { productDao.searchProducts(query) } returns pagingSource

        val result = repository.searchProducts(query).first()
        assertNotNull(result)
    }
}

class FakePagingSource<T : Any>(private val data: List<T>) : PagingSource<Int, T>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> {
        return LoadResult.Page(data, prevKey = null, nextKey = null)
    }
    override fun getRefreshKey(state: PagingState<Int, T>): Int? = null
}
