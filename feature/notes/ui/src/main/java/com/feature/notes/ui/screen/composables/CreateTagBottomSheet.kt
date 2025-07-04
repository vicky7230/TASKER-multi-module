package com.feature.notes.ui.screen.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.core.common.theme.LightGray
import com.core.common.theme.LightGray3
import com.core.common.theme.TaskerTheme
import com.core.common.ui.PrimaryButton
import com.core.common.ui.RoundedTextField
import kotlinx.coroutines.launch

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun CreateTagBottomSheet(
    hideCreateTagBottomSheet: () -> Unit,
    onSaveTagNameClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val sheetState = rememberModalBottomSheetState()
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
                modifier = Modifier.padding(bottom = 20.dp, top = 4.dp),
                text = "Enter name for the tag",
                style =
                    TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = LightGray3,
                    ),
            )

            Text(
                modifier = Modifier.padding(bottom = 8.dp),
                text = "Create Tag",
                style =
                    TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                    ),
            )

            RoundedTextField(
                modifier = Modifier.fillMaxWidth(),
                text = tagName,
                onTextChange = {
                    tagName = it
                },
            )

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
                        if (tagName.isNotEmpty()) {
                            scope.launch { sheetState.hide() }.invokeOnCompletion {
                                if (!sheetState.isVisible) {
                                    onSaveTagNameClick(tagName.trim())
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

@Suppress("UnusedPrivateMember")
@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun CreateTagBottomSheetPreview() {
    TaskerTheme {
        CreateTagBottomSheet(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 25.dp),
            hideCreateTagBottomSheet = { },
            onSaveTagNameClick = { _ -> },
        )
    }
}
