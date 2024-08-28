package com.cyalc.abnamrogithubrepositories

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.cyalc.abnamrogithubrepositories.ui.theme.AppTheme
import com.cyalc.repositories.ui.detail.RepoDetailScreen
import com.cyalc.repositories.ui.home.RepositoryListViewModel
import com.cyalc.repositories.ui.home.HomeScreen
import org.koin.androidx.viewmodel.ext.android.viewModel

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {

    private val repositoryListViewModel: RepositoryListViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val repositories = repositoryListViewModel.repositories.collectAsState()
            val navController = rememberNavController()
            AppTheme {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            colors = topAppBarColors(
                                containerColor = MaterialTheme.colorScheme.primaryContainer,
                                titleContentColor = MaterialTheme.colorScheme.primary,
                            ),
                            title = {
                                // TODO: make this a string resource
                                Text(text = "Github Repos")
                            }
                        )
                    },
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    NavHost(navController = navController, startDestination = "home") {
                        composable("home") {
                            HomeScreen(
                                modifier = Modifier.padding(innerPadding),
                                repositories = repositories.value,
                            ) { id ->
                                navController.navigate("details/$id")
                            }
                        }
                        composable("details/{id}") { backStackEntry ->
                            val id = backStackEntry.arguments?.getLong("id") ?: 0L
                            RepoDetailScreen(id = id)
                        }
                    }
                }
            }
        }
    }
}