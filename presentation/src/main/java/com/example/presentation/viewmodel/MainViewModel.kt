package com.example.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import androidx.navigation.navArgument
import com.example.domain.model.AccountInfo
import com.example.domain.model.Banner
import com.example.domain.model.BannerList
import com.example.domain.model.BaseModel
import com.example.domain.model.Carousel
import com.example.domain.model.Category
import com.example.domain.model.ModelType
import com.example.domain.model.Product
import com.example.domain.model.Ranking
import com.example.domain.usecase.GetAccountInfoUseCase
import com.example.domain.usecase.GetCategoryUseCase
import com.example.domain.usecase.GetModelsUseCase
import com.example.domain.usecase.SignInGoogleUseCase
import com.example.domain.usecase.SignOutGoogleUseCase
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
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getModelsUseCase: GetModelsUseCase,
    private val getCategoriesUseCase: GetCategoryUseCase,
    private val getAccountInfoUseCase: GetAccountInfoUseCase,
    private val signInGoogleUseCase: SignInGoogleUseCase,
    private val signOutGoogleUseCase: SignOutGoogleUseCase
) : ViewModel(), ProductDelegate, BannerDelegate, CategoryDelegate {
    private val _columnCount = MutableStateFlow(DEFAULT_COLUMN_COUNT)
    val columnCount : StateFlow<Int> = _columnCount

    val models = getModelsUseCase.getModels().map(::convertToPresentationVM)
    val categories = getCategoriesUseCase.getCategories()
    val accountInfo = getAccountInfoUseCase()

    fun signInGoogle(accountInfo: AccountInfo) {
        viewModelScope.launch {
            signInGoogleUseCase(accountInfo)
        }
    }

    fun signOutGoogle() {
        viewModelScope.launch {
            signOutGoogleUseCase()
        }
    }

    fun openSearchForm(navController: NavHostController) {
        NavigationUtils.navigate(navController, NavigationRouteName.SEARCH)
    }

    fun updateColumnCount(count : Int) {
        _columnCount.update { count }
    }

    override fun openProduct(navController: NavHostController, product: Product) {
        NavigationUtils.navigate(navController, NavigationRouteName.PRODUCT_DETAIL, product)
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