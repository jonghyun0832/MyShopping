package com.example.domain.repository

import com.example.domain.model.BasketProduct
import com.example.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface BasketRepository {
    fun getBasketProducts() : Flow<List<BasketProduct>>
    suspend fun removeBasketProduct(product: Product)
}