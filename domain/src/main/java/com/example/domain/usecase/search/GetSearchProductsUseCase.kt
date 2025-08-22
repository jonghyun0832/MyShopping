package com.example.domain.usecase.search

import com.example.domain.model.Product
import com.example.domain.model.SearchFilter
import com.example.domain.model.SearchKeyword
import com.example.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetSearchProductsUseCase @Inject constructor(
    private val searchRepository: SearchRepository
) {
    suspend operator fun invoke(searchKeyword: SearchKeyword, filters: List<SearchFilter>) : Flow<List<Product>> {
        return searchRepository.search(searchKeyword).map { list ->
            list.filter {
                isAvailableProduct(
                    product = it,
                    searchKeyword = searchKeyword,
                    filters = filters
                )
            }
        }
    }

    private fun isAvailableProduct(
        product: Product,
        searchKeyword: SearchKeyword,
        filters: List<SearchFilter>
    ): Boolean {
        var isAvailable = true
        filters.forEach { filter ->
            isAvailable = isAvailable && filter.isAvailableProduct(product)
        }

        return isAvailable && product.productName.contains(searchKeyword.keyword)
    }
}