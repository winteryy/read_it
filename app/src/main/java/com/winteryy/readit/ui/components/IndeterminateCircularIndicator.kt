package com.winteryy.readit.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.winteryy.readit.ui.theme.ReadItTheme
import com.winteryy.readit.ui.theme.theme_color_dodgerBlue

@Composable
fun IndeterminateCircularIndicator(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            color = theme_color_dodgerBlue
        )
    }
}

@Composable
@Preview(showBackground = true)
fun IndeterminateCircularIndicatorPreview() {
    ReadItTheme {
        IndeterminateCircularIndicator()
    }
}