package com.ninidze.framework.presentation.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction

@Composable
fun NoteTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    singleLine: Boolean = true,
    textStyle: TextStyle = TextStyle.Default,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    shouldBeInitiallyFocused: Boolean = false,
) {
    var focused by remember { mutableStateOf(shouldBeInitiallyFocused) }
    val focusRequester = remember { FocusRequester() }

    Box {
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = modifier
                .focusRequester(focusRequester)
                .onFocusChanged { focused = it.isFocused },
            singleLine = singleLine,
            maxLines = maxLines,
            textStyle = textStyle,
            cursorBrush = SolidColor(Color.Black),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = if (singleLine) ImeAction.Done else ImeAction.Default
            ),
            keyboardActions = KeyboardActions.Default
        )
    }

    LaunchedEffect(Unit) {
        if (shouldBeInitiallyFocused) {
            focusRequester.requestFocus()
        }
    }
}
