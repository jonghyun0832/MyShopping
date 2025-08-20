package com.example.domain.usecase.main

import com.example.domain.model.Product
import com.example.domain.repository.MainRepository
import javax.inject.Inject

class UpdateLikeProductUseCase @Inject constructor(
    private val mainRepository: MainRepository
) {
    suspend operator fun invoke(product: Product) {
        mainRepository.likeProduct(product)
    }
}