package com.example.pokemonapp.presentation.view.compose

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults.elevatedCardElevation
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.pokemonapp.R
import com.example.pokemonapp.common.ui.UiState
import com.example.pokemonapp.domain.model.PokemonDetail
import com.example.pokemonapp.ui.theme.PokemonAppTheme

@Composable
fun PokemonDetailScreen(
    uiState: UiState<PokemonDetail>,
    onBack: () -> Unit
) {
    when (uiState) {
        UiState.Loading -> FullScreenLoading()
        is UiState.Error -> FullScreenError(uiState.message)
        is UiState.Success -> PokemonDetailView(uiState.data, onBack = onBack)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokemonDetailView(
    detail: PokemonDetail,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = detail.name.ifBlank { stringResource(R.string.unknown_pokemon) },
                        style = MaterialTheme.typography.headlineSmall
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = stringResource(R.string.back)
                        )
                    }
                }
            )
        },
        modifier = modifier
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                shape = MaterialTheme.shapes.large,
                elevation = elevatedCardElevation(defaultElevation = 8.dp),
                modifier = Modifier
                    .size(200.dp)
                    .padding(top = 24.dp)
                    .clip(MaterialTheme.shapes.large)
            ) {
                AsyncImage(
                    model = detail.image.ifBlank { R.drawable.placeholder_dummy },
                    contentDescription = stringResource(R.string.detail_image_content_desc),
                    modifier = Modifier.fillMaxSize()
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            if (detail.height > 0) {
                Text(
                    text = "${stringResource(R.string.height)}: ${detail.height}",
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
}

private val sampleDetail = PokemonDetail(
    id = 1,
    name = "Bulbasaur",
    height = 7,
    image = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/1.png"
)

@Preview(showBackground = true, widthDp = 320, heightDp = 480)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun PokemonDetailScreenPreview() {
    PokemonAppTheme {
        PokemonDetailScreen(
            uiState = UiState.Success(sampleDetail),
            onBack = {}
        )
    }
}
