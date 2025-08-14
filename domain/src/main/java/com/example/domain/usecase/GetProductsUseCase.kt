package com.example.domain.usecase

import com.example.domain.model.Product
import com.example.domain.repository.MainRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProductsUseCase @Inject constructor(private val mainRepository: MainRepository) {
    fun getProducts() : Flow<List<Product>> {
        return mainRepository.getProductList()
    }
}