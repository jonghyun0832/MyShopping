package com.example.presentation.viewmodel.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.domain.model.Product
import com.example.domain.model.SearchFilter
import com.example.domain.model.SearchKeyword
import com.example.domain.usecase.search.GetSearchKeywordsUseCase
import com.example.domain.usecase.search.GetSearchProductsUseCase
import com.example.domain.usecase.search.UpdateLikeProductBySearch
import com.example.presentation.delegate.ProductDelegate
import com.example.presentation.model.ProductVM
import com.example.presentation.ui.ProductDetailNav
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
    private val getSearchKeywordsUseCase: GetSearchKeywordsUseCase,
    private val updateLikeProductBySearch: UpdateLikeProductBySearch
) : ViewModel(), ProductDelegate {
    private val searchManager = SearchManager()
    private val _searchResult = MutableStateFlow<List<ProductVM>>(listOf())
    val searchResult : StateFlow<List<ProductVM>> = _searchResult

    val searchKeywords = getSearchKeywordsUseCase()
    val searchFilters = searchManager.filters

    fun dispatch(action: SearchAction) {
        when(action) {
            is SearchAction.Search -> { search(action.keyword) }
            is SearchAction.UpdateFilter -> { updateFilter(action.filter) }
        }
    }

    private fun search(keyword: String) {
        viewModelScope.launch {
            searchInternalNewSearchKeyword(keyword)
        }
    }

    private fun updateFilter(filter: SearchFilter) {
        viewModelScope.launch {
            searchManager.updateFilter(filter)
            searchInternal()
        }
    }

    private suspend fun searchInternal() {
        getSearchProductsUseCase(searchManager.searchKeyword, searchManager.currentFilters()).collectLatest {
            _searchResult.emit(it.map(::convertToProductVM))
        }
    }

    private suspend fun searchInternalNewSearchKeyword(newSearchKeyword: String = "") {
        searchManager.clearFilter()

        getSearchProductsUseCase(SearchKeyword(newSearchKeyword), searchManager.currentFilters()).collectLatest {
            searchManager.initSearchManager(newSearchKeyword, it)
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
        NavigationUtils.navigate(navController, ProductDetailNav.navigateWithArg(product.productId))
    }

    override fun likeProduct(product: Product) {
        viewModelScope.launch {
            updateLikeProductBySearch(product)
        }
    }
}

sealed class SearchAction {
    data class Search(val keyword: String) : SearchAction()
    data class UpdateFilter(val filter: SearchFilter) : SearchAction()
}