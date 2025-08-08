package com.example.data.datasource

import com.example.domain.model.TestModel
import javax.inject.Inject

class TestDataSource @Inject constructor() {
    fun getTestModel() : TestModel {
        return TestModel("Temp Test Model")
    }
}