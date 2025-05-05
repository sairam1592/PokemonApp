package com.example.pokemonapp.pokemon.presentation.view.compose

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.example.pokemonapp.domain.model.PokemonSummary
import com.example.pokemonapp.presentation.view.compose.PokemonList
import com.example.pokemonapp.ui.theme.PokemonAppTheme
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class PokemonListScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun pokemonList_displaysItems_and_clickTriggersCallback() {
        val testList = listOf(
            PokemonSummary("Bulbasaur"),
            PokemonSummary("Charmander")
        )
        var clickedName: String? = null

        composeTestRule.setContent {
            PokemonAppTheme {
                PokemonList(
                    pokemonList = testList,
                    onSelect = { clickedName = it }
                )
            }
        }

        composeTestRule.onNodeWithTag("pokemon_Bulbasaur", useUnmergedTree = true).assertExists()
        composeTestRule.onNodeWithTag("pokemon_Charmander", useUnmergedTree = true).assertExists()
        composeTestRule.onNodeWithTag("pokemon_Charmander", useUnmergedTree = true).performClick()
        assertEquals("Charmander", clickedName)
    }
}
