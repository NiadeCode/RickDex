package repository

import com.jruizdev.rickdex.domain.model.CharacterBO
import com.jruizdev.rickdex.domain.repository.CharacterRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class CharacterRepositoryTest {

    private val repository: CharacterRepository = mockk()
    val mockCharacters = listOf(
        CharacterBO(1, "Rick Sanchez"),
        CharacterBO(2, "Morty Smith"),
        CharacterBO(3, "Summer Smith"),
        CharacterBO(4, "Beth Smith"),
        CharacterBO(5, "Jerry Smith")
    )

    @Test
    fun `should get all characters`() = runTest {
        //1 setup
        coEvery { repository.getCharacters() } returns mockCharacters
        //2 call
        val result = repository.getCharacters()
        //3 verify
        assertEquals(5, result.size)
        assertEquals(mockCharacters, result)
        coVerify(exactly = 1) { repository.getCharacters() }
    }

    @Test
    fun `should get a character by id`() = runTest {
        coEvery { repository.getCharacter(1) } returns mockCharacters.first()

        val character = repository.getCharacter(1)

        assertEquals(1, character.id)
        assertEquals("Rick Sanchez", character.name)
        coVerify(exactly = 1) { repository.getCharacter(1) }
    }

    @Test
    fun `should throw an exception when getting a character by id`() = runTest {
        coEvery { repository.getCharacter(any()) } throws Exception("Error")
        try {
            repository.getCharacter(1)
        } catch (e: Exception) {
            assertEquals("Error", e.message)
        }
        coVerify(exactly = 1) { repository.getCharacter(1) }
    }
}