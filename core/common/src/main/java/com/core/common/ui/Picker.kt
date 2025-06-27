@file:Suppress("MagicNumber")

package com.core.common.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.core.common.theme.LightGray
import com.core.common.theme.LightGray5
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

@Composable
fun rememberPickerState(itemToPick: String) = remember { PickerState(itemToPick) }

data class PickerState(
    private val itemToPick: String,
) {
    var selectedItem by mutableStateOf(itemToPick)
}

@Composable
fun Picker(
    items: List<String>,
    state: PickerState,
    modifier: Modifier = Modifier,
    textModifier: Modifier = Modifier,
    visibleItemsCount: Int = 3,
    textStyle: TextStyle = LocalTextStyle.current,
    dividerColor: Color = LightGray5,
    wheelAlignment: Alignment.Horizontal = Alignment.End,
) {
    fun getItem(index: Int) = items[index % items.size]

    val pickedNumberIndex = items.indexOf(state.selectedItem).coerceAtLeast(0)

    val visibleItemsMiddle = visibleItemsCount / 2
    val listScrollCount = Integer.MAX_VALUE
    val listScrollMiddle = listScrollCount / 2
    val listStartIndex =
        listScrollMiddle - listScrollMiddle % items.size - visibleItemsMiddle + pickedNumberIndex

    val listState = rememberLazyListState(initialFirstVisibleItemIndex = listStartIndex)
    val flingBehavior = rememberSnapFlingBehavior(lazyListState = listState)

    val itemHeightPixels = remember { mutableIntStateOf(0) }
    val itemHeightDp = pixelsToDp(itemHeightPixels.value)

    val fadingEdgeGradient =
        remember {
            Brush.verticalGradient(
                0f to Color.Transparent,
                0.5f to Color.Black,
                1f to Color.Transparent,
            )
        }

    LaunchedEffect(listState) {
        snapshotFlow { listState.firstVisibleItemIndex }
            .map { index -> getItem(index + visibleItemsMiddle) }
            .distinctUntilChanged()
            .collect { item -> state.selectedItem = item }
    }

    Box(modifier = modifier) {
        LazyColumn(
            state = listState,
            flingBehavior = flingBehavior,
            horizontalAlignment = wheelAlignment,
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(itemHeightDp * visibleItemsCount)
                    .fadingEdge(fadingEdgeGradient),
        ) {
            items(listScrollCount) { index ->
                Text(
                    text = getItem(index),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = textStyle,
                    modifier =
                        Modifier
                            .onSizeChanged { size -> itemHeightPixels.value = size.height }
                            .then(textModifier),
                )
            }
        }

        HorizontalDivider(
            modifier =
                Modifier
                    .padding(top = itemHeightDp * visibleItemsMiddle)
                    .height(2.dp),
            color = dividerColor,
        )

        HorizontalDivider(
            modifier =
                Modifier
                    .padding(top = (itemHeightDp * visibleItemsMiddle) + itemHeightDp)
                    .height(2.dp),
            color = dividerColor,
        )
    }
}

fun Modifier.fadingEdge(brush: Brush) =
    this
        .graphicsLayer(compositingStrategy = CompositingStrategy.Offscreen)
        .drawWithContent {
            drawContent()
            drawRect(brush = brush, blendMode = BlendMode.DstIn)
        }

@Composable
private fun pixelsToDp(pixels: Int) = with(LocalDensity.current) { pixels.toDp() }

@Suppress("UnusedPrivateMember")
@Preview
@Composable
private fun NumberPickerDemo() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.background(color = Color.White),
    ) {
        val values = remember { (1..99).map { it.toString() } }
        val valuesPickerState = rememberPickerState("99")

        Picker(
            state = valuesPickerState,
            items = values,
            visibleItemsCount = 5,
            modifier = Modifier.fillMaxWidth(),
            textModifier = Modifier.padding(8.dp),
            textStyle = TextStyle(fontSize = 32.sp),
            dividerColor = Color(0xFFE8E8E8),
            wheelAlignment = Alignment.CenterHorizontally,
        )

        Text(
            text = "Result: ${valuesPickerState.selectedItem}",
            textAlign = TextAlign.Center,
            fontWeight = FontWeight(500),
            fontSize = 20.sp,
            modifier =
                Modifier
                    .padding(vertical = 16.dp)
                    .fillMaxWidth(0.5f)
                    .background(color = LightGray, shape = RoundedCornerShape(size = 8.dp))
                    .padding(vertical = 10.dp, horizontal = 16.dp),
        )
    }
}
