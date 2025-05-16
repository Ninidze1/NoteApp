package com.ninidze.framework.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.ninidze.framework.presentation.screen.NoteDetailScreenContent
import com.ninidze.framework.presentation.screen.NotesScreenContent
import com.ninidze.framework.presentation.model.NoteActions
import com.ninidze.framework.presentation.model.NoteUiEvent
import com.ninidze.framework.presentation.navigation.NoteDetailScreen
import com.ninidze.framework.presentation.navigation.NotesScreen
import com.ninidze.framework.presentation.theme.NoteAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NoteAppTheme {
                val viewModel = hiltViewModel<NotesViewModel>()
                val navController = rememberNavController()
                val snackBarHostState = remember { SnackbarHostState() }

                LaunchedEffect(Unit) {
                    viewModel.eventFlow.collect { event ->
                        when (event) {
                            is NoteUiEvent.ShowSnackBar -> {
                                snackBarHostState.showSnackbar(message = event.message)
                            }
                        }
                    }
                }

                NavHost(
                    modifier = Modifier.systemBarsPadding(),
                    navController = navController,
                    startDestination = NotesScreen
                ) {
                    composable<NotesScreen> {
                        NotesScreenContent(
                            viewModel = viewModel,
                            action = { action ->
                                viewModel.onAction(action)
                                if (action is NoteActions.Update) {
                                    navController.navigate(
                                        NoteDetailScreen.fromNote(action.note)
                                    )
                                }
                            },
                            snackBarHostState = snackBarHostState,
                            onFabClick = { navController.navigate(NoteDetailScreen.Empty) }
                        )
                    }

                    composable<NoteDetailScreen> { entry ->
                        val args = entry.toRoute<NoteDetailScreen>()
                        NoteDetailScreenContent(
                            note = args.takeIf { it != NoteDetailScreen.Empty }?.toNote(),
                            snackBarHostState = snackBarHostState,
                            actions = { action ->
                                viewModel.onAction(action)
                                if (action != NoteActions.ShowEmptyNoteNotification) {
                                    navController.navigateUp()
                                }
                            },
                            onBackClick = { navController.navigateUp() }
                        )
                    }
                }
            }
        }
    }
}
