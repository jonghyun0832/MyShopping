package com.example.domain.repository

import com.example.domain.model.PurchaseHistory
import kotlinx.coroutines.flow.Flow

interface PurchaseHistoryRepository {
    fun getPurchaseHistory(): Flow<List<PurchaseHistory>>

}