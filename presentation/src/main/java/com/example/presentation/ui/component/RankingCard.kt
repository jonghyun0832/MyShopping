package com.example.presentation.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.domain.model.Product
import com.example.domain.model.Ranking
import com.example.presentation.R

@Composable
fun RankingCard(ranking: Ranking, onClick: (Product) -> Unit) {
    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { ranking.productList.size / DEFAULT_RANKING_ITEM_COUNT }
    )

    Column {
        Text(
            text = ranking.title,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(10.dp, 0.dp, 0.dp, 0.dp)
        )
        HorizontalPager(
            state = pagerState,
            contentPadding = PaddingValues(end = 50.dp)
        ) { index ->
            Column {
                RankingProductCard(index * 3, ranking.productList[index * 3], onClick)
                RankingProductCard(index * 3 + 1, ranking.productList[index * 3 + 1], onClick)
                RankingProductCard(index * 3 + 2, ranking.productList[index * 3 + 2], onClick)
            }
        }
    }
}

@Composable
fun RankingProductCard(index: Int, product: Product, onClick: (Product) -> Unit) {
    Row(
        modifier = Modifier.padding(10.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = "${index + 1}",
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(0.dp, 0.dp, 10.dp, 0.dp)
        )
        Image(
            painter = painterResource(id = R.drawable.product_image),
            contentDescription = "description",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .width(70.dp)
                .aspectRatio(0.7f)
        )
        Column(
            modifier = Modifier.padding(10.dp, 0.dp, 0.dp, 0.dp)
        ) {
            Text(
                fontSize = 14.sp,
                text = product.shop.shopName,
                modifier = Modifier.padding(0.dp, 10.dp, 0.dp, 0.dp)
            )
            Text(
                fontSize = 14.sp,
                text = product.productName,
                modifier = Modifier.padding(0.dp, 10.dp, 0.dp, 0.dp)
            )
            Price(product = product)
        }
    }
}

private const val DEFAULT_RANKING_ITEM_COUNT = 3