package com.example.presentation.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.myshopping.ui.theme.MyShoppingTheme
import com.example.presentation.ui.main.MainInsideScreen
import com.example.presentation.viewmodel.MainViewModel

sealed class MainNavigationItem(val route: String, val name: String, val icon : ImageVector) {
    object Main : MainNavigationItem("Main", "Main", Icons.Filled.Home)
    object Category : MainNavigationItem("Category", "Category", Icons.Filled.Star)
    object MyPage : MainNavigationItem("MyPage", "MyPage", Icons.Filled.AccountBox)
}

@Composable
fun MainScreen() {
    val viewModel = hiltViewModel<MainViewModel>()
    val snackbarHostState = remember { SnackbarHostState() }
    val navController = rememberNavController()

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            Header(viewModel)
        },
        bottomBar = {
            MainNavigationBar(navController)
        }
    ) { paddings ->
        MainNavigationScreen(mainViewModel = viewModel, navController = navController, paddings = paddings)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Header(viewModel : MainViewModel) {
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
fun MainNavigationBar(navController: NavHostController) {
    val navigationItems = listOf(
        MainNavigationItem.Main,
        MainNavigationItem.Category,
        MainNavigationItem.MyPage
    )

    NavigationBar(

    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

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
fun MainNavigationScreen(mainViewModel: MainViewModel, navController: NavHostController, paddings: PaddingValues) {
    NavHost(
        navController = navController,
        startDestination = MainNavigationItem.Main.route,
        modifier = Modifier.padding(paddings)
    ) {
        composable(MainNavigationItem.Main.route) {
            MainInsideScreen(mainViewModel)
        }
        composable(MainNavigationItem.Category.route) {
            Text(text = "Hello Category")
        }
        composable(MainNavigationItem.MyPage.route) {
            Text(text = "Hello MyPage")
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