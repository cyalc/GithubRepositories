package com.cyalc.repositories.ui.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.cyalc.repositories.ui.RepoUiModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun RepoDetailScreen(
    viewModel: RepoDetailViewModel = koinViewModel(),
    id: Long,
    modifier: Modifier,
    onOpenUrl: (String) -> Unit,
) {
    LaunchedEffect(key1 = id) {
        viewModel.loadRepo(id)
    }

    RepoDetail(
        repoUiModel = viewModel.repo.collectAsState().value,
        modifier = modifier,
        onOpenUrl = onOpenUrl
    )
}

@Composable
fun RepoDetail(
    repoUiModel: RepoUiModel?,
    modifier: Modifier,
    onOpenUrl: (String) -> Unit,
) =
    repoUiModel?.let { repo ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = repo.name,
                    style = MaterialTheme.typography.headlineMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f)
                )
                StatusChip(status = repo.status)
            }

            Text(
                text = repo.fullName,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.secondary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = repo.description,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            AsyncImage(
                model = repo.ownerAvatarUrl,
                contentDescription = "Owner Avatar",
                modifier = Modifier
                    .size(64.dp)
                    .padding(bottom = 16.dp),
                contentScale = ContentScale.Crop
            )

            repo.htmlUrl?.let { url ->
                OutlinedButton(
                    onClick = { onOpenUrl(url) },
                ) {
                    Text("View on GitHub")
                }
            }
        }
    } ?: run {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("Repo could not be found", style = MaterialTheme.typography.bodyMedium)
        }
    }

@Composable
fun StatusChip(status: RepoUiModel.Status) {
    val (backgroundColor, contentColor) = when (status) {
        RepoUiModel.Status.PUBLIC -> MaterialTheme.colorScheme.primaryContainer to MaterialTheme.colorScheme.onPrimaryContainer
        RepoUiModel.Status.PRIVATE -> MaterialTheme.colorScheme.secondaryContainer to MaterialTheme.colorScheme.onSecondaryContainer
    }

    Surface(
        color = backgroundColor,
        contentColor = contentColor,
        shape = MaterialTheme.shapes.small
    ) {
        Text(
            text = status.name,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            style = MaterialTheme.typography.labelMedium
        )
    }
}