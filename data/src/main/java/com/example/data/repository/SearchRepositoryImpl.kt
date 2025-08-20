package com.example.data.repository

import com.example.data.datasource.ProductDataSource
import com.example.data.db.dao.LikeDao
import com.example.data.db.dao.SearchDao
import com.example.data.db.entity.SearchKeywordEntity
import com.example.data.db.entity.toDomain
import com.example.data.db.entity.toLikeProductEntity
import com.example.domain.model.Product
import com.example.domain.model.SearchFilter
import com.example.domain.model.SearchKeyword
import com.example.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import kotlin.collections.map

class SearchRepositoryImpl @Inject constructor(
    private val dataSource: ProductDataSource,
    private val searchDao: SearchDao,
    private val likeDao: LikeDao
) : SearchRepository {
    override suspend fun search(
        searchKeyword: SearchKeyword,
        filters: List<SearchFilter>
    ): Flow<List<Product>> {
        searchDao.insert(SearchKeywordEntity(searchKeyword.keyword))
        return dataSource.getProducts().map { list ->
            list.filter {
                isAvailableProduct(
                    product = it,
                    searchKeyword = searchKeyword,
                    filters = filters
                )
            }
        }.combine(likeDao.getAll()) { products, likeList ->
            products.map { product -> updateLikeStatus(product, likeList.map { it.productId }) }
        }
    }

    private fun isAvailableProduct(
        product: Product,
        searchKeyword: SearchKeyword,
        filters: List<SearchFilter>
    ): Boolean {
        var isAvailable = true
        filters.forEach { filter ->
            isAvailable = isAvailable && filter.isAvailableProduct(product)
        }

        return isAvailable && product.productName.contains(searchKeyword.keyword)
    }

    override fun getSearchKeywords(): Flow<List<SearchKeyword>> {
        return searchDao.getAll().map { it.map { entity -> entity.toDomain() } }
    }

    override suspend fun likeProduct(product: Product) {
        if (product.isLike) {
            likeDao.delete(product.productId)
        } else {
            likeDao.insert(product.toLikeProductEntity())
        }
    }

    private fun updateLikeStatus(product: Product, likeProductIds: List<String>): Product {
        return product.copy(isLike = likeProductIds.contains(product.productId))
    }

}