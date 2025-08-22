package com.example.presentation.viewmodel.product_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.Product
import com.example.domain.usecase.product.AddBasketUseCase
import com.example.domain.usecase.product.GetProductDetailUseCase
import com.google.android.play.integrity.internal.ac
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    private val getProductDetailUseCase: GetProductDetailUseCase,
    private val addBasketUseCase: AddBasketUseCase
) : ViewModel() {
    private val _product = MutableStateFlow<Product?>(null)
    val product: StateFlow<Product?> = _product

    private val _eventFlow = MutableSharedFlow<ProductDetailEvent>()
    val eventFlow : SharedFlow<ProductDetailEvent> = _eventFlow

    fun dispatch(action: ProductDetailAction) {
        when(action) {
            is ProductDetailAction.AddBasket -> { addBasket(action.product) }
            is ProductDetailAction.UpdateProduct -> { updateProduct(action.productId) }
        }
    }

    private fun updateProduct(productId: String) {
        viewModelScope.launch {
            getProductDetailUseCase(productId).collectLatest {
                _product.emit(it)
            }
        }
    }

    private fun addBasket(product: Product?) {
        product ?: return
        viewModelScope.launch {
            addBasketUseCase(product)
            _eventFlow.emit(ProductDetailEvent.ShowSnackBar)
        }
    }
}

sealed class ProductDetailEvent {
    data object ShowSnackBar : ProductDetailEvent()
}

sealed class ProductDetailAction {
    data class AddBasket(val product: Product?) : ProductDetailAction()
    data class UpdateProduct(val productId: String) : ProductDetailAction()
}