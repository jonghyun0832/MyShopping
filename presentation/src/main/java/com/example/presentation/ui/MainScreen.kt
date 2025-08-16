package com.example.presentation.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.domain.model.Category
import com.example.domain.model.Product
import com.example.myshopping.ui.theme.MyShoppingTheme
import com.example.presentation.ui.category.CategoryScreen
import com.example.presentation.ui.main.MainCategoryScreen
import com.example.presentation.ui.main.MainHomeScreen
import com.example.presentation.ui.product_detail.ProductDetailScreen
import com.example.presentation.viewmodel.MainViewModel
import com.google.gson.Gson

@Composable
fun MainScreen() {
    val viewModel = hiltViewModel<MainViewModel>()
    val snackbarHostState = remember { SnackbarHostState() }
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            Header(viewModel)
        },
        bottomBar = {
            if (NavigationItem.MainNav.isMainRoute(currentRoute)) {
                MainNavigationBar(navController, currentRoute)
            }
        }
    ) { paddings ->
        MainNavigationScreen(
            mainViewModel = viewModel,
            navController = navController,
            paddings = paddings
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Header(viewModel: MainViewModel) {
    TopAppBar(
        title = {
            Text(text = "My Shoppings")
        },
        actions = {
            IconButton(onClick = {
                // TODO : 검색
            }) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "Search Icon"
                )
            }
        }
    )
}

@Composable
fun MainNavigationBar(navController: NavHostController, currentRoute: String?) {
    val navigationItems = listOf(
        NavigationItem.MainNav.Home,
        NavigationItem.MainNav.Category,
        NavigationItem.MainNav.MyPage
    )

    NavigationBar {
        navigationItems.forEach { item ->
            NavigationBarItem(
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        navController.graph.startDestinationRoute?.let { route ->
                            popUpTo(route) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.name
                    )
                },
                label = {
                    Text(text = item.name)
                }
            )
        }
    }
}

@Composable
fun MainNavigationScreen(
    mainViewModel: MainViewModel,
    navController: NavHostController,
    paddings: PaddingValues
) {
    NavHost(
        navController = navController,
        startDestination = NavigationRouteName.MAIN_HOME,
        modifier = Modifier.padding(paddings)
    ) {
        composable(NavigationRouteName.MAIN_HOME) {
            MainHomeScreen(navController, mainViewModel)
        }
        composable(NavigationRouteName.MAIN_CATEGORY) {
            MainCategoryScreen(mainViewModel, navController)
        }
        composable(NavigationRouteName.MAIN_MY_PAGE) {
            Text(text = "Hello MyPage")
        }
        composable(
            route = NavigationRouteName.CATEGORY + "/{category}",
            arguments = listOf(navArgument("category") { type = NavType.StringType })
        ) {
            val categoryString = it.arguments?.getString("category")
            val category = Gson().fromJson(categoryString, Category::class.java)
            if (category != null) {
                CategoryScreen(navController = navController, category = category)
            }
        }
        composable(
            route = NavigationRouteName.PRODUCT_DETAIL + "/{productId}",
            arguments = listOf(navArgument("productId") { type = NavType.StringType })
        ) {
            val productId = it.arguments?.getString("productId")
            if (productId != null) {
                ProductDetailScreen(productId = productId)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyShoppingTheme {
        MainScreen()
    }
}