package com.example.presentation.ui.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.presentation.viewmodel.MainViewModel

@Composable
fun MainCategoryScreen(viewModel: MainViewModel, navController: NavHostController) {
    val categories by viewModel.categories.collectAsState(
        initial = listOf()
    )

    LazyVerticalGrid(
        columns = GridCells.Fixed(3)
    ) {
        items(
            count = categories.size,
            span = { GridItemSpan(1) }
        ) {
            Card(
                shape = RoundedCornerShape(4.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(10.dp)
                    .shadow((10.dp)),
                onClick = { viewModel.openCategory(navController, categories[it]) }
            ) {
                Text(
                    text = categories[it].categoryName,
                    modifier = Modifier.fillMaxSize()
                        .padding(10.dp),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}