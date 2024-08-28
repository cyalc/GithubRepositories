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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.cyalc.repositories.ui.RepoUiModel


@Composable
fun HomeScreen(repositories: List<RepoUiModel>, modifier: Modifier, onItemClicked: (Long) -> Unit) {
    RepositoryList(items = repositories, modifier = modifier, onItemClicked = onItemClicked)
}

@Composable
fun RepositoryList(items: List<RepoUiModel>, modifier: Modifier, onItemClicked: (Long) -> Unit) {
    LazyColumn(modifier = modifier) {
        items(items, key = { it.id }) { item ->
            RepositoryItem(item, onItemClicked)
        }
    }
}

@Composable
fun RepositoryItem(item: RepoUiModel, onItemClicked: (Long) -> Unit) {
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
        ) {
            // no-op
        }
    }
}

