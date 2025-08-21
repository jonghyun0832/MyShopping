package com.example.domain.usecase.purchase_history

import com.example.domain.model.PurchaseHistory
import com.example.domain.repository.PurchaseHistoryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPurchaseHistoryUseCase @Inject constructor(
    private val purchaseHistoryRepository: PurchaseHistoryRepository
) {
    operator fun invoke(): Flow<List<PurchaseHistory>> {
        return purchaseHistoryRepository.getPurchaseHistory()
    }
}