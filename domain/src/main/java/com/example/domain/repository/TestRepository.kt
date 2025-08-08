package com.example.domain.repository

import com.example.domain.model.TestModel

interface TestRepository {
    fun getTestModel(): TestModel
}