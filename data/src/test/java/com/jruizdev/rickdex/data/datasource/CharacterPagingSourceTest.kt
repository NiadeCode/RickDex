package com.jruizdev.rickdex.data.datasource

import androidx.paging.PagingSource
import com.jruizdev.rickdex.domain.model.CharacterBO
import com.jruizdev.rickdex.domain.model.CharacterResponseBO
import com.jruizdev.rickdex.domain.model.InfoBO
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class CharacterPagingSourceTest {

    private lateinit var datasource: CharacterDatasource
    private lateinit var pagingSource: CharacterPagingSource

    @Before
    fun setUp() {
        datasource = mockk()
        pagingSource = CharacterPagingSource(datasource)
    }

    @Test
    fun `load returns Page on successful load of first page`() = runTest {
        val character = CharacterBO(id = 1, name = "Rick Sanchez")
        val response = CharacterResponseBO(
            info = InfoBO(pages = 3, next = 2),
            characters = listOf(character)
        )

        coEvery { datasource.getCharacters(page = 1) } returns response

        val result = pagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = 20,
                placeholdersEnabled = false
            )
        )

        assertTrue(result is PagingSource.LoadResult.Page)
        val pageResult = result as PagingSource.LoadResult.Page
        assertEquals(listOf(character), pageResult.data)
        assertEquals(null, pageResult.prevKey)
        assertEquals(2, pageResult.nextKey)
    }

    @Test
    fun `load returns Page with correct keys on second page`() = runTest {
        val character = CharacterBO(id = 2, name = "Morty Smith")
        val response = CharacterResponseBO(
            info = InfoBO(pages = 3, next = 3),
            characters = listOf(character)
        )

        coEvery { datasource.getCharacters(page = 2) } returns response

        val result = pagingSource.load(
            PagingSource.LoadParams.Append(
                key = 2,
                loadSize = 20,
                placeholdersEnabled = false
            )
        )

        assertTrue(result is PagingSource.LoadResult.Page)
        val pageResult = result as PagingSource.LoadResult.Page
        assertEquals(listOf(character), pageResult.data)
        assertEquals(1, pageResult.prevKey)
        assertEquals(3, pageResult.nextKey)
    }

    @Test
    fun `load returns Page with null nextKey on last page`() = runTest {
        val character = CharacterBO(id = 3, name = "Summer Smith")
        val response = CharacterResponseBO(
            info = InfoBO(pages = 3, next = null),
            characters = listOf(character)
        )

        coEvery { datasource.getCharacters(page = 3) } returns response

        val result = pagingSource.load(
            PagingSource.LoadParams.Append(
                key = 3,
                loadSize = 20,
                placeholdersEnabled = false
            )
        )

        assertTrue(result is PagingSource.LoadResult.Page)
        val pageResult = result as PagingSource.LoadResult.Page
        assertEquals(listOf(character), pageResult.data)
        assertEquals(2, pageResult.prevKey)
        assertEquals(null, pageResult.nextKey)
    }

    @Test
    fun `load returns Error when datasource throws exception`() = runTest {
        val exception = RuntimeException("Network error")
        coEvery { datasource.getCharacters(page = 1) } throws exception

        val result = pagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = 1,
                loadSize = 20,
                placeholdersEnabled = false
            )
        )

        assertTrue(result is PagingSource.LoadResult.Error)
        val errorResult = result as PagingSource.LoadResult.Error
        assertEquals(exception, errorResult.throwable)
    }
}
