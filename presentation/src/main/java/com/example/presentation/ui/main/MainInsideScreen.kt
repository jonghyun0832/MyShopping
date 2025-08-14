package com.example.presentation.ui.main

import android.util.Log
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.presentation.ui.common.ProductCard
import com.example.presentation.viewmodel.MainViewModel

@Composable
fun MainInsideScreen(viewModel: MainViewModel) {
    val products by viewModel.products.collectAsState(initial = listOf())

    LazyColumn {
        items(products.size) { index ->
            ProductCard(product = products[index]) {

            }
        }
    }
}