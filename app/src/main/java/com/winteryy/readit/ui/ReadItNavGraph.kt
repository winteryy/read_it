package com.winteryy.readit.ui

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
    snackbarHostState: SnackbarHostState,
    navController: NavHostController = rememberNavController(),
    startDestination: String = ReadItDestinations.HOME_ROUTE
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
        modifier = modifier
            .background(Color.White)
    ) {
        composable(
            route = ReadItDestinations.HOME_ROUTE,
            enterTransition = {
                fadeIn(animationSpec = tween(500))
            },
            exitTransition = {
                fadeOut(animationSpec = tween(500))
            }
        ) { HomeRoute(
                navigateToBookDetail = navActions.navigateToBookDetail,
                snackbarHostState = snackbarHostState
            )
        }
        composable(
            route = ReadItDestinations.COMMENT_ROUTE,
            enterTransition = {
                fadeIn(animationSpec = tween(500))
            },
            exitTransition = {
                fadeOut(animationSpec = tween(500))
            }
        ) {
            CommentRoute(
                snackbarHostState = snackbarHostState,
                navigateToEditComment = { navActions.navigateToEditComment(it) }
            )
        }
        composable(
            route = ReadItDestinations.MY_PAGE_ROUTE,
        ) { MyPageScreen() }
        composable(
            route = ReadItDestinations.BOOK_DETAIL_ROUTE,
            enterTransition = { fadeIn(animationSpec = tween(500)) },
            exitTransition = { fadeOut(animationSpec = tween(500)) },
            popExitTransition = { null }

        ) { backStackEntry ->
            BookDetailScreen(
                book = backStackEntry.savedStateHandle.get<Book>(ReadItNavSavedStateHandleKey.BOOK),
                snackbarHostState = snackbarHostState,
                onBackArrowClicked = { navController.popBackStack() },
                onCommentButtonClicked = navActions.navigateToEditComment
            )
        }
        composable(
            route = ReadItDestinations.EDIT_COMMENT_ROUTE,
            enterTransition = { fadeIn(animationSpec = tween(500)) }
        ) { backStackEntry ->
            EditCommentScreen(
                isbn = backStackEntry.savedStateHandle.get<String>(ReadItNavSavedStateHandleKey.ISBN) ?: "",
                snackbarHostState = snackbarHostState,
                onBackArrowClicked = { navController.popBackStack() }
            )
        }
    }
}