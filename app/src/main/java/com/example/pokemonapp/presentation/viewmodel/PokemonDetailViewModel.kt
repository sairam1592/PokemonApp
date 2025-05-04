package com.example.pokemonapp.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokemonapp.common.ui.UiState
import com.example.pokemonapp.core.NavArgs
import com.example.pokemonapp.core.toUiMessage
import com.example.pokemonapp.domain.model.PokemonDetail
import com.example.pokemonapp.domain.usecase.GetPokemonDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class PokemonDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    getDetailUseCase: GetPokemonDetailUseCase
) : ViewModel() {

    private val idOrName: String = checkNotNull(savedStateHandle[NavArgs.ID_OR_NAME])

    val uiState: StateFlow<UiState<PokemonDetail>> =
        getDetailUseCase(idOrName)
            .map<PokemonDetail, UiState<PokemonDetail>> { UiState.Success(it) }
            .onStart { emit(UiState.Loading) }
            .catch { e ->
                emit(UiState.Error(e.toUiMessage()))
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.Lazily,
                initialValue = UiState.Loading
            )
}
