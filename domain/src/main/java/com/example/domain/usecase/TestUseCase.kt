package com.example.domain.usecase

import com.example.domain.model.TestModel
import com.example.domain.repository.TestRepository
import javax.inject.Inject

class TestUseCase @Inject constructor(private val testRepository: TestRepository) {
    fun getTestModel() : TestModel {
        return testRepository.getTestModel()
    }
}