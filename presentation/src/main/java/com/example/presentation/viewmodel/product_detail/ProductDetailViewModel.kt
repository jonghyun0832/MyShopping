package com.example.presentation.viewmodel.product_detail

import androidx.lifecycle.ViewModel
import com.example.domain.model.Product
import com.example.domain.usecase.GetProductDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    private val getProductDetailUseCase: GetProductDetailUseCase
) : ViewModel() {
    private val _product = MutableStateFlow<Product?>(null)
    val product: StateFlow<Product?> = _product

    suspend fun updateProduct(productId: String) {
        getProductDetailUseCase(productId).collectLatest {
            _product.emit(it)
        }
    }

    fun addCart(productId: String) {

    }
}