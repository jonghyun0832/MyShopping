package com.example.presentation.viewmodel.search

import androidx.compose.runtime.key
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.domain.model.Product
import com.example.domain.model.SearchFilter
import com.example.domain.model.SearchKeyword
import com.example.domain.usecase.GetSearchKeywordsUseCase
import com.example.domain.usecase.GetSearchProductsUseCase
import com.example.presentation.delegate.ProductDelegate
import com.example.presentation.model.ProductVM
import com.example.presentation.ui.NavigationRouteName
import com.example.presentation.util.NavigationUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getSearchProductsUseCase: GetSearchProductsUseCase,
    private val getSearchKeywordsUseCase: GetSearchKeywordsUseCase
) : ViewModel(), ProductDelegate {
    private val searchManager = SearchManager()
    private val _searchResult = MutableStateFlow<List<ProductVM>>(listOf())
    val searchResult : StateFlow<List<ProductVM>> = _searchResult

    val searchKeywords = getSearchKeywordsUseCase()
    val searchFilters = searchManager.filters

    fun search(keyword: String) {
        viewModelScope.launch {
            searchInternal(keyword)
        }
    }

    fun updateFilter(filter: SearchFilter) {
        viewModelScope.launch {
            searchManager.updateFilter(filter)
            searchInternal()
        }
    }

    private suspend fun searchInternal(newSearchKeyword: String = "") {
        getSearchProductsUseCase(searchManager.searchKeyword, searchManager.currentFilters()).collectLatest {
            if (newSearchKeyword.isNotEmpty()) {
                searchManager.initSearchManager(newSearchKeyword, it)
            }
            _searchResult.emit(it.map(::convertToProductVM))
        }
    }

    private fun convertToProductVM(product: Product): ProductVM {
        return ProductVM(product, this)
    }

    override fun openProduct(
        navController: NavHostController,
        product: Product
    ) {
        NavigationUtils.navigate(navController, NavigationRouteName.PRODUCT_DETAIL, product)
    }

}