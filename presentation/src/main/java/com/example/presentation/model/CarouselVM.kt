package com.example.presentation.model

import androidx.navigation.NavHostController
import com.example.domain.model.Carousel
import com.example.domain.model.Product
import com.example.presentation.delegate.ProductDelegate

class CarouselVM(
    model: Carousel,
    private val productDelegate: ProductDelegate
): PresentationVM<Carousel>(model) {
    fun openCarouselProduct(navController: NavHostController, product: Product) {
        productDelegate.openProduct(navController, product)
        sendCarouselLog()
    }

    fun sendCarouselLog() {

    }
}