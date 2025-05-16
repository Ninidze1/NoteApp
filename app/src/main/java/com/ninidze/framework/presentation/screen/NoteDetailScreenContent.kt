package com.ninidze.framework.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ninidze.domain.model.Note
import com.ninidze.framework.R
import com.ninidze.framework.presentation.component.NoteTextField
import com.ninidze.framework.presentation.model.NoteActions

@Composable
internal fun NoteDetailScreenContent(
    note: Note? = null,
    actions: (NoteActions) -> Unit,
    onBackClick: () -> Unit,
    snackBarHostState: SnackbarHostState
) {
    var titleValue by rememberSaveable { mutableStateOf(note?.title ?: "") }
    var contentValue by rememberSaveable { mutableStateOf(note?.title ?: "") }

    Scaffold(
        modifier = Modifier.fillMaxWidth(),
        snackbarHost = { SnackbarHost(hostState = snackBarHostState) },
        topBar = {
            IconButton(
                onClick = onBackClick,
                modifier = Modifier.padding(top = 8.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_back),
                    contentDescription = "Back"
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier.imePadding(),
                elevation = FloatingActionButtonDefaults.loweredElevation(),
                onClick = {
                    if (titleValue.isNotBlank() || contentValue.isNotBlank()) {
                        actions(
                            note?.let {
                                NoteActions.Update(
                                    Note(note.id, titleValue, contentValue)
                                )
                            } ?: NoteActions.Create(titleValue, contentValue)
                        )
                    } else {
                        actions(NoteActions.ShowEmptyNoteNotification)
                    }
                }
            ) {
                Icon(
                    Icons.Filled.Done,
                    contentDescription = "Confirm note"
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(
                    start = 16.dp,
                    end = 16.dp,
                    top = 24.dp
                ),
            verticalArrangement = Arrangement.spacedBy(32.dp)
        ) {
            NoteTextField(
                value = titleValue,
                onValueChange = { titleValue = it },
                shouldBeInitiallyFocused = true,
                textStyle = TextStyle(
                    color = Color.Black,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Black
                )
            )

            NoteTextField(
                modifier = Modifier.fillMaxSize(),
                value = contentValue,
                onValueChange = { contentValue = it },
                singleLine = false,
                textStyle = TextStyle(
                    color = Color.Black,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Normal
                )
            )
        }
    }

}