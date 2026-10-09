package com.jruizdev.rickdex.ui.characters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.jruizdev.rickdex.domain.GetCharactersUseCase
import com.jruizdev.rickdex.ui.characters.mapper.toVO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel @Inject constructor(
    private val getCharactersUseCase: GetCharactersUseCase,
) : ViewModel() {

    val characters: Flow<PagingData<CharacterVO>> = getCharactersUseCase().map { pagingData ->
            pagingData.map { it.toVO() }
        }.cachedIn(viewModelScope)

    private val _intent = MutableSharedFlow<CharactersIntent>(
        replay = 0,
        extraBufferCapacity = 64,
        onBufferOverflow = kotlinx.coroutines.channels.BufferOverflow.DROP_OLDEST
    )
    val intent: SharedFlow<CharactersIntent> = _intent.asSharedFlow()

    private val _effect = MutableSharedFlow<CharactersEffect>(
        replay = 0,
        extraBufferCapacity = 64,
        onBufferOverflow = kotlinx.coroutines.channels.BufferOverflow.DROP_OLDEST
    )
    val effect: SharedFlow<CharactersEffect> = _effect.asSharedFlow()

    init {
        viewModelScope.launch {
            intent.collect { action ->
                handleIntent(action)
            }
        }
    }

    fun sendIntent(action: CharactersIntent) {
        viewModelScope.launch {
            _intent.emit(action)
        }
    }

    private fun handleIntent(intent: CharactersIntent) {
        when (intent) {
            is CharactersIntent.NavigateToCharacterDetails -> {
                viewModelScope.launch {
                    _effect.emit(CharactersEffect.NavigateToDetail(intent.characterId))
                }
            }
        }
    }
}
