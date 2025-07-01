package com.feature.tags.ui.screen.composables

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.core.common.theme.LightGray
import com.core.common.theme.LightGray2
import com.core.common.theme.LightGray3
import com.core.common.theme.TaskerTheme
import com.feature.tags.ui.screen.TagsUiBottomSheet
import kotlinx.coroutines.launch

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun EditTagBottomSheet(
    bottomSheet: TagsUiBottomSheet.RenameTagBottomSheet,
    hideEditTagBottomSheet: () -> Unit,
    onSaveTagNameClick: (Long, String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val sheetState = rememberModalBottomSheetState()
    var tagName by remember { mutableStateOf(bottomSheet.tagName) }
    val scope = rememberCoroutineScope()
    ModalBottomSheet(
        onDismissRequest = {
            hideEditTagBottomSheet()
        },
        sheetState = sheetState,
    ) {
        Column(
            modifier = modifier,
            horizontalAlignment = Alignment.Start,
        ) {
            Text(
                modifier = Modifier.padding(top = 12.dp),
                text = "Rename Tag",
                style =
                    TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                    ),
            )

            Text(
                modifier = Modifier.padding(bottom = 20.dp, top = 4.dp),
                text = "Enter a new name for the tag",
                style =
                    TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = LightGray3,
                    ),
            )

            Text(
                modifier = Modifier.padding(bottom = 8.dp),
                text = "Tag Name",
                style =
                    TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                    ),
            )

            TagTextField(
                tagName = tagName,
                onTagNameChange = {
                    tagName = it
                },
            )

            Row(modifier = Modifier.padding(vertical = 20.dp)) {
                Button(
                    modifier = Modifier.weight(1f),
                    onClick = {
                        scope.launch { sheetState.hide() }.invokeOnCompletion {
                            if (!sheetState.isVisible) {
                                hideEditTagBottomSheet()
                            }
                        }
                    },
                    shape = RoundedCornerShape(16.dp),
                    colors =
                        ButtonDefaults.buttonColors().copy(
                            containerColor = LightGray,
                        ),
                ) {
                    ButtonText(
                        modifier = Modifier.padding(vertical = 8.dp),
                        text = "Cancel",
                    )
                }
                Spacer(modifier = Modifier.width(14.dp))
                Button(
                    modifier = Modifier.weight(1f),
                    onClick = {
                        scope.launch { sheetState.hide() }.invokeOnCompletion {
                            if (!sheetState.isVisible) {
                                onSaveTagNameClick(bottomSheet.tagId, tagName.trim())
                                hideEditTagBottomSheet()
                            }
                        }
                    },
                    shape = RoundedCornerShape(16.dp),
                ) {
                    ButtonText(
                        modifier = Modifier.padding(vertical = 8.dp),
                        text = "Save",
                        color = Color.White,
                    )
                }
            }
        }
    }
}

@Composable
private fun TagTextField(
    tagName: String,
    onTagNameChange: (String) -> Unit,
) {
    TextField(
        value = tagName,
        onValueChange = onTagNameChange,
        colors =
            TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                disabledContainerColor = Color.Transparent,
                errorContainerColor = Color.Transparent,
            ),
        modifier =
            Modifier
                .fillMaxWidth()
                .border(
                    width = 1.dp,
                    color = LightGray2,
                    shape = RoundedCornerShape(12.dp),
                ).padding(horizontal = 4.dp),
        shape = RoundedCornerShape(12.dp),
        singleLine = true,
    )
}

@Composable
fun ButtonText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = Color.Black,
) {
    Text(
        modifier = modifier.padding(vertical = 8.dp),
        text = text,
        style =
            TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = color,
            ),
    )
}

@Suppress("UnusedPrivateMember")
@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun EditTagBottomSheetPreview() {
    TaskerTheme {
        EditTagBottomSheet(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 25.dp),
            bottomSheet =
                TagsUiBottomSheet.RenameTagBottomSheet(
                    tagId = 1,
                    tagName = "Tag",
                    tagColor = "#000000",
                ),
            hideEditTagBottomSheet = { },
            onSaveTagNameClick = { _, _ -> },
        )
    }
}
