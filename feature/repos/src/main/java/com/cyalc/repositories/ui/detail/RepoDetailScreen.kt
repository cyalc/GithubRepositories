package com.cyalc.repositories.ui.detail

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import com.cyalc.repositories.ui.RepoUiModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun RepoDetailScreen(
    viewModel: RepoDetailViewModel = koinViewModel(),
    id: Long
) {
    LaunchedEffect(key1 = id) {
        viewModel.loadRepo(id)

    }

    RepoDetail(
        repoUiModel = viewModel.repo
            .collectAsState()
            .value
    )
}

@Composable
fun RepoDetail(repoUiModel: RepoUiModel?) {
    Text(
        text = "Repo Name",
        style = MaterialTheme.typography.titleMedium
    )
}