package com.winteryy.readit.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.winteryy.readit.model.Book

sealed class ReadItNavigationItem(val route: String, val label: String, val icon: ImageVector) {
    data object Main : ReadItNavigationItem(ReadItDestinations.HOME_ROUTE, "홈", Icons.Filled.Home)
    data object Comment : ReadItNavigationItem(ReadItDestinations.COMMENT_ROUTE, "코멘트", Icons.Filled.Create)
    data object MyPage : ReadItNavigationItem(ReadItDestinations.MY_PAGE_ROUTE, "마이 페이지", Icons.Filled.AccountCircle)
}

object ReadItDestinations {
    const val HOME_ROUTE = "home"
    const val COMMENT_ROUTE = "comment"
    const val MY_PAGE_ROUTE = "my_page"
    const val BOOK_DETAIL_ROUTE = "book_detail"
    const val EDIT_COMMENT_ROUTE = "edit_comment"
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
    val navigateToBookDetail: (Book) -> Unit = { book ->
        navController.navigate(ReadItDestinations.BOOK_DETAIL_ROUTE) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }

        navController.currentBackStackEntry?.savedStateHandle?.set("book", book)
    }

    val navigateToEditComment: (String) -> Unit = { isbn ->
        navController.navigate(ReadItDestinations.EDIT_COMMENT_ROUTE) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }

        navController.currentBackStackEntry?.savedStateHandle?.set("isbn", isbn)
    }
}