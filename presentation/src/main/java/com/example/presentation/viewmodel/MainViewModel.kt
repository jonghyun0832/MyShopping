package com.example.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.domain.model.TestModel
import com.example.domain.usecase.TestUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val testUseCase: TestUseCase) : ViewModel() {
    fun getTestModel(): TestModel {
        return testUseCase.getTestModel()
    }
}