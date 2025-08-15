package com.example.domain.repository

import com.example.domain.model.Category
import com.example.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    fun getCategories(): Flow<List<Category>>
    fun getProductsByCategory(category: Category) : Flow<List<Product>>
}