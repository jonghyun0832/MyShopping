package com.example.presentation.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.domain.model.BannerList
import com.example.presentation.R
import com.example.presentation.model.BannerListVM
import com.example.presentation.model.PresentationVM
import kotlinx.coroutines.delay

@Composable
fun BannerListCard(presentationVM: BannerListVM) {
    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { presentationVM.model.imageList.size }
    )
    LaunchedEffect(key1 = pagerState) {
        autoScrollInfinity(pagerState)
    }
    HorizontalPager(
        state = pagerState
    ) {
        Card(
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .shadow(20.dp),
            onClick = { presentationVM.openBannerList(presentationVM.model.bannerId) }
        ) {
            Image(
                painter = painterResource(id = R.drawable.product_image),
                contentDescription = "description",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(2f)
            )
            Text(
                text = "Page Number : $it",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.End
            )
        }
    }
}

private suspend fun autoScrollInfinity(pagerState: PagerState) {
    while (true) {
        delay(3000)
        val currentPage = pagerState.currentPage
        var nextPage = currentPage + 1
        if (currentPage + 1 > pagerState.pageCount) {
            nextPage = 0
        }
        pagerState.animateScrollToPage(nextPage)
    }
}