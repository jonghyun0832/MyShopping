package com.example.presentation.ui.main

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.domain.model.Banner
import com.example.domain.model.BannerList
import com.example.domain.model.Carousel
import com.example.domain.model.ModelType
import com.example.domain.model.Product
import com.example.domain.model.Ranking
import com.example.presentation.model.BannerListVM
import com.example.presentation.model.BannerVM
import com.example.presentation.model.CarouselVM
import com.example.presentation.model.ProductVM
import com.example.presentation.model.RankingVM
import com.example.presentation.ui.component.BannerCard
import com.example.presentation.ui.component.BannerListCard
import com.example.presentation.ui.component.CarouselCard
import com.example.presentation.ui.component.ProductCard
import com.example.presentation.ui.component.RankingCard
import com.example.presentation.viewmodel.MainViewModel

@Composable
fun MainHomeScreen(viewModel: MainViewModel) {
    val models by viewModel.models.collectAsState(initial = listOf())
    val columnCount by viewModel.columnCount.collectAsState()

    LazyVerticalGrid(
        columns = GridCells.Fixed(columnCount)
    ) {
        items(
            count = models.size,
            span = { index ->
                val item = models[index]
                val spanCount = getSpanCountByType(item.model.type, columnCount)
                GridItemSpan(spanCount)
            }
        ) { index ->
            val item = models[index]
            when (item) {
                is BannerVM -> BannerCard(presentationVM = item)
                is BannerListVM -> BannerListCard(presentationVM = item)
                is ProductVM -> ProductCard(presentationVM = item)
                is CarouselVM -> CarouselCard(presentationVM = item)
                is RankingVM -> RankingCard(presentationVM = item)
            }
        }
    }
}

private fun getSpanCountByType(type: ModelType, defaultColumnCount: Int): Int {
    return when(type) {
        ModelType.PRODUCT -> {
            1
        }
        ModelType.BANNER, ModelType.BANNER_LIST, ModelType.CAROUSEL, ModelType.RANKING -> {
            defaultColumnCount
        }
    }
}