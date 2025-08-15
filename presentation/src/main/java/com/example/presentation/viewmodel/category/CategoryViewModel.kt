package com.example.presentation.viewmodel.category

import androidx.lifecycle.ViewModel
import com.example.domain.model.BaseModel
import com.example.domain.model.Category
import com.example.domain.model.Product
import com.example.domain.usecase.GetCategoryUseCase
import com.example.domain.usecase.GetProductsByCategoryUseCase
import com.example.presentation.delegate.ProductDelegate
import com.example.presentation.model.PresentationVM
import com.example.presentation.model.ProductVM
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val getCategoryUseCase: GetCategoryUseCase,
    private val getProductsByCategoryUseCase: GetProductsByCategoryUseCase
) : ViewModel(), ProductDelegate {
    private val _products = MutableStateFlow<List<ProductVM>>(listOf())
    val products : StateFlow<List<ProductVM>> = _products

    suspend fun updateCategory(category: Category) {
        getProductsByCategoryUseCase(category = category).collectLatest {
            _products.emit(convertToPresentationVM(it))
        }
    }

    override fun openProduct(product: Product) {

    }

    private fun convertToPresentationVM(list: List<Product>) : List<ProductVM> {
        return list.map {
            ProductVM(it, this)
        }
    }
}