package com.example.domain.repository

import com.example.domain.model.BaseModel
import com.example.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface MainRepository {
    fun getModelList(): Flow<List<BaseModel>>
    suspend fun likeProduct(product: Product)
}