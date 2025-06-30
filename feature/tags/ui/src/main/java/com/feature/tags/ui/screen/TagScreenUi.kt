package com.feature.tags.ui.screen

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetValue
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.core.common.theme.BlackSemiTransparent
import com.core.common.theme.TaskerTheme
import com.core.common.ui.ErrorScreen
import com.core.common.ui.LoadingScreen
import com.core.common.utils.toColorSafely
import com.core.domain.model.Note
import com.feature.tags.ui.screen.composables.TagContent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TagScreenUi(
    tagsUiState: TagsUiState,
    onNoteClick: (Note) -> Unit,
    onSheetHide: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val state = tagsUiState
    val sheetState =
        rememberStandardBottomSheetState(
            initialValue = SheetValue.Hidden,
            skipHiddenState = false,
        )

    val currentOnSheetHide by rememberUpdatedState(onSheetHide)

    LaunchedEffect(state) {
        if (state is TagsUiState.TagLoaded) {
            sheetState.expand()
        }
        snapshotFlow {
            sheetState.currentValue
        }.collect {
            Log.d("TagScreenUi", it.name)
            if (it == SheetValue.PartiallyExpanded) {
                currentOnSheetHide()
            }
        }
    }

    BottomSheetScaffold(
        scaffoldState = rememberBottomSheetScaffoldState(bottomSheetState = sheetState),
        modifier = modifier,
        sheetContainerColor = if (state is TagsUiState.TagLoaded) state.tag.color.toColorSafely() else BottomSheetDefaults.ContainerColor,
        containerColor = BlackSemiTransparent,
        sheetPeekHeight = 0.dp,
        sheetContent = {
            when (state) {
                is TagsUiState.Error -> {
                    ErrorScreen(
                        message = state.message,
                        modifier =
                            Modifier
                                .fillMaxSize(),
                    )
                }

                TagsUiState.Idle -> {}
                TagsUiState.Loading -> {
                    LoadingScreen(
                        modifier =
                            Modifier
                                .fillMaxSize(),
                    )
                }

                is TagsUiState.TagLoaded -> {
                    TagContent(
                        modifier = Modifier.fillMaxSize(),
                        tag = state.tag,
                        onNoteClick = onNoteClick,
                    )
                }
            }
        },
        content = {},
    )
}

@Suppress("UnusedPrivateMember")
@Preview(showBackground = true)
@Composable
private fun TagScreenPreview(
    @PreviewParameter(TagScreenPreviewParameterProvider::class) state: TagsUiState,
) {
    TaskerTheme {
        TagScreenUi(
            modifier = Modifier.fillMaxSize(),
            tagsUiState = state,
            onSheetHide = {},
            onNoteClick = {},
        )
    }
}
