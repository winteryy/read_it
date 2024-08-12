package com.winteryy.readit.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination

sealed class ReadItNavigationItem(val route: String, val label: String, val icon: ImageVector) {
    data object Main : ReadItNavigationItem(ReadItDestinations.HOME_ROUTE, "홈", Icons.Filled.Home)
    data object Comment : ReadItNavigationItem(ReadItDestinations.COMMENT_ROUTE, "코멘트", Icons.Filled.Create)
    data object MyPage : ReadItNavigationItem(ReadItDestinations.MY_PAGE_ROUTE, "마이 페이지", Icons.Filled.AccountCircle)
}

object ReadItDestinations {
    const val HOME_ROUTE = "home"
    const val COMMENT_ROUTE = "comment"
    const val MY_PAGE_ROUTE = "my_page"
}

class ReadItNavigationActions(navController: NavController) {
    val navigateToHome: () -> Unit = {
        navController.navigate(ReadItDestinations.HOME_ROUTE) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }
    val navigateToComment: () -> Unit = {
        navController.navigate(ReadItDestinations.COMMENT_ROUTE) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }
    val navigateToMyPage: () -> Unit = {
        navController.navigate(ReadItDestinations.MY_PAGE_ROUTE) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }
}