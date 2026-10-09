package com.jruizdev.rickdex.ui.characters

import androidx.paging.PagingData
import com.jruizdev.rickdex.domain.GetCharactersUseCase
import com.jruizdev.rickdex.domain.model.CharacterBO
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CharactersViewModelTest {

    private lateinit var viewModel: CharactersViewModel
    private lateinit var getCharactersUseCase: GetCharactersUseCase
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        getCharactersUseCase = mockk()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `test characters flow emits paging data`() = runTest(testDispatcher) {
        val characterBO = CharacterBO(id = 1, name = "Rick Sanchez")
        val pagingData = PagingData.from(listOf(characterBO))

        every { getCharactersUseCase(name = any()) } returns flowOf(pagingData)

        viewModel = CharactersViewModel(getCharactersUseCase)
        testDispatcher.scheduler.advanceUntilIdle()

        val resultPagingData = viewModel.characters.first()
        assertNotNull(resultPagingData)
    }

    @Test
    fun `test NavigateToCharacterDetails intent emits NavigateToDetail effect`() = runTest(testDispatcher) {
        every { getCharactersUseCase(name = any()) } returns flowOf(PagingData.empty())

        viewModel = CharactersViewModel(getCharactersUseCase)

        var emittedEffect: CharactersEffect? = null
        val job = launch {
            viewModel.effect.collect { effect ->
                emittedEffect = effect
            }
        }

        viewModel.sendIntent(CharactersIntent.NavigateToCharacterDetails(characterId = 42))
        testDispatcher.scheduler.advanceUntilIdle()

        assertNotNull(emittedEffect)
        assertEquals(CharactersEffect.NavigateToDetail(42), emittedEffect)

        job.cancel()
    }

    @Test
    fun `test UpdateQuery intent calls getCharactersUseCase with query`() = runTest(testDispatcher) {
        every { getCharactersUseCase(name = any()) } returns flowOf(PagingData.empty())

        viewModel = CharactersViewModel(getCharactersUseCase)

        val job = launch {
            viewModel.characters.collect {}
        }

        viewModel.sendIntent(CharactersIntent.UpdateQuery(query = "Rick", force = true))
        testDispatcher.scheduler.advanceUntilIdle()

        verify { getCharactersUseCase(name = "Rick") }

        job.cancel()
    }
}
