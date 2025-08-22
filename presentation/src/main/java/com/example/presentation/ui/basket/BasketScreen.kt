package com.example.presentation.ui.basket

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.navigation.NavHostController
import com.example.domain.model.BasketProduct
import com.example.domain.model.Product
import com.example.presentation.R
import com.example.presentation.ui.component.Price
import com.example.presentation.ui.popupSnackBar
import com.example.presentation.util.NumberUtils
import com.example.presentation.viewmodel.basket.BasketAction
import com.example.presentation.viewmodel.basket.BasketEvent
import com.example.presentation.viewmodel.basket.BasketViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun BasketScreen(
    snackbarHostState: SnackbarHostState,
    viewModel: BasketViewModel = hiltViewModel(),
    navHostController: NavHostController
) {
    val basketProducts by viewModel.basketProducts.collectAsState(initial = listOf())

    LaunchedEffect(Unit) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is BasketEvent.CompleteCheckoutBasket -> {
                    popupSnackBar(
                        scope = this,
                        snackbarHostState = snackbarHostState,
                        message = "결제되었습니다."
                    )
                    navHostController.popBackStack()
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        if (basketProducts.isEmpty()) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .wrapContentHeight(),
                text = "장바구니가 비어있습니다.",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
            )
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f),
                contentPadding = PaddingValues(10.dp)
            ) {
                items(basketProducts.size) { index ->
                    BasketProductCard(basketProduct = basketProducts[index]) {
                        viewModel.dispatch(BasketAction.RemoveProduct(it))
                    }
                }
            }
        }
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                viewModel.dispatch(BasketAction.CheckoutBasket(basketProducts))
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Magenta,
                disabledContainerColor = Color.LightGray
            ),
            shape = RoundedCornerShape(12.dp),
            enabled = basketProducts.isNotEmpty()
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
                .padding(16.dp)
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
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
                    .padding(start = 12.dp)
                    .weight(1f)
            ) {
                Text(
                    fontSize = 18.sp,
                    text = basketProduct.product.shop.shopName,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 10.dp)
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    fontSize = 16.sp,
                    text = basketProduct.product.productName,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                Price(product = basketProduct.product)
            }
            Text(
                text = "${basketProduct.count}개",
                modifier = Modifier.align(Alignment.Bottom).padding(end = 12.dp),
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center,
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