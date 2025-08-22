package com.example.presentation.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDeepLink
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.example.domain.model.Category
import com.example.domain.model.Product
import com.example.presentation.ui.NavigationRouteName.BASKET
import com.example.presentation.ui.NavigationRouteName.CATEGORY
import com.example.presentation.ui.NavigationRouteName.DEEP_LINK_SCHEME
import com.example.presentation.ui.NavigationRouteName.MAIN_CATEGORY
import com.example.presentation.ui.NavigationRouteName.MAIN_HOME
import com.example.presentation.ui.NavigationRouteName.MAIN_LIKE
import com.example.presentation.ui.NavigationRouteName.MAIN_MY_PAGE
import com.example.presentation.ui.NavigationRouteName.PRODUCT_DETAIL
import com.example.presentation.ui.NavigationRouteName.SEARCH
import com.example.presentation.util.GsonUtils

sealed class MainNav(
    override val route: String,
    override val title: String,
    val icon: ImageVector,
) : Destination {
    object Home : MainNav(MAIN_HOME, NavigationTitle.MAIN_HOME, Icons.Filled.Home)
    object Category : MainNav(MAIN_CATEGORY, NavigationTitle.MAIN_CATEGORY, Icons.Filled.Star)
    object MyPage : MainNav(MAIN_MY_PAGE, NavigationTitle.MAIN_MY_PAGE, Icons.Filled.AccountBox)
    object Like : MainNav(MAIN_LIKE, NavigationTitle.MAIN_LIKE, Icons.Filled.Favorite)

    override val deepLinks: List<NavDeepLink> = listOf(navDeepLink { uriPattern = "$DEEP_LINK_SCHEME$route" })

    companion object {
        fun isMainRoute(route: String?): Boolean {
            return when (route) {
                MAIN_HOME, MAIN_LIKE, MAIN_CATEGORY, MAIN_MY_PAGE -> true
                else -> false
            }
        }
    }
}


object SearchNav : Destination {
    override val route: String
        get() = NavigationRouteName.SEARCH
    override val title: String
        get() = NavigationTitle.SEARCH
    override val deepLinks: List<NavDeepLink>
        get() = listOf(
            navDeepLink { uriPattern = "$DEEP_LINK_SCHEME$route" }
        )
}

object BasketNav : Destination {
    override val route: String
        get() = NavigationRouteName.BASKET
    override val title: String
        get() = NavigationTitle.BASKET
    override val deepLinks: List<NavDeepLink>
        get() = listOf(
            navDeepLink { uriPattern = "$DEEP_LINK_SCHEME$route" }
        )
}

object PurchaseHistoryNav : Destination {
    override val route: String
        get() = NavigationRouteName.PURCHASE_HISTORY
    override val title: String
        get() = NavigationTitle.PURCHASE_HISTORY
    override val deepLinks: List<NavDeepLink>
        get() = listOf(
            navDeepLink { uriPattern = "$DEEP_LINK_SCHEME$route" }
        )
}

object CategoryNav : DestinationArg<Category> {
    override val route: String
        get() = CATEGORY
    override val title: String
        get() = NavigationTitle.CATEGORY
    override val deepLinks: List<NavDeepLink>
        get() = listOf(navDeepLink { uriPattern = "$DEEP_LINK_SCHEME$route/{${argName}}" })
    override val argName: String
        get() = "category"
    override val arguments: List<NamedNavArgument>
        get() = listOf(navArgument(argName) { type = NavType.StringType })

    override fun navigateWithArg(item: Category): String {
        val arg = GsonUtils.toJson(item)
        return "$route/$arg"
    }

    override fun findArgument(navBackStackEntry: NavBackStackEntry): Category? {
        val categoryString = navBackStackEntry.arguments?.getString(argName)
        return GsonUtils.fromJson<Category>(categoryString)
    }
}

object ProductDetailNav : DestinationArg<String> {
    override val route: String
        get() = PRODUCT_DETAIL
    override val title: String
        get() = NavigationTitle.PRODUCT_DETAIL
    override val deepLinks: List<NavDeepLink>
        get() = listOf(navDeepLink { uriPattern = "$DEEP_LINK_SCHEME$route/{${argName}}" })
    override val argName: String
        get() = "productId"
    override val arguments: List<NamedNavArgument>
        get() = listOf(navArgument(argName) { type = NavType.StringType })

    override fun navigateWithArg(item: String): String {
        val arg = GsonUtils.toJson(item)
        return "$route/$arg"
    }

    override fun findArgument(navBackStackEntry: NavBackStackEntry): String? {
        val productId = navBackStackEntry.arguments?.getString(argName)
        return GsonUtils.fromJson<String>(productId)
    }
}

interface Destination {
    val route: String
    val title: String
    val deepLinks: List<NavDeepLink>
}

interface DestinationArg<T> : Destination {
    val argName: String
    val arguments: List<NamedNavArgument>

    fun routeWithArgName() = "$route/{$argName}"
    fun navigateWithArg(item: T): String
    fun findArgument(navBackStackEntry: NavBackStackEntry): T?
}

object NavigationRouteName {
    const val DEEP_LINK_SCHEME = "myshop://"
    const val MAIN_HOME = "main_home"
    const val MAIN_CATEGORY = "main_category"
    const val MAIN_MY_PAGE = "main_my_page"
    const val MAIN_LIKE = "main_like"
    const val CATEGORY = "category"
    const val PRODUCT_DETAIL = "product_detail"
    const val SEARCH = "search"
    const val BASKET = "basket"
    const val PURCHASE_HISTORY = "purchase_history"
}

object NavigationTitle {
    const val MAIN_HOME = "홈"
    const val MAIN_CATEGORY = "카테고리"
    const val MAIN_MY_PAGE = "마이페이지"
    const val MAIN_LIKE = "좋아요 품목"
    const val CATEGORY = "카테고리별 보기"
    const val PRODUCT_DETAIL = "상품 상세페이지"
    const val SEARCH = "검색"
    const val BASKET = "장바구니"
    const val PURCHASE_HISTORY = "결제내역"
}