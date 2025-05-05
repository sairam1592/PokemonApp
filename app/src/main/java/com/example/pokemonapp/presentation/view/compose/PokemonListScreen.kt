package com.example.pokemonapp.presentation.view.compose

import android.content.res.Configuration
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.CardDefaults.elevatedCardElevation
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pokemonapp.R
import com.example.pokemonapp.common.ui.UiState
import com.example.pokemonapp.domain.model.PokemonSummary
import com.example.pokemonapp.ui.theme.PokemonAppTheme

@Composable
fun PokemonListScreen(
    uiState: UiState<List<PokemonSummary>>,
    onSelect: (String) -> Unit
) {
    when (uiState) {
        UiState.Loading -> FullScreenLoading()
        is UiState.Error -> FullScreenError(uiState.message)
        is UiState.Success -> PokemonList(
            pokemonList = uiState.data,
            onSelect = onSelect
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokemonList(
    pokemonList: List<PokemonSummary>,
    onSelect: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = stringResource(R.string.list_title)) }
            )
        },
        modifier = modifier
    ) { padding ->
        LazyColumn(
            contentPadding = padding
        ) {
            items(pokemonList) { summary ->
                ElevatedCard(
                    onClick = { onSelect(summary.name) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    shape = MaterialTheme.shapes.medium,
                    elevation = elevatedCardElevation(defaultElevation = 4.dp)
                ) {
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .testTag("pokemon_${summary.name}"),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = summary.name,
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.weight(1f)
                        )
                        Icon(
                            imageVector = Icons.Default.PlayArrow,
                            contentDescription = null
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(15.dp))
    }
}

private val sampleList = listOf(PokemonSummary("Bulbasaur"), PokemonSummary("Charmander"))

@Preview(showBackground = true, widthDp = 320, heightDp = 480)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun PokemonListScreenPreview() {
    PokemonAppTheme {
        PokemonListScreen(
            uiState = UiState.Success(sampleList),
            onSelect = {}
        )
    }
}
