package com.example.data.repository

import com.example.data.db.dao.BasketDao
import com.example.data.db.entity.toDomainModel
import com.example.domain.model.BasketProduct
import com.example.domain.model.Product
import com.example.domain.repository.BasketRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class BasketRepositoryImpl @Inject constructor(
    private val basketDao: BasketDao
) : BasketRepository {
    override fun getBasketProducts(): Flow<List<BasketProduct>> {
        return basketDao.getAll().map { list ->
            list.map { BasketProduct(it.toDomainModel(), it.count) }
        }
    }

    override suspend fun removeBasketProduct(product: Product) {
        basketDao.delete(product.productId)
    }
}