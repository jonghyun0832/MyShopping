package com.example.domain.usecase.search

import com.example.domain.model.Product
import com.example.domain.repository.SearchRepository
import javax.inject.Inject

class UpdateLikeProductBySearch @Inject constructor(
    private val searchRepository: SearchRepository
) {
    suspend operator fun invoke(product: Product) {
        searchRepository.likeProduct(product)
    }
}