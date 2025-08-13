package com.example.domain.usecase

import com.example.domain.model.Product
import com.example.domain.repository.MainRepository
import javax.inject.Inject

class GetProductsUseCase @Inject constructor(private val mainRepository: MainRepository) {
    fun getProducts() : List<Product> {
        return mainRepository.getProductList()
    }
}