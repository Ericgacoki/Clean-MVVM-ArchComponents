package com.k0d4black.theforce.data.remote.repository

import com.google.common.truth.Truth
import com.k0d4black.theforce.data.remote.BaseTest
import com.k0d4black.theforce.data.remote.helpers.Constants.EXISTING_CHARACTER_URL
import com.k0d4black.theforce.domain.repository.ICharacterDetailsRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

internal class CharacterDetailsRepositoryTest : BaseTest() {

    private lateinit var characterDetailsRepository: ICharacterDetailsRepository

    @Before
    override fun setup() {
        super.setup()
        characterDetailsRepository = CharacterDetailsRepository(starWarsApiService)
    }

    @Test
    fun `given a character id when executed then return character details`() {
        runBlocking {
            val filmsFlow = characterDetailsRepository.getCharacterFilms(EXISTING_CHARACTER_URL)
            val speciesFlow = characterDetailsRepository.getCharacterSpecies(EXISTING_CHARACTER_URL)
            val planetFlow = characterDetailsRepository.getCharacterPlanet(EXISTING_CHARACTER_URL)

            filmsFlow.collect { films ->
                Truth.assertThat(films.size).isAtLeast(1)
            }
            planetFlow.collect { planet ->
                Truth.assertThat(planet.name).matches("Tatooine")
            }
            speciesFlow.collect { species ->
                Truth.assertThat(species.size).isAtLeast(1)
            }
        }
    }

}