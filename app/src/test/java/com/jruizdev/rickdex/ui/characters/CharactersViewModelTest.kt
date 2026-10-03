package com.jruizdev.rickdex.ui.characters

import com.jruizdev.rickdex.domain.GetCharactersUseCase
import com.jruizdev.rickdex.domain.model.CharacterResponseBO
import com.jruizdev.rickdex.domain.model.InfoBO
import com.jruizdev.rickdex.domain.repository.CharacterRepository
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CharactersViewModelTest {
    private lateinit var viewModel: CharactersViewModel
    private lateinit var getCharactersUseCase: GetCharactersUseCase
    private lateinit var characterRepository: CharacterRepository

    private val testDispatcher = StandardTestDispatcher()

    private val characterResponseBO1: CharacterResponseBO = CharacterResponseBO(
        info = InfoBO(
            pages = 3,
            next = 2,
        ), characters = emptyList()
    )
    private val characterResponseBO2: CharacterResponseBO = CharacterResponseBO(
        info = InfoBO(
            pages = 3,
            next = 3,
        ), characters = emptyList()
    )
    private val characterResponseBO3: CharacterResponseBO = CharacterResponseBO(
        info = InfoBO(
            pages = 3,
            next = null,
        ), characters = emptyList()
    )
    private val characterVOList: List<CharacterVO> = emptyList()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        characterRepository = mockk()
        getCharactersUseCase = mockk()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `test loadCharacters success`() = runTest(testDispatcher) {
        coEvery { getCharactersUseCase(1) } returns Result.success(characterResponseBO1)

        viewModel = CharactersViewModel(getCharactersUseCase)
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(characterVOList, viewModel.state.value.characters)
        assertEquals(false, viewModel.state.value.isLoading)
        assertEquals(null, viewModel.state.value.error)
        assertEquals(2, viewModel.state.value.page)
    }

    @Test
    fun `test load more loadCharacters success`() = runTest(testDispatcher) {
        coEvery { getCharactersUseCase(1) } returns Result.success(characterResponseBO1)
        coEvery { getCharactersUseCase(2) } returns Result.success(characterResponseBO2)
        coEvery { getCharactersUseCase(3) } returns Result.success(characterResponseBO3)

        viewModel = CharactersViewModel(getCharactersUseCase)
        testDispatcher.scheduler.advanceUntilIdle()
        assertEquals(2, viewModel.state.value.page)

        viewModel.sendIntent(CharactersIntent.LoadCharacters)
        testDispatcher.scheduler.advanceUntilIdle()
        assertEquals(3, viewModel.state.value.page)

        viewModel.sendIntent(CharactersIntent.LoadCharacters)
        testDispatcher.scheduler.advanceUntilIdle()
        assertEquals(-1, viewModel.state.value.page)

        assertEquals(characterVOList, viewModel.state.value.characters)
        assertEquals(false, viewModel.state.value.isLoading)
        assertEquals(null, viewModel.state.value.error)
    }
}
