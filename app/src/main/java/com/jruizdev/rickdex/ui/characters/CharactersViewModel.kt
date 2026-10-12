package com.jruizdev.rickdex.ui.characters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.jruizdev.rickdex.domain.GetCharactersUseCase
import com.jruizdev.rickdex.ui.characters.CharactersEffect.NavigateToDetail
import com.jruizdev.rickdex.ui.characters.mapper.toVO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.transformLatest
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class CharactersViewModel @Inject constructor(
    private val getCharactersUseCase: GetCharactersUseCase,
) : ViewModel() {

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

    private val _searchQueryIntent = MutableStateFlow(
        CharactersIntent.UpdateQuery(query = "", force = true)
    )

    val characters: Flow<PagingData<CharacterVO>> = _searchQueryIntent
        .transformLatest { action ->
            if (action.force) {
                emit(action.query)
            } else {
                delay(5.seconds)
                emit(action.query)
            }
        }
        .distinctUntilChanged()
        .flatMapLatest { query ->
            getCharactersUseCase(name = query.ifBlank { null })
        }
        .map { pagingData ->
            pagingData.map { it.toVO() }
        }
        .cachedIn(viewModelScope)

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

    private suspend fun handleIntent(intent: CharactersIntent) {
        when (intent) {
            is CharactersIntent.NavigateToCharacterDetails -> {
                _effect.emit(NavigateToDetail(intent.characterId))
            }

            is CharactersIntent.UpdateQuery -> {
                _searchQueryIntent.value = intent
            }
        }
    }
}
