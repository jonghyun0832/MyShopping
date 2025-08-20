package com.example.domain.usecase.product

import com.example.domain.model.Product
import com.example.domain.repository.ProductDetailRepository
import javax.inject.Inject

class AddBasketUseCase @Inject constructor(
    private val productDetailRepository: ProductDetailRepository
) {
    suspend operator fun invoke(product: Product) {
        productDetailRepository.addBasket(product)
    }
}