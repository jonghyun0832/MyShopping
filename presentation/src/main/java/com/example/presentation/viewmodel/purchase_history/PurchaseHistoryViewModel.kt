package com.example.presentation.viewmodel.purchase_history

import androidx.lifecycle.ViewModel
import com.example.domain.usecase.purchase_history.GetPurchaseHistoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PurchaseHistoryViewModel @Inject constructor(
    private val getPurchaseHistoryUseCase: GetPurchaseHistoryUseCase
) : ViewModel() {
    val purchaseHistory = getPurchaseHistoryUseCase()
}