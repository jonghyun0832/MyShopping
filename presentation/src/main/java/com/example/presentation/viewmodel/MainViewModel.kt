package com.example.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.domain.model.Product
import com.example.domain.usecase.GetProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val getProductsUseCase: GetProductsUseCase) : ViewModel() {
    val products = getProductsUseCase.getProducts()
}