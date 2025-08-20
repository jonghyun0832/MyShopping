package com.example.domain.usecase.category

import com.example.domain.model.Category
import com.example.domain.model.Product
import com.example.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProductsByCategoryUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository
) {
    suspend operator fun invoke(category: Category): Flow<List<Product>> {
        return categoryRepository.getProductsByCategory(category)
    }
}