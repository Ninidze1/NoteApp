package com.ninidze.framework.presentation.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxState
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.dp
import com.ninidze.framework.presentation.theme.red
import kotlinx.coroutines.delay

/**
 * To be used as a container for a LazyList items with unique ID
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> SwipeToDeleteContainer(
    modifier: Modifier = Modifier,
    item: T,
    onDelete: (T) -> Unit,
    dismissBoxPadding: PaddingValues = PaddingValues(0.dp),
    dismissBoxBackgroundShape: Shape = RectangleShape,
    content: @Composable (T) -> Unit
) {
    var show by remember { mutableStateOf(true) }
    var itemWidth by remember { mutableIntStateOf(0) }
    val currentItem by rememberUpdatedState(item)
    val dismissState = rememberSwipeToDismissBoxState(
        confirmValueChange = { value ->
            if (value == SwipeToDismissBoxValue.EndToStart) {
                show = false
                true
            } else false
        },
        positionalThreshold = { (itemWidth / THRESHOLD_DIVISOR).toFloat() }
    )
    LaunchedEffect(item) {
        show = true
    }
    AnimatedVisibility(
        modifier = modifier,
        visible = show, exit = fadeOut(spring())
    ) {
        SwipeToDismissBox(
            state = dismissState,
            enableDismissFromStartToEnd = false,
            content = { content(item) },
            backgroundContent = {
                SwipeDeleteBackground(
                    modifier = Modifier
                        .padding(dismissBoxPadding)
                        .onGloballyPositioned {
                            itemWidth = it.size.width
                        },
                    backgroundShape = dismissBoxBackgroundShape,
                    swipeDismissState = dismissState
                )
            },
        )
    }

    LaunchedEffect(show) {
        if (!show) {
            delay(ANIMATION_DURATION.toLong())
            onDelete(currentItem)
        }
        dismissState.snapTo(SwipeToDismissBoxValue.Settled)
    }
}

@ExperimentalMaterial3Api
@Composable
private fun SwipeDeleteBackground(
    modifier: Modifier,
    backgroundShape: Shape = RectangleShape,
    swipeDismissState: SwipeToDismissBoxState
) {
    val color = if (swipeDismissState.dismissDirection == SwipeToDismissBoxValue.EndToStart) {
        red
    } else {
        Color.Transparent
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                color = color,
                shape = backgroundShape
            )
            .padding(24.dp),
        contentAlignment = Alignment.CenterEnd
    ) {
        Icon(
            modifier = Modifier
                .heightIn(40.dp)
                .widthIn(40.dp),
            imageVector = Icons.Rounded.Delete,
            contentDescription = null,
            tint = Color.White
        )
    }
}

private const val ANIMATION_DURATION = 400
private const val THRESHOLD_DIVISOR = 3
