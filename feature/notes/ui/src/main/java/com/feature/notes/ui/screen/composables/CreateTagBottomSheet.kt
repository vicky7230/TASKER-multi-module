@file:Suppress("MagicNumber")

package com.feature.notes.ui.screen.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.core.common.theme.LightGray
import com.core.common.theme.LightGray3
import com.core.common.theme.TaskerTheme
import com.core.common.theme.tagColors
import com.core.common.ui.PrimaryButton
import com.core.common.ui.RoundedTextField
import com.core.common.utils.toColorSafely
import com.core.common.utils.toHexString
import com.feature.notes.ui.screen.NotesUiBottomSheet
import kotlinx.coroutines.launch

@Composable
@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
fun CreateTagBottomSheet(
    bottomSheet: NotesUiBottomSheet.CreateTagBottomSheet,
    hideCreateTagBottomSheet: () -> Unit,
    onSaveTagNameClick: (String, String) -> Unit,
    onBottomSheetColorItemClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val sheetState =
        rememberModalBottomSheetState(
            skipPartiallyExpanded = true,
        )
    var tagName by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    ModalBottomSheet(
        onDismissRequest = {
            hideCreateTagBottomSheet()
        },
        sheetState = sheetState,
    ) {
        Column(
            modifier = modifier,
            horizontalAlignment = Alignment.Start,
        ) {
            Text(
                modifier = Modifier.padding(top = 12.dp),
                text = "Create Tag",
                style =
                    TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                    ),
            )

            Text(
                modifier = Modifier.padding(bottom = 12.dp, top = 4.dp),
                text = "Enter name for the tag",
                style =
                    TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = LightGray3,
                    ),
            )

            RoundedTextField(
                modifier = Modifier.fillMaxWidth(),
                text = tagName,
                onTextChange = {
                    tagName = it
                },
            )

            Text(
                modifier = Modifier.padding(bottom = 12.dp, top = 4.dp),
                text = "Choose color",
                style =
                    TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = LightGray3,
                    ),
            )

            val windowInfo = LocalWindowInfo.current
            val density = LocalDensity.current
            val spacing = 8.dp
            val screenWidthDp = with(density) { windowInfo.containerSize.width.toDp() }
            val itemWidth =
                remember(screenWidthDp) {
                    val totalSpacing = spacing * 5 // 5 gaps between 6 items
                    (((screenWidthDp - totalSpacing - 50.dp) / 6).value.toInt()).dp
                }
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(spacing),
                verticalArrangement = Arrangement.spacedBy(spacing),
            ) {
                tagColors.forEachIndexed { index, color ->
                    ColorItem(
                        color = color,
                        selected = color == bottomSheet.selectedColor.toColorSafely(),
                        itemWidth = itemWidth,
                        onItemClick = { onBottomSheetColorItemClick(it.toHexString()) },
                    )
                }
            }

            Row(modifier = Modifier.padding(vertical = 20.dp)) {
                PrimaryButton(
                    buttonText = "Cancel",
                    modifier = Modifier.weight(1f),
                    onButtonClick = {
                        scope.launch { sheetState.hide() }.invokeOnCompletion {
                            if (!sheetState.isVisible) {
                                hideCreateTagBottomSheet()
                            }
                        }
                    },
                    buttonColors =
                        ButtonDefaults.buttonColors().copy(
                            containerColor = LightGray,
                        ),
                )
                Spacer(modifier = Modifier.width(14.dp))
                PrimaryButton(
                    buttonText = "Save",
                    modifier = Modifier.weight(1f),
                    onButtonClick = {
                        if (tagName.isNotEmpty() && bottomSheet.selectedColor != "#00000000") {
                            scope.launch { sheetState.hide() }.invokeOnCompletion {
                                if (!sheetState.isVisible) {
                                    onSaveTagNameClick(
                                        tagName.trim(),
                                        bottomSheet.selectedColor,
                                    )
                                    hideCreateTagBottomSheet()
                                }
                            }
                        }
                    },
                    buttonTextColor = Color.White,
                )
            }
        }
    }
}

@Composable
fun ColorItem(
    color: Color,
    selected: Boolean,
    itemWidth: Dp,
    onItemClick: (Color) -> Unit,
    modifier: Modifier = Modifier,
) {
    val borderModifier =
        if (selected) {
            Modifier.border(1.dp, color, CircleShape)
        } else {
            Modifier
        }
    Box(
        modifier =
            modifier
                .size(itemWidth)
                .then(borderModifier)
                .padding(2.5.dp)
                .clip(CircleShape)
                .background(color)
                .clickable { onItemClick(color) },
    )
}

@Suppress("UnusedPrivateMember")
@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun CreateTagBottomSheetPreview() {
    TaskerTheme {
        CreateTagBottomSheet(
            bottomSheet = NotesUiBottomSheet.CreateTagBottomSheet(selectedColor = "#FF000000"),
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 25.dp),
            hideCreateTagBottomSheet = { },
            onSaveTagNameClick = { _, _ -> },
            onBottomSheetColorItemClick = { _ -> },
        )
    }
}
