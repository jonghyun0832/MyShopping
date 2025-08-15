package com.example.presentation.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.domain.model.Banner
import com.example.presentation.R
import com.example.presentation.model.BannerVM
import com.example.presentation.model.PresentationVM

@Composable
fun BannerCard(presentationVM: BannerVM) {
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .shadow(10.dp),
        onClick = { presentationVM.openBanner(presentationVM.model.bannerId) }
    ) {
        Image(
            painter = painterResource(id = R.drawable.product_image),
            contentDescription = "description",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(2f)
        )
    }
}