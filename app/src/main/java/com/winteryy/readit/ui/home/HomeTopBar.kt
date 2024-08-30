package com.winteryy.readit.ui.home

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.winteryy.readit.R
import com.winteryy.readit.ui.theme.ReadItTheme
import com.winteryy.readit.ui.theme.Typography
import com.winteryy.readit.ui.theme.theme_grey_whiteSmoke

sealed interface HomeTopBarType {
    data class TextBar(val title: String): HomeTopBarType
    sealed interface SearchBar: HomeTopBarType {
        object Search: SearchBar
        object SearchResult: SearchBar
    }
}

@Composable
fun HomeTopBar(
    homeTopBarType: HomeTopBarType,
    modifier: Modifier = Modifier,
) {

    var searchText by rememberSaveable { mutableStateOf("") }

    when(homeTopBarType) {
        is HomeTopBarType.TextBar -> {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .heightIn(min = 40.dp)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "back button"
                )
                Spacer(modifier = Modifier.size(16.dp))
                Text(
                    text = homeTopBarType.title,
                    style = Typography.headlineSmall.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }
        is HomeTopBarType.SearchBar -> {
            TextField(
                value = searchText,
                onValueChange = { searchText = it },
                leadingIcon = {
                    when(homeTopBarType) {
                        is HomeTopBarType.SearchBar.Search-> {
                            Icon(
                                imageVector = Icons.Filled.Search,
                                contentDescription = "search button"
                            )
                        }
                        is HomeTopBarType.SearchBar.SearchResult -> {
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
                    .padding(16.dp)
                    .heightIn(min = 56.dp)
            )
        }
    }


}

@Preview(showBackground = true)
@Composable
fun ListHomeTopBarPreview() {
    ReadItTheme {
        HomeTopBar(
            HomeTopBarType.TextBar("읽는 중인 책"),
        )
    }
}

@Preview
@Composable
fun FeedHomeTopBarPreview() {
    ReadItTheme {
        HomeTopBar(
            HomeTopBarType.SearchBar.Search,
        )
    }
}

@Preview
@Composable
fun SearchHomeTopBarPreview() {
    ReadItTheme {
        HomeTopBar(
            HomeTopBarType.SearchBar.SearchResult,
        )
    }
}