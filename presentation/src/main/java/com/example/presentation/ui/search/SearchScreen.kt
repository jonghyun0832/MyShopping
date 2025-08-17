package com.example.presentation.ui.search

import android.widget.Space
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.RangeSlider
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.domain.model.SearchFilter
import com.example.myshopping.ui.theme.Purple40
import com.example.myshopping.ui.theme.Purple80
import com.example.presentation.ui.component.ProductCard
import com.example.presentation.viewmodel.search.SearchViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    navController: NavHostController,
    viewModel: SearchViewModel = hiltViewModel()
) {
    val searchFilter by viewModel.searchFilters.collectAsState()
    val sheetState = rememberStandardBottomSheetState(initialValue = SheetValue.PartiallyExpanded)
    val scaffoldState = rememberBottomSheetScaffoldState(bottomSheetState = sheetState)
    val scope = rememberCoroutineScope()
    var currentFilterType by remember { mutableStateOf<SearchFilter.Type?>(null) }

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetShape = RoundedCornerShape(10.dp, 10.dp, 0.dp, 0.dp),
        sheetContent = {
            when (currentFilterType) {
                SearchFilter.Type.CATEGORY -> {
                    val categoryFilter =
                        searchFilter.first { it is SearchFilter.CategoryFilter } as SearchFilter.CategoryFilter
                    SearchFilterCategoryContent(filter = categoryFilter) {
                        scope.launch {
                            currentFilterType = null
                            sheetState.partialExpand()
                        }
                        viewModel.updateFilter(it)
                    }
                }

                SearchFilter.Type.PRICE -> {
                    val priceFilter =
                        searchFilter.first { it is SearchFilter.PriceFilter } as SearchFilter.PriceFilter
                    SearchFilterPriceContent(filter = priceFilter) {
                        scope.launch {
                            currentFilterType = null
                            sheetState.partialExpand()
                        }
                        viewModel.updateFilter(it)
                    }
                }

                else -> {}
            }
        },
        sheetPeekHeight = 0.dp
    ) {
        SearchContent(viewModel, navController) {
            scope.launch {
                currentFilterType = it
                sheetState.expand()
            }
        }
    }
}

@Composable
fun SearchFilterCategoryContent(
    filter: SearchFilter.CategoryFilter,
    onCompleteFilter: (SearchFilter) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
    ) {
        Text(
            text = "카테고리 필터",
            fontWeight = FontWeight.SemiBold,
            fontSize = 20.sp,
            modifier = Modifier.padding(10.dp)
        )
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(10.dp)
        ) {
            items(filter.categories.size) { index ->
                val category = filter.categories[index]
                Button(
                    onClick = {
                        filter.selectedCategory = category
                        onCompleteFilter(filter)
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (filter.selectedCategory == category) Purple80 else Purple40
                    )
                ) {
                    Text(
                        fontSize = 18.sp,
                        text = category.categoryName
                    )
                }
            }
        }
    }
}

@Composable
fun SearchFilterPriceContent(
    filter: SearchFilter.PriceFilter,
    onCompleteFilter: (SearchFilter) -> Unit
) {
    var sliderValues by remember {
        val selectedRange = filter.selectedRange
        if (selectedRange == null) {
            mutableStateOf(filter.priceRange.first..filter.priceRange.second)
        } else {
            mutableStateOf(selectedRange.first..selectedRange.second)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
            .padding(20.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "가격 필터",
                fontWeight = FontWeight.SemiBold,
                fontSize = 20.sp,
                modifier = Modifier.padding(10.dp)
            )
            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = {
                    filter.selectedRange = sliderValues.start to sliderValues.endInclusive
                    onCompleteFilter(filter)
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Purple80
                )
            ) {
                Text(
                    fontSize = 18.sp,
                    text = "완료"
                )
            }
        }
        RangeSlider(
            value = sliderValues,
            onValueChange = { sliderValues = it },
            valueRange = filter.priceRange.first..filter.priceRange.second,
            steps = 9
        )
        Text(
            text = "최저가 : ${sliderValues.start} ~ 최고가 : ${sliderValues.endInclusive}"
        )
    }
}

@Composable
fun SearchContent(
    viewModel: SearchViewModel,
    navController: NavHostController,
    openFilterDialog: (SearchFilter.Type) -> Unit
) {
    val searchResult by viewModel.searchResult.collectAsState()
    val searchFilters by viewModel.searchFilters.collectAsState()
    val searchKeywords by viewModel.searchKeywords.collectAsState(listOf())
    var keyword by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current

    Column {
        SearchBox(
            keyword = keyword,
            onValueChange = { keyword = it },
            searchAction = {
                viewModel.search(keyword)
                keyboardController?.hide()
            }
        )
        if (searchResult.isEmpty()) {
            Text(
                modifier = Modifier.padding(6.dp),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                text = "최근 검색어"
            )
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(10.dp)
            ) {
                items(count = searchKeywords.size) { index ->
                    val currentKeyword = searchKeywords.reversed()[index].keyword
                    Button(
                        onClick = {
                            keyword = currentKeyword
                            viewModel.search(keyword)
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Unspecified)
                    ) {
                        Text(
                            fontSize = 18.sp,
                            text = currentKeyword
                        )
                    }
                }
            }
        } else {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                Button(
                    onClick = { openFilterDialog(SearchFilter.Type.CATEGORY) }
                ) {
                    val filter = searchFilters.find { it.type == SearchFilter.Type.CATEGORY } as? SearchFilter.CategoryFilter

                    if (filter?.selectedCategory == null) {
                        Text("Category")
                    } else {
                        Text("${filter.selectedCategory?.categoryName}")
                    }
                }
                Spacer(modifier = Modifier.width(20.dp))
                Button(
                    onClick = { openFilterDialog(SearchFilter.Type.PRICE) }
                ) {
                    val filter = searchFilters.find { it.type == SearchFilter.Type.PRICE } as? SearchFilter.PriceFilter

                    if (filter?.selectedRange == null) {
                        Text("Price")
                    } else {
                        Text("${filter.selectedRange?.first} ~ ${filter.selectedRange?.second}")
                    }
                }
            }

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(10.dp)
            ) {
                items(searchResult.size) { index ->
                    ProductCard(navController = navController, presentationVM = searchResult[index])
                }
            }
        }
    }
}

@Composable
fun SearchBox(
    keyword: String,
    onValueChange: (String) -> Unit,
    searchAction: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        TextField(
            value = keyword,
            onValueChange = onValueChange,
            placeholder = { Text("검색어를 입력해주세요.") },
            shape = RoundedCornerShape(8.dp),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search,
                keyboardType = KeyboardType.Text,
                capitalization = KeyboardCapitalization.Words,
            ),
            keyboardActions = KeyboardActions(
                onSearch = { searchAction() }
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            maxLines = 1,
            singleLine = true,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "Search Icon"
                )
            }
        )
    }
}