package com.example.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.example.domain.model.Banner
import com.example.domain.model.BannerList
import com.example.domain.model.BaseModel
import com.example.domain.model.Carousel
import com.example.domain.model.Category
import com.example.domain.model.ModelType
import com.example.domain.model.Product
import com.example.domain.model.Ranking
import com.example.domain.usecase.GetCategoryUseCase
import com.example.domain.usecase.GetModelsUseCase
import com.example.presentation.delegate.BannerDelegate
import com.example.presentation.delegate.CategoryDelegate
import com.example.presentation.delegate.ProductDelegate
import com.example.presentation.model.BannerListVM
import com.example.presentation.model.BannerVM
import com.example.presentation.model.CarouselVM
import com.example.presentation.model.PresentationVM
import com.example.presentation.model.ProductVM
import com.example.presentation.model.RankingVM
import com.example.presentation.ui.NavigationRouteName
import com.example.presentation.util.NavigationUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getModelsUseCase: GetModelsUseCase,
    private val getCategoriesUseCase: GetCategoryUseCase
) : ViewModel(), ProductDelegate, BannerDelegate, CategoryDelegate {
    private val _columnCount = MutableStateFlow(DEFAULT_COLUMN_COUNT)
    val columnCount : StateFlow<Int> = _columnCount

    val models = getModelsUseCase.getModels().map(::convertToPresentationVM)
    val categories = getCategoriesUseCase.getCategories()

    fun updateColumnCount(count : Int) {
        _columnCount.update { count }
    }

    override fun openProduct(product: Product) {

    }

    override fun openBanner(bannerId: String) {
        TODO("Not yet implemented")
    }

    override fun openCategory(navController: NavHostController, category: Category) {
        NavigationUtils.navigate(navController, NavigationRouteName.CATEGORY, category)
    }

    private fun convertToPresentationVM(list: List<BaseModel>) : List<PresentationVM<out BaseModel>> {
        return list.map { model ->
            when(model) {
                is Product -> ProductVM(model, this)
                is Ranking -> RankingVM(model, this)
                is Carousel -> CarouselVM(model, this)
                is Banner -> BannerVM(model, this)
                is BannerList -> BannerListVM(model, this)
            }
        }
    }

    companion object {
        private const val DEFAULT_COLUMN_COUNT = 2
    }
}