package com.example.data.repository

import com.example.data.datasource.ProductDataSource
import com.example.data.db.dao.SearchDao
import com.example.data.db.entity.SearchKeywordEntity
import com.example.data.db.entity.toDomain
import com.example.domain.model.Product
import com.example.domain.model.SearchFilter
import com.example.domain.model.SearchKeyword
import com.example.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val dataSource: ProductDataSource,
    private val searchDao: SearchDao
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

}