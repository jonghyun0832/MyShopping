package com.example.presentation.util

import com.example.domain.model.Price
import java.text.NumberFormat

object NumberUtils {
    fun numberFormatPrice(price: Int?) : String {
        return NumberFormat.getNumberInstance().format(price ?: 0)
    }
}