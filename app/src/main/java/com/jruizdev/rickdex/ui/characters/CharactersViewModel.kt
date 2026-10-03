package com.jruizdev.rickdex.ui.characters

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jruizdev.rickdex.domain.GetCharactersUseCase
import com.jruizdev.rickdex.domain.model.CharacterResponseBO
import com.jruizdev.rickdex.ui.characters.mapper.toVO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel @Inject constructor(
    private val getCharactersUseCase: GetCharactersUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(CharactersUiState())
    val state: StateFlow<CharactersUiState> = _state.asStateFlow()

    private val _intent = MutableSharedFlow<CharactersIntent>(
        replay = 0,
        extraBufferCapacity = 64,
        onBufferOverflow = kotlinx.coroutines.channels.BufferOverflow.DROP_OLDEST
    )
    val intent: SharedFlow<CharactersIntent> = _intent.asSharedFlow()

    private var currentPage = 1

    init {
        viewModelScope.launch() {
            intent.collect { action ->
                handleIntent(action)
            }
        }
        sendIntent(CharactersIntent.LoadCharacters)
    }

    fun sendIntent(action: CharactersIntent) {
        viewModelScope.launch {
            _intent.emit(action)
        }
    }

    private fun handleIntent(intent: CharactersIntent) {
        when (intent) {
            is CharactersIntent.LoadCharacters -> loadCharacters()
            is CharactersIntent.NavigateToCharacterDetails -> {
                // Handled via navigation events or effects if needed
            }
        }
    }

    private fun loadCharacters() {
        Log.d("CharactersViewModel", "loadCharacters called")
        Log.d("CharactersViewModel", "current page = $currentPage")

        if (currentPage == -1) {
            return
        }

        viewModelScope.launch {
            setLoading()
            getCharactersUseCase(currentPage)
                .onSuccess { success ->
                    digestSuccess(success)
                }
                .onFailure { error ->
                    digestError(error)
                }
        }
    }

    private fun digestError(error: Throwable) {
        _state.value = _state.value.copy(
            isLoading = false,
            error = error.message
        )
    }

    private fun digestSuccess(success: CharacterResponseBO) {
        currentPage = (success.info.next ?: currentPage)
        _state.value = _state.value.copy(
            page = success.info.next ?: -1,
            isLoading = false,
            error = null,
            characters = _state.value.characters + success.characters.map { it.toVO() }
        )
    }

    private fun setLoading() {
        _state.value = _state.value.copy(
            isLoading = true,
            error = null,
        )
    }
}
