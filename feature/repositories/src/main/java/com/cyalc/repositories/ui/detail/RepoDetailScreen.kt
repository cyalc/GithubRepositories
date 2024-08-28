package com.cyalc.repositories.ui.detail

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun RepoDetailScreen(id: Long) {
    RepoDetail()
}

@Composable
fun RepoDetail() {
    Text(
        text = "Repo Name",
        style = MaterialTheme.typography.titleMedium
    )
}