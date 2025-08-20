package com.example.presentation.viewmodel.basket

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.Product
import com.example.domain.usecase.basket.DeleteBasketProductUseCase
import com.example.domain.usecase.basket.GetBasketProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BasketViewModel @Inject constructor(
    private val getBasketProductsUseCase: GetBasketProductsUseCase,
    private val deleteBasketProductUseCase: DeleteBasketProductUseCase
) : ViewModel() {
    val basketProducts = getBasketProductsUseCase()

    fun removeBasketProduct(product: Product) {
        viewModelScope.launch {
            deleteBasketProductUseCase(product)
        }
    }
}