package com.example.domain.usecase.category

import com.example.domain.model.Category
import com.example.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCategoryUseCase @Inject constructor(private val repository: CategoryRepository) {
    fun getCategories() : Flow<List<Category>> {
        return repository.getCategories()
    }
}