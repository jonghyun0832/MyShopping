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
import androidx.navigation.NavHostController
import com.example.domain.model.Product
import com.example.domain.model.Ranking
import com.example.presentation.R
import com.example.presentation.model.PresentationVM
import com.example.presentation.model.RankingVM

@Composable
fun RankingCard(navController: NavHostController, presentationVM: RankingVM) {
    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { presentationVM.model.productList.size / DEFAULT_RANKING_ITEM_COUNT }
    )

    Column {
        Text(
            text = presentationVM.model.title,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(10.dp, 0.dp, 0.dp, 0.dp)
        )
        HorizontalPager(
            state = pagerState,
            contentPadding = PaddingValues(end = 50.dp)
        ) { index ->
            Column {
                RankingProductCard(index * 3, presentationVM.model.productList[index * 3]) {
                    presentationVM.openRankingProduct(navController, it)
                }
                RankingProductCard(index * 3 + 1, presentationVM.model.productList[index * 3 + 1]) {
                    presentationVM.openRankingProduct(navController, it)
                }
                RankingProductCard(index * 3 + 2, presentationVM.model.productList[index * 3 + 2]) {
                    presentationVM.openRankingProduct(navController, it)
                }
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