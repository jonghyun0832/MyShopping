package com.example.presentation.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.domain.model.Category
import com.example.domain.model.Price
import com.example.domain.model.Product
import com.example.domain.model.SalesStatus
import com.example.domain.model.Shop
import com.example.presentation.R
import com.example.presentation.delegate.ProductDelegate
import com.example.presentation.model.PresentationVM
import com.example.presentation.model.ProductVM

@Composable
fun ProductCard(navController: NavHostController, presentationVM: ProductVM) {
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(intrinsicSize = IntrinsicSize.Max)
            .padding(10.dp)
            .shadow(elevation = 10.dp),
        onClick = { presentationVM.openProduct(navController, presentationVM.model) }
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(
                onClick = { presentationVM.likeProduct(presentationVM.model) },
                modifier = Modifier.align(Alignment.BottomEnd)
            ) {
                Icon(
                    imageVector = if (presentationVM.model.isLike) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                    contentDescription = "Favorite Icon"
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(10.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {
                Image(
                    painter = painterResource(id = R.drawable.product_image),
                    contentDescription = "product image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f)
                )
                Text(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    text = presentationVM.model.shop.shopName
                )
                Text(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    text = presentationVM.model.productName
                )
                Price(presentationVM.model)
            }
        }
    }
}

@Composable
fun Price(product: Product) {
    when (product.price.salesStatus) {
        SalesStatus.ON_SALE -> {
            Text(
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                text = "${product.price.originPrice}원"
            )
        }

        SalesStatus.ON_DISCOUNT -> {
            Text(
                fontSize = 14.sp,
                text = "${product.price.originPrice}원",
                style = TextStyle(textDecoration = TextDecoration.LineThrough)
            )
            Text(
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Magenta,
                text = "${product.price.finalPrice}원",
            )
        }

        SalesStatus.SOLD_OUT -> {
            Text(
                fontSize = 18.sp,
                color = Color(0xFF666666),
                text = "판매종료"
            )
        }
    }
}

@Preview
@Composable
private fun PreviewProductCard() {
    ProductCard(
        navController = rememberNavController(),
        presentationVM = ProductVM(
            model = Product(
                productId = "1",
                productName = "상품명",
                imageUrl = "",
                price = Price(
                    originPrice = 30000,
                    finalPrice = 30000,
                    salesStatus = SalesStatus.ON_SALE
                ),
                category = Category.Top,
                shop = Shop(
                    shopId = "1",
                    shopName = "샵 이름",
                    imageUrl = ""
                ),
                isNew = true,
                isLike = false,
                isFreeShipping = false
            ),
            object : ProductDelegate {
                override fun openProduct(
                    navController: NavHostController,
                    product: Product
                ) {
                }

                override fun likeProduct(product: Product) {
                    TODO("Not yet implemented")
                }
            }
        )
    )
}

@Preview
@Composable
private fun PreviewCardDiscount() {
    ProductCard(
        navController = rememberNavController(),
        presentationVM = ProductVM(
            model = Product(
                productId = "1",
                productName = "상품명",
                imageUrl = "",
                price = Price(
                    originPrice = 30000,
                    finalPrice = 20000,
                    salesStatus = SalesStatus.ON_DISCOUNT
                ),
                category = Category.Top,
                shop = Shop(
                    shopId = "1",
                    shopName = "샵 이름",
                    imageUrl = ""
                ),
                isNew = true,
                isLike = true,
                isFreeShipping = false
            ),
            object : ProductDelegate {
                override fun openProduct(
                    navController: NavHostController,
                    product: Product
                ) {
                }

                override fun likeProduct(product: Product) {}
            }
        )
    )
}

@Preview
@Composable
private fun PreviewCardSoldOut() {
    ProductCard(
        navController = rememberNavController(),
        presentationVM = ProductVM(
            model = Product(
                productId = "1",
                productName = "상품명",
                imageUrl = "",
                price = Price(
                    originPrice = 30000,
                    finalPrice = 30000,
                    salesStatus = SalesStatus.SOLD_OUT
                ),
                category = Category.Top,
                shop = Shop(
                    shopId = "1",
                    shopName = "샵 이름",
                    imageUrl = ""
                ),
                isNew = true,
                isLike = false,
                isFreeShipping = false
            ),
            object : ProductDelegate {
                override fun openProduct(
                    navController: NavHostController,
                    product: Product
                ) {
                }

                override fun likeProduct(product: Product) {}
            }
        )
    )
}