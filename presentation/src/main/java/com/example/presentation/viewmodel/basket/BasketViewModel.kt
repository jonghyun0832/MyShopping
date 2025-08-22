package com.example.presentation.viewmodel.basket

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.BasketProduct
import com.example.domain.model.Product
import com.example.domain.usecase.basket.CheckoutBasketUseCase
import com.example.domain.usecase.basket.DeleteBasketProductUseCase
import com.example.domain.usecase.basket.GetBasketProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BasketViewModel @Inject constructor(
    private val getBasketProductsUseCase: GetBasketProductsUseCase,
    private val deleteBasketProductUseCase: DeleteBasketProductUseCase,
    private val checkoutBasketUseCase: CheckoutBasketUseCase
) : ViewModel() {
    val basketProducts = getBasketProductsUseCase()

    private val _eventFlow = MutableSharedFlow<BasketEvent>()
    val eventFlow : SharedFlow<BasketEvent> = _eventFlow

    fun dispatch(action: BasketAction) {
        when(action) {
            is BasketAction.RemoveProduct -> { removeBasketProduct(product = action.product) }
            is BasketAction.CheckoutBasket -> {
                checkoutBasket(products = action.products)
                viewModelScope.launch {
                    _eventFlow.emit(BasketEvent.CompleteCheckoutBasket)
                }
            }
        }
    }

    private fun removeBasketProduct(product: Product) {
        viewModelScope.launch {
            deleteBasketProductUseCase(product)
        }
    }

    private fun checkoutBasket(products: List<BasketProduct>) {
        viewModelScope.launch {
            checkoutBasketUseCase(products)
        }
    }
}

// ViewModel -> Screen
sealed class BasketEvent {
    data object CompleteCheckoutBasket : BasketEvent()
}

// Screen -> ViewModel
sealed class BasketAction {
    data class RemoveProduct(val product: Product) : BasketAction()
    data class CheckoutBasket(val products: List<BasketProduct>) : BasketAction()
}