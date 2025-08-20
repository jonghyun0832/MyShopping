package com.example.data.repository

import com.example.data.db.dao.LikeDao
import com.example.data.db.entity.toDomainModel
import com.example.domain.model.Product
import com.example.domain.repository.LikeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LikeRepositoryImpl @Inject constructor(
    private val likeDao: LikeDao
) : LikeRepository {
    override fun getLikeProduct(): Flow<List<Product>> {
        return likeDao.getAll().map { entities ->
            entities.map { entity ->
                entity.toDomainModel()
            }
        }
    }
}