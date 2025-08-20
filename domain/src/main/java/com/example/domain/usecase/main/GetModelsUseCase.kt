package com.example.domain.usecase.main

import com.example.domain.model.BaseModel
import com.example.domain.repository.MainRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetModelsUseCase @Inject constructor(private val mainRepository: MainRepository) {
    fun getModels() : Flow<List<BaseModel>> {
        return mainRepository.getModelList()
    }
}