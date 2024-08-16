package com.winteryy.readit.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.winteryy.readit.ui.comment.CommentScreen
import com.winteryy.readit.ui.home.HomeScreen
import com.winteryy.readit.ui.mypage.MyPageScreen
import androidx.hilt.navigation.compose.hiltViewModel
import com.winteryy.readit.ui.home.HomeViewModel

@Composable
fun ReadItNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = ReadItDestinations.HOME_ROUTE
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(
            route = ReadItDestinations.HOME_ROUTE,
        ) {
            val viewModel = hiltViewModel<HomeViewModel>()
            HomeScreen(viewModel) }
        composable(
            route = ReadItDestinations.COMMENT_ROUTE,
        ) { CommentScreen() }
        composable(
            route = ReadItDestinations.MY_PAGE_ROUTE,
        ) { MyPageScreen() }
    }
}