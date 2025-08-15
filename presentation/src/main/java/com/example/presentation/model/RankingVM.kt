package com.example.presentation.model

import com.example.domain.model.Product
import com.example.domain.model.Ranking
import com.example.presentation.delegate.ProductDelegate

class RankingVM(
    model: Ranking,
    private val productDelegate: ProductDelegate
) : PresentationVM<Ranking>(model) {
    fun openRankingProduct(product: Product) {
        productDelegate.openProduct(product)
        sendRankingLog()
    }

    private fun sendRankingLog() {

    }
}