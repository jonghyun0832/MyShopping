package com.example.domain.repository

import com.example.domain.model.Product

interface MainRepository {
    fun getProductList(): List<Product>
}