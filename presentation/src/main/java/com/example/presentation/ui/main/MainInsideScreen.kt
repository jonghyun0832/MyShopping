package com.example.presentation.ui.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.domain.model.Banner
import com.example.domain.model.BannerList
import com.example.domain.model.Carousel
import com.example.domain.model.ModelType
import com.example.domain.model.Product
import com.example.domain.model.Ranking
import com.example.presentation.R
import com.example.presentation.ui.component.BannerCard
import com.example.presentation.ui.component.BannerListCard
import com.example.presentation.ui.component.CarouselCard
import com.example.presentation.ui.component.ProductCard
import com.example.presentation.ui.component.RankingCard
import com.example.presentation.viewmodel.MainViewModel

@Composable
fun MainInsideScreen(viewModel: MainViewModel) {
    val models by viewModel.models.collectAsState(initial = listOf())
    val columnCount by viewModel.columnCount.collectAsState()

    LazyVerticalGrid(
        columns = GridCells.Fixed(columnCount)
    ) {
        items(
            count = models.size,
            span = { index ->
                val item = models[index]
                val spanCount = getSpanCountByType(item.type, columnCount)
                GridItemSpan(spanCount)
            }
        ) { index ->
            val item = models[index]
            when (item) {
                is Banner -> BannerCard(banner = item) { model ->
                    viewModel.openBanner(model)
                }
                is BannerList -> BannerListCard(bannerList = item) { model ->
                    viewModel.openBannerList(model)
                }
                is Product -> ProductCard(product = item) { model ->
                    viewModel.openProduct(model )
                }
                is Carousel -> CarouselCard(carousel = item) { model ->
                    viewModel.openCarouselProduct(model)
                }
                is Ranking -> RankingCard(ranking = item) { model ->
                    viewModel.openRankingProduct(model)
                }
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