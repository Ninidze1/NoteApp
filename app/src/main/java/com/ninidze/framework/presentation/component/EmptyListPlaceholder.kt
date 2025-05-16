package com.ninidze.framework.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.ninidze.framework.R
import com.ninidze.framework.presentation.theme.CustomFont

@Composable
fun EmptyListPlaceholder(
    modifier: Modifier = Modifier,
    onAddClick: () -> Unit = {},
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp)
            .wrapContentSize(Alignment.Center),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.img_empty_placeholder),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier.size(240.dp)
        )

        Spacer(Modifier.height(16.dp))

        Text(
            text = "Add your first note",
            fontFamily = CustomFont,
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(8.dp))

        Text(
            text = "Relax and write something beautiful",
            fontFamily = CustomFont,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(40.dp))

        Button(
            onClick = onAddClick,
            shape = MaterialTheme.shapes.small,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.secondaryContainer
            ),
            elevation = ButtonDefaults.buttonElevation(
                defaultElevation = 6.dp,
                pressedElevation = 8.dp,
                hoveredElevation = 8.dp,
                focusedElevation = 8.dp
            ),
        ) {
            Text(
                text = "Add Note",
                fontFamily = CustomFont,
                style = MaterialTheme.typography.labelLarge,
                color = Color.Black
            )
        }
    }
}
