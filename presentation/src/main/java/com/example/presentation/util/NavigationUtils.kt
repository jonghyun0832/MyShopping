package com.example.presentation.util

import android.net.Uri
import android.os.Parcelable
import androidx.navigation.NavHostController
import com.google.gson.Gson
import androidx.core.net.toUri
import com.example.domain.model.Category

object NavigationUtils {
    fun navigate(
        controller: NavHostController,
        routeName: String,
        args: Any? = null,
        backStackRouteName: String? = null,
        isLaunchSingleTop: Boolean = true,
        needToRestoreState: Boolean = true
    ) {
        var argument = ""
        if (args != null) {
            when (args) {
                is Parcelable -> {
                    argument = String.format("/%s", Gson().toJson(args).toUri())
                }
                is Category -> {
                    argument = String.format("/%s", Gson().toJson(args).toUri())
                }

            }
        }
        controller.navigate("$routeName$argument") {
            if (backStackRouteName != null) {
                popUpTo(backStackRouteName) { saveState = true }
            }
            launchSingleTop = isLaunchSingleTop
            restoreState = needToRestoreState
        }
    }
}