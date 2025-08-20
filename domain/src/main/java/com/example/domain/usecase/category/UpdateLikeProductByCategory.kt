package com.example.domain.usecase.category

import com.example.domain.model.Product
import com.example.domain.repository.CategoryRepository
import javax.inject.Inject

class UpdateLikeProductByCategory @Inject constructor(
    private val categoryRepository: CategoryRepository
) {
    suspend operator fun invoke(product: Product) {
        categoryRepository.likeProduct(product)
    }
}