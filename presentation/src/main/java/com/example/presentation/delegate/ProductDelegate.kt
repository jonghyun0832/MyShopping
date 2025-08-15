package com.example.presentation.delegate

import com.example.domain.model.Product

interface ProductDelegate {
    fun openProduct(product: Product)
}