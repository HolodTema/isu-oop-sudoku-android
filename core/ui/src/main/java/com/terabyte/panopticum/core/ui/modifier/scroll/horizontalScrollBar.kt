package com.terabyte.panopticum.core.ui.modifier.scroll

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.terabyte.panopticum.core.ui.theme.Dimen

fun Modifier.horizontalScrollBar(
    state: LazyListState,
    color: Color = Color.LightGray,
    height: Dp = 4.dp,
    paddingBottom: Dp = 2.dp
): Modifier = drawWithContent {
    drawContent()

    val layoutInfo = state.layoutInfo
    val amountItems = layoutInfo.totalItemsCount

    if (amountItems > 0) {
        val listVisibleItems = layoutInfo.visibleItemsInfo
        if (listVisibleItems.isNotEmpty()) {
            val firstVisibleIndex = state.firstVisibleItemIndex

            val viewportWidth = size.width
            val scrollbarWidth = (viewportWidth / amountItems) * listVisibleItems.size
            val scrollbarMarginLeft = (viewportWidth / amountItems) * firstVisibleIndex

            drawRect(
                color = color,
                topLeft = Offset(
                    x = scrollbarMarginLeft,
                    y = size.height - height.toPx() + Dimen.paddingMedium.toPx()
                ),
                size = Size(
                    width = scrollbarWidth,
                    height = height.toPx()
                )
            )
        }
    }
}