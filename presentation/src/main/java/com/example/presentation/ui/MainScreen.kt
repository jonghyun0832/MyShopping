package com.example.presentation.ui

import android.graphics.drawable.Icon
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.presentation.ui.basket.BasketScreen
import com.example.presentation.ui.category.CategoryScreen
import com.example.presentation.ui.main.LikeScreen
import com.example.presentation.ui.main.MainCategoryScreen
import com.example.presentation.ui.main.MainHomeScreen
import com.example.presentation.ui.main.MyPageScreen
import com.example.presentation.ui.product_detail.ProductDetailScreen
import com.example.presentation.ui.search.SearchScreen
import com.example.presentation.util.NavigationUtils
import com.example.presentation.viewmodel.MainViewModel
import com.google.android.gms.auth.api.signin.GoogleSignInClient

@Composable
fun MainScreen(googleSignInClient: GoogleSignInClient) {
    val viewModel = hiltViewModel<MainViewModel>()
    val snackbarHostState = remember { SnackbarHostState() }
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            MainHeader(viewModel = viewModel, navController = navController, currentRoute)
        },
        bottomBar = {
            if (MainNav.isMainRoute(currentRoute)) {
                MainNavigationBar(navController, currentRoute)
            }
        }
    ) { paddings ->
        MainNavigationScreen(
            mainViewModel = viewModel,
            navController = navController,
            googleSignInClient = googleSignInClient,
            paddings = paddings
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainHeader(viewModel: MainViewModel, navController: NavHostController, currentRoute: String?) {
    TopAppBar(
        title = {
            Text(text = NavigationUtils.findDestination(currentRoute).title)
        },
        navigationIcon = {
            if(!MainNav.isMainRoute(currentRoute)) {
                IconButton(
                    onClick = { navController.popBackStack() }
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back Icon"
                    )
                }
            } else {
                null
            }
        },
        actions = {
            if (MainNav.isMainRoute(currentRoute)) {
                IconButton(onClick = {
                    viewModel.openSearchForm(navController = navController)
                }) {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = "Search Icon"
                    )
                }
                IconButton(onClick = {
                    viewModel.openBasket(navHostController = navController)
                }) {
                    Icon(
                        imageVector = Icons.Filled.ShoppingCart,
                        contentDescription = "Basket Icon"
                    )
                }
            }
        }
    )
}

@Composable
fun MainNavigationBar(navController: NavHostController, currentRoute: String?) {
    val navigationItems = listOf(
        MainNav.Home,
        MainNav.Category,
        MainNav.Like,
        MainNav.MyPage
    )

    NavigationBar {
        navigationItems.forEach { item ->
            NavigationBarItem(
                selected = currentRoute == item.route,
                onClick = {
                    NavigationUtils.navigate(
                        controller = navController,
                        routeName = item.route,
                        backStackRouteName = navController.graph.startDestinationRoute
                    )
                },
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.title
                    )
                },
                label = {
                    Text(text = item.title)
                }
            )
        }
    }
}

@Composable
fun MainNavigationScreen(
    mainViewModel: MainViewModel,
    navController: NavHostController,
    googleSignInClient: GoogleSignInClient,
    paddings: PaddingValues
) {
    NavHost(
        navController = navController,
        startDestination = MainNav.Home.route,
        modifier = Modifier.padding(paddings)
    ) {
        composable(
            route = MainNav.Home.route,
            deepLinks = MainNav.Home.deepLinks
        ) {
            MainHomeScreen(navController, mainViewModel)
        }
        composable(
            route = MainNav.Category.route,
            deepLinks = MainNav.Category.deepLinks
        ) {
            MainCategoryScreen(mainViewModel, navController)
        }
        composable(
            route = MainNav.MyPage.route,
            deepLinks = MainNav.MyPage.deepLinks
        ) {
            MyPageScreen(viewModel = mainViewModel, googleSignInClient = googleSignInClient)
        }
        composable(
            route = MainNav.Like.route,
            deepLinks = MainNav.Like.deepLinks
        ) {
            LikeScreen(navController = navController, viewModel = mainViewModel)
        }
        composable(
            route = BasketNav.route,
            deepLinks = BasketNav.deepLinks
        ) {
            BasketScreen()
        }
        composable(
            route = CategoryNav.routeWithArgName(),
            arguments = CategoryNav.arguments,
            deepLinks = CategoryNav.deepLinks
        ) {
            val category = CategoryNav.findArgument(it)
            if (category != null) {
                CategoryScreen(navController = navController, category = category)
            }
        }
        composable(
            route = ProductDetailNav.routeWithArgName(),
            arguments = ProductDetailNav.arguments,
            deepLinks = ProductDetailNav.deepLinks
        ) {
            val productId = ProductDetailNav.findArgument(it)
            if (productId != null) {
                ProductDetailScreen(productId = productId)
            }
        }
        composable(
            route = SearchNav.route,
            deepLinks = SearchNav.deepLinks
        ) {
            SearchScreen(navController = navController)
        }
    }
}