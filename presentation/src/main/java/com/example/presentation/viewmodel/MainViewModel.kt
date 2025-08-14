package com.example.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.domain.model.Banner
import com.example.domain.model.BannerList
import com.example.domain.model.Product
import com.example.domain.usecase.GetModelsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val getModelsUseCase: GetModelsUseCase) : ViewModel() {
    private val _columnCount = MutableStateFlow(DEFAULT_COLUMN_COUNT)
    val columnCount : StateFlow<Int> = _columnCount

    val models = getModelsUseCase.getModels()

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

    companion object {
        private const val DEFAULT_COLUMN_COUNT = 2
    }
}