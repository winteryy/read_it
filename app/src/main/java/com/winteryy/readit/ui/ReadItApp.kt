package com.winteryy.readit.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.winteryy.readit.R
import com.winteryy.readit.ui.theme.ReadItTheme

sealed class MainNavigationItem(val route: String, val name: String) {
    data object Main : MainNavigationItem(ReadItDestinations.HOME_ROUTE, "main")
    data object Comment : MainNavigationItem(ReadItDestinations.COMMENT_ROUTE, "comment")
    data object MyPage : MainNavigationItem(ReadItDestinations.MY_PAGE_ROUTE, "my_page")
}

@Composable
fun ReadItApp() {

    val navController = rememberNavController()

    Scaffold(
        bottomBar = { MainBottomNavigationBar(navController) }
    ) { paddingValues ->
        ReadItNavGraph(
            Modifier.padding(paddingValues),
            navController,
            ReadItDestinations.HOME_ROUTE
        )
    }

}

@Composable
fun MainBottomNavigationBar(navController: NavHostController) {
    val bottomNavigationItems = listOf(
        MainNavigationItem.Main,
        MainNavigationItem.Comment,
        MainNavigationItem.MyPage
    )

    NavigationBar(
        containerColor = colorResource(id = R.color.teal_200),
    ) {
        val navActions = remember(navController) {
            ReadItNavigationActions(navController)
        }

        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route ?: ReadItDestinations.HOME_ROUTE

        bottomNavigationItems.forEach { item ->
            NavigationBarItem(
                selected = currentRoute == item.route,
                onClick = {
                    when (item.route) {
                        ReadItDestinations.HOME_ROUTE -> navActions.navigateToHome()
                        ReadItDestinations.COMMENT_ROUTE -> navActions.navigateToComment()
                        ReadItDestinations.MY_PAGE_ROUTE -> navActions.navigateToMyPage()
                    }
                },
                icon = { Icon(imageVector = Icons.Filled.Add, contentDescription = "test") })
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ReadItAppPreview() {
    ReadItTheme {
        ReadItApp()
    }
}