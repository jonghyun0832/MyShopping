package com.example.domain.usecase

import com.example.domain.model.Product
import com.example.domain.model.SearchKeyword
import com.example.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSearchProductsUseCase @Inject constructor(
    private val searchRepository: SearchRepository
) {
    suspend operator fun invoke(searchKeyword: SearchKeyword) : Flow<List<Product>> {
        return searchRepository.search(searchKeyword)
    }
}