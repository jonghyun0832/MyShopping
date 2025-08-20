package com.example.presentation.delegate

import androidx.navigation.NavHostController
import com.example.domain.model.Product

interface ProductDelegate {
    fun openProduct(navController: NavHostController, product: Product)
    fun likeProduct(product: Product)
}