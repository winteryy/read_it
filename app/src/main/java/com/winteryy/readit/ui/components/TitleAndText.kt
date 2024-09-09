package com.winteryy.readit.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.winteryy.readit.ui.theme.DEFAULT_PADDING
import com.winteryy.readit.ui.theme.ReadItTheme
import com.winteryy.readit.ui.theme.Typography

@Composable
fun TitleAndText(
    title: String,
    text: String,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        color = Color.White
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = DEFAULT_PADDING, vertical = 20.dp)
        ) {
            Text(
                text = title,
                style = Typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.size(16.dp))
            Text(
                text = text,
                style = Typography.bodySmall
            )
        }

    }
}

@Preview
@Composable
fun TitleAndTextPreview() {
    ReadItTheme {
        TitleAndText(
            title = "책 소개",
            text = "테스트용 문장 테스트용 문장 테스트용 문장 테스트용 문장 테스트용 문장 테스트용 문장 테스트용 문장 테스트용 문장 테스트용 문장"
        )
    }
}
