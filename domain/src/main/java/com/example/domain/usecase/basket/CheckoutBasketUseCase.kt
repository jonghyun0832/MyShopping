package com.example.domain.usecase.basket

import com.example.domain.model.BasketProduct
import com.example.domain.repository.BasketRepository
import javax.inject.Inject

class CheckoutBasketUseCase @Inject constructor(
    private val basketRepository: BasketRepository
) {
    suspend operator fun invoke(products: List<BasketProduct>) {
        basketRepository.checkoutBasket(products)
    }
}