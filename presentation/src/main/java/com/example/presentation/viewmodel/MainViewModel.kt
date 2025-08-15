package com.example.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.example.domain.model.Banner
import com.example.domain.model.BannerList
import com.example.domain.model.Category
import com.example.domain.model.Product
import com.example.domain.usecase.GetCategoryUseCase
import com.example.domain.usecase.GetModelsUseCase
import com.example.presentation.ui.NavigationRouteName
import com.example.presentation.util.NavigationUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getModelsUseCase: GetModelsUseCase,
    private val getCategoriesUseCase: GetCategoryUseCase
) : ViewModel() {
    private val _columnCount = MutableStateFlow(DEFAULT_COLUMN_COUNT)
    val columnCount : StateFlow<Int> = _columnCount

    val models = getModelsUseCase.getModels()
    val categories = getCategoriesUseCase.getCategories()

    fun updateColumnCount(count : Int) {
        _columnCount.update { count }
    }

    fun openProduct(product: Product) {

    }

    fun openCarouselProduct(product: Product) {

    }

    fun openBanner(banner: Banner) {

    }

    fun openBannerList(bannerList: BannerList) {

    }

    fun openRankingProduct(product: Product) {

    }

    fun openCategory(navController: NavHostController, category: Category) {
        NavigationUtils.navigate(navController, NavigationRouteName.CATEGORY, category)
    }

    companion object {
        private const val DEFAULT_COLUMN_COUNT = 2
    }
}