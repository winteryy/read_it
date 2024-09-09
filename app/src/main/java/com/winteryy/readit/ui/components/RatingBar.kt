package com.winteryy.readit.ui.components

import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.boundsInParent
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.winteryy.readit.ui.theme.ReadItTheme
import com.winteryy.readit.ui.theme.theme_color_malibu
import kotlin.math.round

@Composable
fun RatingBar(
    rating: Float,
    onRatingChanged: (Float) -> Unit,
    modifier: Modifier = Modifier
) {
    val bounds = remember { mutableMapOf<Int, Rect>() }

    Row(
        modifier = modifier
            .then(
            Modifier.pointerInput(Unit) {
                detectHorizontalDragGestures { change, _ ->
                    val (ind, rect) = bounds.entries.find { (_, rect) ->
                        rect.contains(Offset(change.position.x, 0f))
                    } ?: return@detectHorizontalDragGestures

                    val baseRating = (ind -1)
                    val normalizedX = (change.position.x - rect.left)
                    val fractionalRating = (normalizedX/rect.width).coerceIn(0f, 1f)

                    val roundedRating = round(fractionalRating/0.5f) * 0.5f

                    onRatingChanged(baseRating + roundedRating)
                }
            } )
    ) {
        for(ind in 1..5) {
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .onGloballyPositioned { layoutCoordinates ->
                        bounds[ind] = layoutCoordinates.boundsInParent()
                    }
                    .then(
                        Modifier.pointerInput(rating) {
                            detectTapGestures { offset ->
                                val curX = offset.x
                                val boxWidth = size.width

                                val newRating = if (curX < boxWidth / 2) {
                                    ind - 0.5f
                                } else {
                                    ind.toFloat()
                                }

                                if (newRating == rating) {
                                    onRatingChanged(0f)
                                } else {
                                    onRatingChanged(newRating)
                                }
                            }
                        }
                    )
            ) {
                Icon(
                    tint = Color.LightGray,
                    imageVector = Icons.Filled.Star,
                    contentDescription = "unrated star",
                    modifier = Modifier
                        .matchParentSize()
                )

                val fillWidthRatio = when {
                    (rating >= ind) -> 1f
                    (rating <= ind-1) -> 0f
                    else -> 0.5f
                }

                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .clip(ClippingShape(fillWidthRatio))
                ) {
                    Icon(
                        tint = theme_color_malibu,
                        imageVector = Icons.Filled.Star,
                        contentDescription = "rated star",
                        modifier = Modifier
                            .matchParentSize()
                    )
                }
            }
        }
    }
}

private class ClippingShape(private val fillWidthRatio: Float): Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val clippingRect = Rect(Offset.Zero, Size(size.width * fillWidthRatio, size.height))
        return Outline.Rectangle(clippingRect)
    }
}

@Preview(showBackground = true)
@Composable
fun RatingBarPreview() {
    ReadItTheme {
        Surface {
            val rating = remember { mutableFloatStateOf(2.5f) }
            RatingBar(
                rating.floatValue,
                {
                    rating.floatValue = it
                }
            )
        }
    }
}
