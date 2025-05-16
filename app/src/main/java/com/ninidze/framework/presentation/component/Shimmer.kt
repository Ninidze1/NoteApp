package com.ninidze.framework.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ninidze.framework.presentation.theme.gray_200
import com.valentinilk.shimmer.Shimmer
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.rememberShimmer
import com.valentinilk.shimmer.shimmer

@Composable
fun ShimmeringList(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues,
    titleContent: @Composable () -> Unit = {},
    shimmer: Shimmer = rememberShimmer(ShimmerBounds.Window)
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = paddingValues,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item { titleContent() }

        items(5) {
            ShimmerItem(shimmer = shimmer)
        }
    }
}

@Composable
fun ShimmerItem(
    modifier: Modifier = Modifier,
    shimmer: Shimmer
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(100.dp)
            .shimmer(customShimmer = shimmer)
            .background(color = gray_200, shape = MaterialTheme.shapes.medium)
    )
}
