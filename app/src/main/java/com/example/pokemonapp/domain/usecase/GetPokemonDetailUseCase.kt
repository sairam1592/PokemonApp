package com.example.pokemonapp.domain.usecase

import com.example.pokemonapp.data.mapper.PokemonDetailDtoToDomainMapper
import com.example.pokemonapp.domain.model.PokemonDetail
import com.example.pokemonapp.domain.repository.PokemonRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetPokemonDetailUseCase @Inject constructor(
    private val repo: PokemonRepository,
    private val mapper: PokemonDetailDtoToDomainMapper
) {
    operator fun invoke(idOrName: String): Flow<PokemonDetail> =
        repo.getPokemonDetail(idOrName)
            .map { dto -> mapper(dto) }
}