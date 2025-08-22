@file:Suppress("NonAsciiCharacters")

package com.example.domain.usecase

import app.cash.turbine.test
import com.example.domain.model.Category
import com.example.domain.model.Product
import com.example.domain.model.SearchFilter
import com.example.domain.model.SearchKeyword
import com.example.domain.repository.SearchRepository
import com.example.domain.usecase.search.GetSearchKeywordsUseCase
import com.example.domain.usecase.search.GetSearchProductsUseCase
import com.example.domain.usecase.search.UpdateLikeProductBySearch
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class SearchUseCaseTest {
    private lateinit var getSearchProductsUseCase: GetSearchProductsUseCase
    private lateinit var updateLikeProductBySearch: UpdateLikeProductBySearch
    private lateinit var getSearchKeywordsUseCase: GetSearchKeywordsUseCase

    @Mock
    private lateinit var searchRepository: SearchRepository

    @Mock
    private lateinit var topProduct: Product

    @Mock
    private lateinit var dressProduct: Product

    @Mock
    private lateinit var pantsProduct: Product

    private lateinit var searchResponse: List<Product>

    private lateinit var autoCloseable: AutoCloseable

    private val searchKeyword = SearchKeyword("1")

    @Before
    fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher())
        autoCloseable = MockitoAnnotations.openMocks(this)

        `when`(topProduct.category).thenReturn(Category.Top)
        `when`(dressProduct.category).thenReturn(Category.Dress)
        `when`(pantsProduct.category).thenReturn(Category.Pants)

        `when`(topProduct.productName).thenReturn("상의1")
        `when`(dressProduct.productName).thenReturn("드레스1")
        `when`(pantsProduct.productName).thenReturn("바지1")

        searchResponse = listOf(topProduct, dressProduct, pantsProduct)

        getSearchProductsUseCase = GetSearchProductsUseCase(searchRepository)
        getSearchKeywordsUseCase = GetSearchKeywordsUseCase(searchRepository)
        updateLikeProductBySearch = UpdateLikeProductBySearch(searchRepository)
    }

    @After
    fun close() {
        Dispatchers.resetMain()
        autoCloseable.close()
    }

    @Test
    fun `검색 호출 테스트`() = runTest {
        // 1) Stub: 리포지토리가 Flow를 반환한다고 가정
        whenever(searchRepository.getSearchKeywords())
            .thenReturn(flowOf(listOf(searchKeyword)))

        // 2) 실제 호출로 트리거
        val result = getSearchKeywordsUseCase().first()

        // 3) 검증
        assertEquals(listOf(searchKeyword), result)
        verify(searchRepository).getSearchKeywords()
    }

    @Test
    fun `상의 필터 검색 테스트`() = runTest {
        whenever(searchRepository.search(searchKeyword)).thenReturn(flowOf(searchResponse))
        getSearchProductsUseCase(
            searchKeyword, listOf(
                SearchFilter.CategoryFilter(
                    listOf(),
                    Category.Top
                )
            )
        ).test {
            assertThat(awaitItem()).isEqualTo(listOf(topProduct))
            awaitComplete()
        }
    }

    @Test
    fun `드레스 필터 검색 테스트`() = runTest {
        whenever(searchRepository.search(searchKeyword)).thenReturn(flowOf(searchResponse))
        getSearchProductsUseCase(
            searchKeyword, listOf(
                SearchFilter.CategoryFilter(
                    listOf(),
                    Category.Dress
                )
            )
        ).test {
            assertThat(awaitItem()).isEqualTo(listOf(dressProduct))
            awaitComplete()
        }
    }

    @Test
    fun `검색어 테스트`() = runTest {
        val searchKeyword = SearchKeyword("상의")
        whenever(searchRepository.search(searchKeyword)).thenReturn(flowOf(searchResponse))

        getSearchProductsUseCase(searchKeyword, listOf()).test {
            assertThat(awaitItem()).isEqualTo(listOf(topProduct))
            awaitComplete()
        }
    }
}