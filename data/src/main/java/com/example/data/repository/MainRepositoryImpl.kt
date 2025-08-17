package com.example.data.repository

import com.example.data.datasource.ProductDataSource
import com.example.domain.model.BaseModel
import com.example.domain.repository.MainRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    private val productDataSource: ProductDataSource
) : MainRepository {
    override fun getModelList(): Flow<List<BaseModel>> {
        return productDataSource.getHomeComponents()
    }
}