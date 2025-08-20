package com.example.data.repository

import com.example.data.datasource.ProductDataSource
import com.example.data.db.dao.LikeDao
import com.example.data.db.entity.toLikeProductEntity
import com.example.domain.model.BaseModel
import com.example.domain.model.Carousel
import com.example.domain.model.Product
import com.example.domain.model.Ranking
import com.example.domain.repository.MainRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    private val productDataSource: ProductDataSource,
    private val likeDao: LikeDao
) : MainRepository {
    override fun getModelList(): Flow<List<BaseModel>> {
        return productDataSource.getHomeComponents().combine(likeDao.getAll()) { homeComponents, likeList ->
            homeComponents.map { model -> mappingLike(model, likeList.map { it.productId }) }
        }

    }

    override suspend fun likeProduct(product: Product) {
        if (product.isLike) {
            likeDao.delete(product.productId)
        } else {
            likeDao.insert(product.toLikeProductEntity().copy(isLike = true))
        }
    }

    private fun mappingLike(baseModel: BaseModel, likeProductIds: List<String>) : BaseModel {
        return when(baseModel) {
            is Carousel -> { baseModel.copy(productList = baseModel.productList.map { updateLikeStatus(it, likeProductIds) }) }
            is Ranking -> { baseModel.copy(productList = baseModel.productList.map { updateLikeStatus(it, likeProductIds) }) }
            is Product -> { updateLikeStatus(baseModel, likeProductIds) }
            else -> baseModel
        }
    }

    private fun updateLikeStatus(product: Product, likeProductIds: List<String>): Product {
        return product.copy(isLike = likeProductIds.contains(product.productId))
    }
}