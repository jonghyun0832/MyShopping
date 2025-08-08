package com.example.data.repository

import com.example.data.datasource.TestDataSource
import com.example.domain.model.TestModel
import com.example.domain.repository.TestRepository
import javax.inject.Inject

class TestRepositoryImpl @Inject constructor(private val dataSource: TestDataSource) : TestRepository {
    override fun getTestModel(): TestModel {
        return dataSource.getTestModel()
    }
}