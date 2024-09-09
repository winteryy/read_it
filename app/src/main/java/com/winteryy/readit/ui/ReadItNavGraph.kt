package com.winteryy.readit.ui

import android.os.Build
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.winteryy.readit.model.Book
import com.winteryy.readit.ui.bookdetail.BookDetailScreen
import com.winteryy.readit.ui.comment.CommentScreen
import com.winteryy.readit.ui.home.HomeRoute
import com.winteryy.readit.ui.home.HomeUiState
import com.winteryy.readit.ui.mypage.MyPageScreen

@Composable
fun ReadItNavGraph(
    navActions: ReadItNavigationActions,
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
        ) { HomeRoute(
                navActions.navigateToBookDetail
            )
        }
        composable(
            route = ReadItDestinations.COMMENT_ROUTE,
        ) { CommentScreen() }
        composable(
            route = ReadItDestinations.MY_PAGE_ROUTE,
        ) { MyPageScreen() }
        composable(
            route = ReadItDestinations.BOOK_DETAIL_ROUTE,
        ) { backStackEntry ->
                BookDetailScreen(
                    book = backStackEntry.savedStateHandle.get<Book>("book"),
                    onBackArrowClicked = { navController.popBackStack() }
                )

        }
    }
}