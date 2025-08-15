package com.example.data.repository

import android.content.Context
import com.example.data.datasource.ProductDataSource
import com.example.data.deserializer.BaseModelDeserializer
import com.example.domain.model.BaseModel
import com.example.domain.model.Product
import com.example.domain.repository.MainRepository
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.InputStreamReader
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    private val productDataSource: ProductDataSource
) : MainRepository {
    override fun getModelList(): Flow<List<BaseModel>> {
        return productDataSource.getProducts()
    }
}