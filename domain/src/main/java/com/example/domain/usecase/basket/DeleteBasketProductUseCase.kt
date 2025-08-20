package com.example.domain.usecase.basket

import com.example.domain.model.BasketProduct
import com.example.domain.model.Product
import com.example.domain.repository.BasketRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeleteBasketProductUseCase @Inject constructor(
    private val basketRepository: BasketRepository
) {
    suspend operator fun invoke(product: Product) {
        return basketRepository.removeBasketProduct(product)
    }
}