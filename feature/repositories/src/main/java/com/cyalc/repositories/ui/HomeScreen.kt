package com.cyalc.repositories.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage


@Composable
fun HomeScreen(repositories: List<RepoUiModel>, modifier: Modifier) {
    RepositoryList(items = repositories)
}

@Composable
fun RepositoryList(items: List<RepoUiModel>) {
    LazyColumn {
        items(items, key = { it.id }) { item ->
            RepositoryItem(item = item)
            HorizontalDivider()
        }
    }
}

@Composable
fun RepositoryItem(item: RepoUiModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = item.ownerAvatarUrl,
            contentDescription = "Owner Avatar",
            modifier = Modifier
                .size(48.dp)
                .padding(end = 16.dp)
        )
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
fun RepositoryListPreview() {
    MaterialTheme {
        HomeScreen(
            repositories = listOf(
                RepoUiModel(
                    id = 1,
                    name = "Repository 1",
                    ownerAvatarUrl = "https://avatars.githubusercontent.com/u/1",
                    visibility = true,
                    status = RepoUiModel.Status.PUBLIC
                ),
                RepoUiModel(
                    id = 2,
                    name = "Repository 2",
                    ownerAvatarUrl = "https://avatars.githubusercontent.com/u/2",
                    visibility = false,
                    status = RepoUiModel.Status.PRIVATE
                )
            ),
            modifier = Modifier.padding(8.dp)
        )
    }
}

