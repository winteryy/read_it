package com.winteryy.readit.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.winteryy.readit.ui.theme.DEFAULT_PADDING
import com.winteryy.readit.ui.theme.ReadItTheme
import com.winteryy.readit.ui.theme.Typography
import com.winteryy.readit.ui.theme.theme_color_dodgerBlue

@Composable
fun TextTopBar(
    modifier: Modifier = Modifier,
    title: String = "",
    onBackArrowClicked: () -> Unit = {},
    backButtonImageVector: ImageVector = Icons.AutoMirrored.Filled.ArrowBack,
    trailingText: String? = null,
    trailingTextCallback: (() -> Unit)? = null,
) {
    Surface(
        modifier = modifier,
        color = Color.White
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(DEFAULT_PADDING)
                .heightIn(min = 40.dp)
        ) {
            Icon(
                imageVector = backButtonImageVector,
                contentDescription = "back button",
                modifier = Modifier.clickable { onBackArrowClicked() }
            )
            Spacer(modifier = Modifier.size(16.dp))
            Text(
                text = title,
                style = Typography.headlineSmall.copy(
                    fontWeight = FontWeight.Bold
                )
            )

            trailingText?.let {
                Spacer(
                    modifier = Modifier
                        .weight(1f)
                )
                Box(
                    modifier = Modifier
                        .size(width = 60.dp, height = 40.dp)
                        .let { originModifier ->
                            trailingTextCallback?.let { callBack ->
                                originModifier.clickable {
                                    callBack()
                                }
                            } ?: originModifier
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = it,
                        color = theme_color_dodgerBlue,
                    )
                }

            }

        }
    }
}

@Preview
@Composable
fun TextTopBarPreview() {
    ReadItTheme {
        TextTopBar(
            title = "for test",
            trailingText = "확인"
        )
    }
}