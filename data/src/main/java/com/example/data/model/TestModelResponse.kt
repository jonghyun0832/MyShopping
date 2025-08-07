package com.example.data.model

import com.example.domain.model.TestModel

data class TestModelResponse(val name: String)

fun TestModelResponse.toDomainModel() : TestModel {
    return TestModel(name)
}