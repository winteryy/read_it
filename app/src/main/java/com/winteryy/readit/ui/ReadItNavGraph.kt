package com.winteryy.readit.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.winteryy.readit.model.Book
import com.winteryy.readit.ui.bookdetail.BookDetailScreen
import com.winteryy.readit.ui.comment.CommentRoute
import com.winteryy.readit.ui.editcomment.EditCommentScreen
import com.winteryy.readit.ui.home.HomeRoute
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
                navigateToBookDetail = navActions.navigateToBookDetail
            )
        }
        composable(
            route = ReadItDestinations.COMMENT_ROUTE,
        ) { CommentRoute() }
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
        composable(
            route = ReadItDestinations.EDIT_COMMENT_ROUTE,
        ) { backStackEntry ->
            EditCommentScreen(
                isbn = backStackEntry.savedStateHandle.get<String>("isbn") ?: "",
                onBackArrowClicked = { navController.popBackStack() }
            )
        }
    }
}