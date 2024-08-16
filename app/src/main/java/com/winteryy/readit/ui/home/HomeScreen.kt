package com.winteryy.readit.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.winteryy.readit.ui.theme.ReadItTheme

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = viewModel()
) {
    Column {
        Text(text = "Home")
        Button(
            onClick = {
                //viewModel.repoTest()
            },
            colors = ButtonDefaults.buttonColors().copy(
                containerColor = Color.DarkGray
            )
        ) {
            Text(text = "test button")
        }

    }


}

@Preview
@Composable
fun PreviewHomeScreen() {
    ReadItTheme {
        HomeScreen()
    }
}