package com.winteryy.readit.ui

import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination

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