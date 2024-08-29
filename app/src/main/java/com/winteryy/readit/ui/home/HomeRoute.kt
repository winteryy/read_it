package com.winteryy.readit.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

enum class HomeScreenType {
    FEED, SEARCH, SEARCH_RESULT
}

@Composable
fun HomeRoute(
    modifier: Modifier = Modifier
) {

    var homeScreenType by rememberSaveable { mutableStateOf(HomeScreenType.FEED) }

    Column(
        modifier = modifier
    ) {
        HomeSearchBar(
            homeScreenType,
            Modifier.padding(16.dp),
        )
        HorizontalDivider()

        when(homeScreenType) {
            HomeScreenType.FEED -> {
                HomeFeedScreen()
            }
            HomeScreenType.SEARCH -> TODO()
            HomeScreenType.SEARCH_RESULT -> TODO()
        }
    }

}