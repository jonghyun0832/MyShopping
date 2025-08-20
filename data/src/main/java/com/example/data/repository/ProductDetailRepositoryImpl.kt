package com.example.data.repository

import com.example.data.datasource.ProductDataSource
import com.example.data.db.dao.BasketDao
import com.example.data.db.entity.toBasketProductEntity
import com.example.domain.model.Product
import com.example.domain.repository.ProductDetailRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ProductDetailRepositoryImpl @Inject constructor(
    private val dataSource: ProductDataSource,
    private val basketDao: BasketDao
) : ProductDetailRepository {
    override fun getProductDetail(productId: String): Flow<Product> {
        return dataSource.getHomeComponents().map { products ->
            products.filterIsInstance<Product>().first { it.productId == productId }
        }
    }

    override suspend fun addBasket(product: Product) {
        val alreadyExistProduct = basketDao.get(product.productId)
        if (alreadyExistProduct == null) {
            basketDao.insert(product.toBasketProductEntity())
        } else {
            basketDao.insert(alreadyExistProduct.copy(count = alreadyExistProduct.count + 1))
        }
    }
}