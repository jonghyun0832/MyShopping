package com.example.domain.repository

import com.example.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface MainRepository {
    fun getProductList(): Flow<List<Product>>
}