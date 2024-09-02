package com.winteryy.readit.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.winteryy.readit.ui.theme.Typography

@Composable
fun TextTopBar(
    modifier: Modifier = Modifier,
    title: String = "",
    onBackArrowClicked: () -> Unit = {}
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
            .heightIn(min = 40.dp)
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
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
    }
}