package com.example.domain.usecase.like

import com.example.domain.model.Product
import com.example.domain.repository.LikeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLikeUseCase @Inject constructor(
    private val repository: LikeRepository
) {
    operator fun invoke() : Flow<List<Product>> {
        return repository.getLikeProduct()
    }
}