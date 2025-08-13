package com.example.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.data.db.converter.PurchaseConverter
import com.example.domain.model.Category
import com.example.domain.model.Price
import com.example.domain.model.Product
import com.example.domain.model.Shop

@Entity(tableName = "purchase")
@TypeConverters(PurchaseConverter::class)
data class PurchaseProductEntity(
    @PrimaryKey
    val productId: String,
    val productName: String,
    val imageUrl: String,
    val price: Price,
    val category: Category,
    val shop: Shop,
    val isNew: Boolean,
    val isFreeShipping: Boolean,
)

fun PurchaseProductEntity.toDomainModel() : Product {
    return Product(
        productId = productId,
        productName = productName,
        imageUrl = imageUrl,
        price = price,
        category = category,
        shop = shop,
        isNew = isNew,
        isFreeShipping = isFreeShipping
    )
}