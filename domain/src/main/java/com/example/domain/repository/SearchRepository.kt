package com.example.domain.repository

import com.example.domain.model.Product
import com.example.domain.model.SearchFilter
import com.example.domain.model.SearchKeyword
import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    suspend fun search(searchKeyword: SearchKeyword): Flow<List<Product>>

    fun getSearchKeywords() : Flow<List<SearchKeyword>>
    suspend fun likeProduct(product: Product)
}