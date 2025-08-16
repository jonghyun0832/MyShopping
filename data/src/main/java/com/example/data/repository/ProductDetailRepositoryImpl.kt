package com.example.data.repository

import com.example.data.datasource.ProductDataSource
import com.example.domain.model.Product
import com.example.domain.repository.ProductDetailRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ProductDetailRepositoryImpl @Inject constructor(
    private val dataSource: ProductDataSource
) : ProductDetailRepository {
    override fun getProductDetail(productId: String): Flow<Product> {
        return dataSource.getProducts().map { products ->
            products.filterIsInstance<Product>().first { it.productId == productId }
        }
    }
}