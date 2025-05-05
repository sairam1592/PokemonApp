package com.example.pokemonapp.data.datasource.api

object ApiConstants {
    const val POKEMON = "pokemon"
    const val ID_PARAM = "id"
    const val OFFSET_PARAM = "offset"
    const val LIMIT_PARAM = "limit"

    const val POKEMON_LIST_PATH = POKEMON
    const val POKEMON_DETAIL_PATH = "$POKEMON/{$ID_PARAM}"

    const val DEFAULT_OFFSET = 0
    const val DEFAULT_LIMIT = 50 // I am reducing API Limit as 50 as this is MVP, in future pagination need to be done
}