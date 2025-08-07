package com.example.data.repository

import com.example.data.datasource.TestDataSource
import com.example.domain.repository.TestRepository

class TestRepositoryImpl(val dataSource: TestDataSource) : TestRepository {
}