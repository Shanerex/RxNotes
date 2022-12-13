package com.example.rxnotes.feature_note.presentation.add_edit.components

import androidx.compose.animation.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Save
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.rxnotes.feature_note.domain.models.Note
import com.example.rxnotes.feature_note.presentation.add_edit.AddEditNoteViewModel
import com.example.rxnotes.feature_note.presentation.add_edit.AddNoteEvents
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun AddEditNoteScreen(
    navController: NavController,
    viewModel: AddEditNoteViewModel = hiltViewModel(),
    noteColour: Int
) {
    val titleState = viewModel.noteTitleState.value
    val contentState = viewModel.noteContentState.value

    //for snackbar
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when(event) {
                is AddEditNoteViewModel.UIEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.msg
                    )
                }
                is AddEditNoteViewModel.UIEvent.SaveNote -> {
                    navController.navigateUp()
                }
            }
        }
    }

    val noteBackgroundAnimatable = remember {
        Animatable(Color(if (noteColour != -1) noteColour else viewModel.noteColourState.value))
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    viewModel.onEvent(AddNoteEvents.SaveNote)
                },
                backgroundColor = MaterialTheme.colors.primary
            ) {
                Icon(
                    imageVector = Icons.Default.Save,
                    contentDescription = "Save Note"
                )
            }
        },
        scaffoldState = scaffoldState
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(noteBackgroundAnimatable.value)
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Note.noteColors.forEach{ colour ->
                    val colourInt = colour.toArgb()
                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .shadow(15.dp, CircleShape)
                            .clip(CircleShape)
                            .background(colour)
                            .border(
                                width = 3.dp,
                                color = if (viewModel.noteColourState.value == colourInt) {
                                    Color.Black
                                } else {
                                    Color.Transparent
                                },
                                shape = CircleShape
                            )
                            .clickable {
                                coroutineScope.launch {
                                    noteBackgroundAnimatable.animateTo(
                                        targetValue = Color(colourInt),
                                        animationSpec = tween(
                                            durationMillis = 500
                                        )
                                    )
                                    viewModel.onEvent(AddNoteEvents.ChangeColour(colourInt))
                                }
                            }
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            TransparentHintTextField(
                text = titleState.text,
                hint = titleState.hint,
                onValueChange = { newTitle ->
                                viewModel.onEvent(AddNoteEvents.EnteredTitle(newTitle))
                },
                onFocusChange = {
                    viewModel.onEvent(AddNoteEvents.ChangeTitleFocus(it))
                },
                isHintVisible = titleState.isHintVisible,
                singleLine = true,
                textStyle = MaterialTheme.typography.h5
            )
            Spacer(modifier = Modifier.height(16.dp))
            TransparentHintTextField(
                text = contentState.text,
                hint = contentState.hint,
                onValueChange = { newContent ->
                    viewModel.onEvent(AddNoteEvents.EnteredContent(newContent))
                },
                onFocusChange = {
                    viewModel.onEvent(AddNoteEvents.ChangeContentFocus(it))
                },
                isHintVisible = contentState.isHintVisible,
                singleLine = false,
                textStyle = MaterialTheme.typography.body1,
                modifier = Modifier.fillMaxHeight()
                )
        }
    }
}