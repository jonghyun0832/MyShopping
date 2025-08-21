package com.example.data.repository

import com.example.data.db.dao.PurchaseHistoryDao
import com.example.data.db.entity.toDomainModel
import com.example.domain.model.PurchaseHistory
import com.example.domain.repository.PurchaseHistoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PurchaseHistoryRepositoryImpl @Inject constructor(
    private val purchaseHistoryDao: PurchaseHistoryDao
) : PurchaseHistoryRepository {
    override fun getPurchaseHistory(): Flow<List<PurchaseHistory>> {
        return purchaseHistoryDao.getAll().map { list ->
            list.map { it.toDomainModel() }
        }
    }
}