package com.ninidze.framework.presentation.screen

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ninidze.framework.presentation.NotesViewModel
import com.ninidze.framework.presentation.component.EmptyListPlaceholder
import com.ninidze.framework.presentation.component.NoteItem
import com.ninidze.framework.presentation.component.ShimmeringList
import com.ninidze.framework.presentation.component.SwipeToDeleteContainer
import com.ninidze.framework.presentation.model.NoteActions
import com.ninidze.framework.presentation.model.NoteUiState
import com.ninidze.framework.presentation.model.NotesScreenState
import com.ninidze.framework.presentation.theme.CustomFont
import com.ninidze.framework.presentation.theme.backgroundColor

@Composable
internal fun NotesScreenContent(
    viewModel: NotesViewModel,
    action: (NoteActions) -> Unit,
    onFabClick: () -> Unit,
    snackBarHostState: SnackbarHostState
) {
    val uiState by viewModel.uiState

    Scaffold(
        modifier = Modifier
            .padding(top = 24.dp)
            .fillMaxSize(),
        snackbarHost = { SnackbarHost(hostState = snackBarHostState) },
        containerColor = backgroundColor,
        floatingActionButton = {
            if (uiState.notes.isNotEmpty()) {
                FloatingActionButton(
                    onClick = onFabClick,
                    elevation = FloatingActionButtonDefaults.elevation()
                ) {
                    Icon(
                        Icons.Filled.Add,
                        contentDescription = "Add note"
                    )
                }
            }
        }
    ) { innerPadding ->
        val paddingValues = PaddingValues(
            start = 16.dp,
            end = 16.dp,
            top = innerPadding.calculateTopPadding() + 8.dp,
            bottom = innerPadding.calculateBottomPadding() + 8.dp
        )
        val titleContent = @Composable {
            Text(
                "Notes",
                fontStyle = TextStyle.Default.fontStyle,
                fontSize = 24.sp,
                fontFamily = CustomFont,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        NotesScreen(
            uiState = uiState,
            action = action,
            titleContent = titleContent,
            paddingValues = paddingValues,
            onFabClick = onFabClick
        )
    }
}

@Composable
private fun NotesList(
    paddingValues: PaddingValues,
    uiState: NoteUiState,
    titleContent: @Composable () -> Unit = {},
    action: (NoteActions) -> Unit
) {
    LazyColumn(
        contentPadding = paddingValues,
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        item { titleContent() }

        items(uiState.notes, key = { it.id }) { note ->
            SwipeToDeleteContainer(
                modifier = Modifier.animateItem(),
                item = note,
                dismissBoxBackgroundShape = CardDefaults.shape,
                onDelete = { action(NoteActions.Delete(note.id)) }
            ) {
                NoteItem(
                    note = note,
                    onEdit = { action(NoteActions.Update(note)) }
                )
            }
        }
    }
}

@Composable
private fun NotesScreen(
    uiState: NoteUiState,
    action: (NoteActions) -> Unit,
    titleContent: @Composable () -> Unit,
    paddingValues: PaddingValues,
    onFabClick: () -> Unit
) {
    val screenState = rememberScreenState(uiState)
    AnimatedContent(
        targetState = screenState,
        label = "NotesScreenState"
    ) { state ->
        when (state) {
            NotesScreenState.Loading -> ShimmeringList(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize(),
                titleContent = titleContent,
                paddingValues = paddingValues
            )

            NotesScreenState.Empty -> EmptyListPlaceholder(
                modifier = Modifier.padding(paddingValues),
                onAddClick = onFabClick
            )

            is NotesScreenState.Data -> NotesList(
                uiState = uiState,
                action = action,
                titleContent = titleContent,
                paddingValues = paddingValues
            )
        }
    }
}

@Composable
private fun rememberScreenState(uiState: NoteUiState): NotesScreenState =
    remember(uiState.loading, uiState.notes) {
        when {
            uiState.loading -> NotesScreenState.Loading
            uiState.notes.isEmpty() -> NotesScreenState.Empty
            else -> NotesScreenState.Data(uiState.notes)
        }
    }
