package com.example.pokemonapp.presentation.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.pokemonapp.core.NavArgs
import com.example.pokemonapp.presentation.navigation.Screen
import com.example.pokemonapp.presentation.view.compose.PokemonDetailScreen
import com.example.pokemonapp.presentation.view.compose.PokemonListScreen
import com.example.pokemonapp.presentation.viewmodel.PokemonDetailViewModel
import com.example.pokemonapp.presentation.viewmodel.PokemonListViewModel
import com.example.pokemonapp.ui.theme.PokemonAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            PokemonAppTheme {
                AppNavigator()
            }
        }
    }
}

@Composable
private fun AppNavigator() {
    val navController = rememberNavController()

    Scaffold { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.List.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.List.route) {
                val listViewModel: PokemonListViewModel = hiltViewModel()
                val state = listViewModel.uiState.collectAsStateWithLifecycle()

                PokemonListScreen(
                    uiState = state.value,
                    onSelect = { idOrName ->
                        navController.navigate(Screen.Detail.createRoute(idOrName))
                    }
                )
            }

            composable(
                route = Screen.Detail.route,
                arguments = listOf(navArgument(NavArgs.ID_OR_NAME) {
                    type = NavType.StringType
                })
            ) {
                val detailViewModel: PokemonDetailViewModel = hiltViewModel()
                val state = detailViewModel.uiState.collectAsStateWithLifecycle()

                PokemonDetailScreen(
                    uiState = state.value,
                    onBack = { navController.popBackStack() }
                )
            }
        }
    }
}