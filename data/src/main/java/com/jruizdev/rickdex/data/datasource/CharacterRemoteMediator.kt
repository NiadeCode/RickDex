package com.jruizdev.rickdex.data.datasource

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.jruizdev.rickdex.data.database.CharacterCacheValidator
import com.jruizdev.rickdex.data.database.RickDexDatabase
import com.jruizdev.rickdex.data.database.entity.CharacterEntity
import com.jruizdev.rickdex.data.database.entity.CharacterRemoteKeysEntity
import com.jruizdev.rickdex.data.mapper.mapToEntity
import com.jruizdev.rickdex.domain.exception.RateLimitException
import retrofit2.HttpException

@OptIn(ExperimentalPagingApi::class)
class CharacterRemoteMediator(
    private val api: CharactersApi,
    private val database: RickDexDatabase,
    private val cacheValidator: CharacterCacheValidator,
    private val name: String? = null,
    private val status: String? = null,
    private val species: String? = null,
    private val type: String? = null,
    private val gender: String? = null
) : RemoteMediator<Int, CharacterEntity>() {

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, CharacterEntity>
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> {
                val hasNoFilter = name.isNullOrBlank() && status.isNullOrBlank() &&
                        species.isNullOrBlank() && type.isNullOrBlank() && gender.isNullOrBlank()
                val isCacheValid = !cacheValidator.isCacheExpired(timeoutHours = 24)
                val hasDataInDb = database.characterDao().getCharacterCount() > 0

                if (hasNoFilter && isCacheValid && hasDataInDb) {
                    return MediatorResult.Success(endOfPaginationReached = false)
                }
                1
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                if (remoteKeys == null) {
                    val firstItem = state.firstItemOrNull()
                    if (firstItem == null) {
                        return MediatorResult.Success(endOfPaginationReached = false)
                    } else {
                        return MediatorResult.Success(endOfPaginationReached = true)
                    }
                }
                val prevKey = remoteKeys.prevKey
                    ?: return MediatorResult.Success(endOfPaginationReached = true)
                prevKey
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                if (remoteKeys == null) {
                    val lastItem = state.lastItemOrNull()
                    if (lastItem == null) {
                        return MediatorResult.Success(endOfPaginationReached = false)
                    } else {
                        return MediatorResult.Success(endOfPaginationReached = true)
                    }
                }
                val nextKey = remoteKeys.nextKey
                    ?: return MediatorResult.Success(endOfPaginationReached = true)
                nextKey
            }
        }

        return try {
            val response = api.getCharacters(
                page = page,
                name = name,
                status = status,
                specie = species,
                type = type,
                gender = gender
            )

            val endOfPaginationReached = response.info.next == null
            val nextKey = if (endOfPaginationReached) null else page + 1
            val prevKey = if (page == 1) null else page - 1

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    database.characterRemoteKeysDao().clearRemoteKeys()
                    database.characterDao().clearAllCharacters()
                }

                val keys = response.results.map { characterDto ->
                    CharacterRemoteKeysEntity(
                        characterId = characterDto.id,
                        prevKey = prevKey,
                        nextKey = nextKey
                    )
                }

                    database.characterRemoteKeysDao().insertAll(keys)
                database.characterDao().insertCharacters(response.results.map { it.mapToEntity() })
                cacheValidator.updateLastUpdatedTime()
            }

            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: HttpException) {
            val error = if (e.code() == 429) RateLimitException() else e
            MediatorResult.Error(error)
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, CharacterEntity>): CharacterRemoteKeysEntity? {
        return state.lastItemOrNull()?.let { character ->
            database.characterRemoteKeysDao().getRemoteKeysByCharacterId(character.id)
        }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, CharacterEntity>): CharacterRemoteKeysEntity? {
        return state.firstItemOrNull()?.let { character ->
            database.characterRemoteKeysDao().getRemoteKeysByCharacterId(character.id)
        }
    }
}
