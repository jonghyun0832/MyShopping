package com.example.domain.usecase.basket

import com.example.domain.model.BasketProduct
import com.example.domain.repository.BasketRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBasketProductsUseCase @Inject constructor(
    private val basketRepository: BasketRepository
) {
    operator fun invoke() : Flow<List<BasketProduct>> {
        return basketRepository.getBasketProducts()
    }
}