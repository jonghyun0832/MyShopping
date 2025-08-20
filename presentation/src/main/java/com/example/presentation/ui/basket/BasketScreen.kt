package com.example.presentation.ui.basket

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.domain.model.BasketProduct
import com.example.domain.model.Product
import com.example.presentation.R
import com.example.presentation.ui.component.Price
import com.example.presentation.util.NumberUtils
import com.example.presentation.viewmodel.basket.BasketViewModel

@Composable
fun BasketScreen(
    viewModel: BasketViewModel = hiltViewModel()
) {
    val basketProducts by viewModel.basketProducts.collectAsState(initial = listOf())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f),
            contentPadding = PaddingValues(10.dp)
        ) {
            items(basketProducts.size) { index ->
                BasketProductCard(basketProduct = basketProducts[index]) {
                    viewModel.removeBasketProduct(it)
                }
            }
        }
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {},
            colors = ButtonDefaults.buttonColors(containerColor = Color.Magenta),
            shape = RoundedCornerShape(12.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.Check,
                contentDescription = "Check Icon"
            )

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp),
                fontSize = 16.sp,
                text = "${getTotalPrice(basketProducts)}원 결제하기"
            )
        }
    }
}

@Composable
fun BasketProductCard(basketProduct: BasketProduct, removeProduct: (Product) -> Unit) {
    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = R.drawable.product_image),
                contentDescription = "description",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(120.dp)
                    .aspectRatio(1f)
            )
            Column(
                modifier = Modifier
                    .padding(10.dp, 0.dp, 0.dp, 0.dp)
                    .weight(1f)
            ) {
                Text(
                    fontSize = 14.sp,
                    text = basketProduct.product.shop.shopName,
                    modifier = Modifier.padding(0.dp, 10.dp, 0.dp, 0.dp)
                )
                Text(
                    fontSize = 14.sp,
                    text = basketProduct.product.productName,
                    modifier = Modifier.padding(0.dp, 10.dp, 0.dp, 0.dp)
                )
                Price(product = basketProduct.product)
            }
            Text(
                text = "${basketProduct.count} 개",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(30.dp)
            )
        }
        IconButton(
            onClick = { removeProduct(basketProduct.product) },
            modifier = Modifier.align(Alignment.TopEnd)
        ) {
            Icon(
                imageVector = Icons.Filled.Close,
                contentDescription = "Close Icon"
            )
        }
    }
}

private fun getTotalPrice(list: List<BasketProduct>): String {
    val totalPrice = list.sumOf { it.product.price.finalPrice * it.count }
    return NumberUtils.numberFormatPrice(totalPrice)
}