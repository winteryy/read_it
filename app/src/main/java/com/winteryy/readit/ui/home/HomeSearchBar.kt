package com.winteryy.readit.ui.home

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.winteryy.readit.R
import com.winteryy.readit.ui.theme.ReadItTheme
import com.winteryy.readit.ui.theme.theme_grey_gunPowder
import com.winteryy.readit.ui.theme.theme_grey_whiteSmoke

@Composable
fun HomeSearchBar(
    homeScreenType: HomeScreenType,
    modifier: Modifier = Modifier,
) {

    var searchText by rememberSaveable { mutableStateOf("") }

    TextField(
        value = searchText,
        onValueChange = { searchText = it },
        leadingIcon = {
            when(homeScreenType) {
                HomeScreenType.FEED -> {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = "search button"
                    )
                }
                HomeScreenType.SEARCH, HomeScreenType.SEARCH_RESULT -> {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "back button"
                    )
                }
            }
        },
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = theme_grey_whiteSmoke,
            focusedContainerColor = theme_grey_whiteSmoke,
            unfocusedIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent
        ),
        placeholder = {
            Text(text = stringResource(R.string.search_place_holder))
        },
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 56.dp)
    )
}

@Preview
@Composable
fun FeedHomeSearchBarPreview() {
    ReadItTheme {
        HomeSearchBar(
            HomeScreenType.FEED
        )
    }
}

@Preview
@Composable
fun SearchHomeSearchBarPreview() {
    ReadItTheme {
        HomeSearchBar(
            HomeScreenType.SEARCH
        )
    }
}