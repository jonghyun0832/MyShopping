package com.example.domain.repository

import com.example.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface LikeRepository {
    fun getLikeProduct(): Flow<List<Product>>
}