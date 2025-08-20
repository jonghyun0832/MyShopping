package com.example.presentation.ui.main

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.presentation.model.ProductVM
import com.example.presentation.ui.component.ProductCard
import com.example.presentation.viewmodel.MainViewModel

@Composable
fun LikeScreen(
    navController: NavHostController,
    viewModel: MainViewModel
) {
    val likeProducts by viewModel.likeProducts.collectAsState(initial = listOf())

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(10.dp)
    ) {
        items(likeProducts.size) { index ->
            ProductCard(
                navController = navController,
                presentationVM = likeProducts[index] as ProductVM
            )
        }
    }
}