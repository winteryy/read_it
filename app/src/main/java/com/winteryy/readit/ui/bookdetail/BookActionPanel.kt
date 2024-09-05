package com.winteryy.readit.ui.bookdetail

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.winteryy.readit.R
import com.winteryy.readit.model.BookSaveStatus
import com.winteryy.readit.ui.components.RatingBar
import com.winteryy.readit.ui.theme.ReadItTheme
import com.winteryy.readit.ui.theme.theme_color_malibu
import com.winteryy.readit.ui.theme.theme_grey_black

data class PanelItemResource(
    val title: String,
    val defaultIcon: ImageVector,
    val checkedIcon: ImageVector
)

@Composable
fun BookActionPanel(
    rating: Float,
    bookSaveStatus: BookSaveStatus,
    hasComment: Boolean,
    modifier: Modifier = Modifier,
    onRatingChanged: (Float) -> Unit = {},
    onWishButtonClicked: (Boolean) -> Unit = {},
    onCommentButtonClicked: (Boolean) -> Unit = {},
    onReadingButtonClicked: (Boolean) -> Unit = {}
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        RatingBar(
            rating = rating,
            onRatingChanged = onRatingChanged,
            modifier = Modifier
                .padding(vertical = 16.dp)
        )
        HorizontalDivider(
            thickness = 0.5.dp
        )
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            PanelItem(
                panelItemResource = PanelItemResource(
                    title = stringResource(R.string.wish_button_title),
                    defaultIcon = Icons.Filled.Add,
                    checkedIcon = Icons.Filled.Done,
                ),
                isChecked = bookSaveStatus == BookSaveStatus.WISH,
                onClicked = onWishButtonClicked
            )
            PanelItem(
                panelItemResource = PanelItemResource(
                    title = stringResource(R.string.comment_button_title),
                    defaultIcon = Icons.Filled.Create,
                    checkedIcon = Icons.Filled.Create,
                ),
                isChecked = hasComment,
                onClicked = onCommentButtonClicked
            )
            PanelItem(
                panelItemResource = PanelItemResource(
                    title = stringResource(R.string.reading_button_title),
                    defaultIcon = ImageVector.vectorResource(id = R.drawable.baseline_visibility_off_24),
                    checkedIcon = ImageVector.vectorResource(id = R.drawable.baseline_visibility_24),
                ),
                isChecked = bookSaveStatus == BookSaveStatus.READING,
                onClicked = onReadingButtonClicked
            )
        }

    }
}

@Composable
fun PanelItem(
    panelItemResource: PanelItemResource,
    modifier: Modifier = Modifier,
    isChecked: Boolean = false,
    onClicked: (Boolean) -> Unit = {}
) {
    Surface(
        modifier = modifier
            .size(120.dp, 80.dp)
            .clickable {
                onClicked(isChecked)
            }
    ) {
        Column(
            modifier = Modifier,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if(isChecked) {
                Icon(
                    imageVector = panelItemResource.checkedIcon,
                    contentDescription = panelItemResource.title,
                    tint = theme_color_malibu
                )
                Text(
                    text = panelItemResource.title,
                    color = theme_color_malibu
                )
            } else {
                Icon(
                    imageVector = panelItemResource.defaultIcon,
                    contentDescription = panelItemResource.title,
                    tint = theme_grey_black
                )
                Text(
                    text = panelItemResource.title,
                    color = theme_grey_black
                )
            }

        }
    }
}

@Composable
@Preview(showBackground = true)
fun BookActionPanelPreview() {
    ReadItTheme {
        BookActionPanel(
            2.5f,
            BookSaveStatus.WISH,
            false
        )
    }
}

@Composable
@Preview(showBackground = true)
fun PanelItemPreview() {
    ReadItTheme {
        PanelItem(
            panelItemResource = PanelItemResource(
                title = "보는 중",
                defaultIcon = ImageVector.vectorResource(id = R.drawable.baseline_visibility_off_24),
                checkedIcon = ImageVector.vectorResource(id = R.drawable.baseline_visibility_24)
            ),
            isChecked = true
        )
    }
}