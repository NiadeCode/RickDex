package com.jruizdev.rickdex.data.datasource

import com.jruizdev.rickdex.data.database.dao.CharacterDao
import com.jruizdev.rickdex.data.database.entity.CharacterEntity
import com.jruizdev.rickdex.data.database.entity.LocationEntity
import com.jruizdev.rickdex.data.model.CharacterDto
import com.jruizdev.rickdex.data.model.CharacterResponseDto
import com.jruizdev.rickdex.data.model.InfoDto
import com.jruizdev.rickdex.data.model.LocationDto
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class CharacterDatasourceImplTest {

    private lateinit var api: CharactersApi
    private lateinit var dao: CharacterDao
    private lateinit var datasource: CharacterDatasourceImpl

    @Before
    fun setUp() {
        api = mockk()
        dao = mockk(relaxed = true)
        datasource = CharacterDatasourceImpl(api, dao)
    }

    @Test
    fun `getCharacters returns mapped response bo and caches entities`() = runTest {
        val location = LocationDto("Earth", "url")
        val characterDto = CharacterDto(
            id = 1,
            name = "Rick Sanchez",
            status = "Alive",
            species = "Human",
            type = "",
            gender = "Male",
            origin = location,
            location = location,
            image = "url",
            episode = emptyList(),
            url = "url",
            created = "date"
        )
        val infoDto = InfoDto(count = 1, pages = 1, next = "https://rickandmortyapi.com/api/character/?page=2", prev = null)
        val responseDto = CharacterResponseDto(info = infoDto, results = listOf(characterDto))

        coEvery { api.getCharacters(1) } returns responseDto

        val result = datasource.getCharacters(1)

        assertEquals(1, result.characters.size)
        assertEquals("Rick Sanchez", result.characters[0].name)
        assertEquals(2, result.info.next)

        coVerify(exactly = 1) { dao.insertCharacters(any()) }
    }

    @Test
    fun `getCharacter returns mapped character bo and inserts entity into dao`() = runTest {
        val location = LocationDto("Earth", "url")
        val characterDto = CharacterDto(
            id = 1,
            name = "Rick Sanchez",
            status = "Alive",
            species = "Human",
            type = "",
            gender = "Male",
            origin = location,
            location = location,
            image = "url",
            episode = emptyList(),
            url = "url",
            created = "date"
        )

        coEvery { api.getCharacter(1) } returns characterDto

        val result = datasource.getCharacter(1)

        assertEquals(1, result.id)
        assertEquals("Rick Sanchez", result.name)

        coVerify(exactly = 1) { dao.insertCharacter(any()) }
    }

    @Test
    fun `getCharacter falls back to dao when api fails`() = runTest {
        val entity = CharacterEntity(
            id = 1,
            name = "Rick Sanchez",
            status = "Alive",
            species = "Human",
            type = "",
            gender = "Male",
            origin = LocationEntity("Earth", "url"),
            location = LocationEntity("Earth", "url"),
            image = "url",
            episodes = "",
            url = "url",
            created = "date"
        )

        coEvery { api.getCharacter(1) } throws RuntimeException("Network error")
        coEvery { dao.getCharacterById(1) } returns entity

        val result = datasource.getCharacter(1)

        assertEquals(1, result.id)
        assertEquals("Rick Sanchez", result.name)
        coVerify(exactly = 1) { dao.getCharacterById(1) }
    }

    @Test(expected = RuntimeException::class)
    fun `getCharacters throws exception when api fails`() = runTest {
        coEvery { api.getCharacters(1) } throws RuntimeException("Network error")
        datasource.getCharacters(1)
    }

    @Test(expected = RuntimeException::class)
    fun `getCharacter throws exception when api fails and cached entity is null`() = runTest {
        coEvery { api.getCharacter(1) } throws RuntimeException("Network error")
        coEvery { dao.getCharacterById(1) } returns null
        datasource.getCharacter(1)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `getCharacters throws IllegalArgumentException when page is negative`() = runTest {
        datasource.getCharacters(-1)
    }

    @Test(expected = RuntimeException::class)
    fun `getCharacters throws exception when page is out of range`() = runTest {
        coEvery { api.getCharacters(9999) } throws RuntimeException("Out of range")
        datasource.getCharacters(9999)
    }
}
