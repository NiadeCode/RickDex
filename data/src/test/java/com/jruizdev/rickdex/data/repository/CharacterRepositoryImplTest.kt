package com.jruizdev.rickdex.data.repository

import com.jruizdev.rickdex.data.database.CharacterCacheValidator
import com.jruizdev.rickdex.data.database.RickDexDatabase
import com.jruizdev.rickdex.data.database.dao.CharacterDao
import com.jruizdev.rickdex.data.datasource.CharacterDatasource
import com.jruizdev.rickdex.data.datasource.CharactersApi
import com.jruizdev.rickdex.domain.model.CharacterBO
import com.jruizdev.rickdex.domain.model.CharacterResponseBO
import com.jruizdev.rickdex.domain.model.InfoBO
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class CharacterRepositoryImplTest {

    private lateinit var datasource: CharacterDatasource
    private lateinit var api: CharactersApi
    private lateinit var database: RickDexDatabase
    private lateinit var dao: CharacterDao
    private lateinit var cacheValidator: CharacterCacheValidator
    private lateinit var repository: CharacterRepositoryImpl

    @Before
    fun setUp() {
        datasource = mockk()
        api = mockk()
        database = mockk()
        dao = mockk()
        cacheValidator = mockk(relaxed = true)
        repository = CharacterRepositoryImpl(datasource, api, database, dao, cacheValidator)
    }

    @Test
    fun `getCharacters returns character response from datasource`() = runTest {
        val character = CharacterBO(id = 1, name = "Rick Sanchez")
        val responseBO = CharacterResponseBO(
            info = InfoBO(pages = 1, next = 2),
            characters = listOf(character)
        )

        coEvery { datasource.getCharacters(1) } returns responseBO

        val result = repository.getCharacters(1)

        assertEquals(1, result.characters.size)
        assertEquals("Rick Sanchez", result.characters[0].name)
        assertEquals(2, result.info.next)
    }

    @Test
    fun `getCharacter returns character from datasource`() = runTest {
        val character = CharacterBO(id = 1, name = "Rick Sanchez")

        coEvery { datasource.getCharacter(1) } returns character

        val result = repository.getCharacter(1)

        assertEquals(1, result.id)
        assertEquals("Rick Sanchez", result.name)
    }

    @Test(expected = RuntimeException::class)
    fun `getCharacters throws exception when datasource fails`() = runTest {
        coEvery { datasource.getCharacters(1) } throws RuntimeException("Datasource error")
        repository.getCharacters(1)
    }

    @Test(expected = RuntimeException::class)
    fun `getCharacter throws exception when datasource fails`() = runTest {
        coEvery { datasource.getCharacter(1) } throws RuntimeException("Datasource error")
        repository.getCharacter(1)
    }
}
