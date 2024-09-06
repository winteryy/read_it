package com.winteryy.readit.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.winteryy.readit.ui.theme.ReadItTheme
import com.winteryy.readit.ui.theme.theme_grey_whiteSmoke

@Composable
fun ReadItApp() {

    val navController = rememberNavController()
    val navActions = remember(navController) {
        ReadItNavigationActions(navController)
    }

    Scaffold(
        bottomBar = { MainBottomNavigationBar(navController, navActions) }
    ) { paddingValues ->
        ReadItNavGraph(
            navActions,
            Modifier.padding(paddingValues),
            navController,
            ReadItDestinations.HOME_ROUTE
        )
    }

}

@Composable
fun MainBottomNavigationBar(
    navController: NavHostController,
    navActions: ReadItNavigationActions
    ) {
    val bottomNavigationItems = listOf(
        ReadItNavigationItem.Main,
        ReadItNavigationItem.Comment,
        ReadItNavigationItem.MyPage
    )

    NavigationBar(
        containerColor = theme_grey_whiteSmoke,
    ) {

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
                icon = { Icon(imageVector = item.icon, contentDescription = item.label) },
                label = { Text(text = item.label) }
                )
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