package com.jruizdev.rickdex.ui.character_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jruizdev.rickdex.domain.GetCharacterDetailUseCase
import com.jruizdev.rickdex.ui.characters.mapper.toCharacterDetailVO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterDetailViewModel @Inject constructor(
    private val getCharacterDetailUseCase: GetCharacterDetailUseCase,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    suspend fun getCharacter(id: Int) = getCharacterDetailUseCase(id)

    private val _intent = MutableSharedFlow<CharacterDetailIntent>()
    val intent: SharedFlow<CharacterDetailIntent> = _intent.asSharedFlow()

    private val _state = MutableStateFlow<CharacterDetailUiState>(CharacterDetailUiState.Loading)
    val state: StateFlow<CharacterDetailUiState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            intent.collect { action ->
                handleIntent(action)
            }
        }
        savedStateHandle.get<String>("itemId")?.let {
            viewModelScope.launch {
                _intent.emit(CharacterDetailIntent.GetCharacter(it.toInt()))
            }
        }
    }

    private fun handleIntent(intent: CharacterDetailIntent) {
        when (intent) {
            is CharacterDetailIntent.GetCharacter -> {
                viewModelScope.launch {
                    _state.value = CharacterDetailUiState.Loading
                    val character = getCharacter(intent.id).toCharacterDetailVO()
                    _state.value = CharacterDetailUiState.Success(character)
                }
            }
        }
    }

}
