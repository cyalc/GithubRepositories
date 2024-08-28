package com.cyalc.repositories.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.cyalc.repositories.ui.RepoUiModel
import org.koin.androidx.compose.koinViewModel


@Composable
fun HomeScreen(
    viewModel: RepoHomeViewModel = koinViewModel(),
    modifier: Modifier,
    onItemClicked: (Long) -> Unit,
) {
    val repos by viewModel.repos.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    RepoList(
        items = repos,
        isLoading = isLoading,
        modifier = modifier,
        onItemClicked = onItemClicked,
        onEndOfListReached = viewModel::loadNextPage
    )
}

@Composable
fun RepoList(
    items: List<RepoUiModel>,
    isLoading: Boolean,
    modifier: Modifier,
    onItemClicked: (Long) -> Unit,
    onEndOfListReached: () -> Unit,
) {
    val listState = rememberLazyListState()
    val endOfListReached by remember {
        derivedStateOf {
            val lastVisibleItem = listState.layoutInfo.visibleItemsInfo.lastOrNull()
            lastVisibleItem?.index != 0 && lastVisibleItem?.index == listState.layoutInfo.totalItemsCount - 1
        }
    }

    LaunchedEffect(endOfListReached) {
        if (endOfListReached) {
            onEndOfListReached()
        }
    }

    LazyColumn(modifier = modifier, state = listState) {
        items(items, key = { it.id }) { item ->
            RepoItem(item, onItemClicked)
        }

        if (isLoading) {
            item {
                CircularProgressIndicator(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .wrapContentSize(Alignment.Center)
                )
            }
        }
    }
}

@Composable
fun RepoItem(item: RepoUiModel, onItemClicked: (Long) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable { onItemClicked(item.id) },
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = item.ownerAvatarUrl,
            contentDescription = "Owner Avatar",
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)

        )

        Spacer(modifier = Modifier.width(16.dp))

        Column {
            Text(
                text = item.name,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Row {
                Text(
                    text = if (item.visibility) "Visible" else "Hidden",
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = item.status.name,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RepoListPreview() {
    MaterialTheme {
        HomeScreen(
            modifier = Modifier.padding(8.dp)
        ) {
            // no-op
        }
    }
}

