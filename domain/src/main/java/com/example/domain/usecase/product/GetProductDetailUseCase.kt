package com.example.domain.usecase.product

import com.example.domain.model.Product
import com.example.domain.repository.ProductDetailRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProductDetailUseCase @Inject constructor(
    private val productDetailRepository: ProductDetailRepository
) {
    operator fun invoke(productId: String) : Flow<Product> {
        return productDetailRepository.getProductDetail(productId)
    }
}