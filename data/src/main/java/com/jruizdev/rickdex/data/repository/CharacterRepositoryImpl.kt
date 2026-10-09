package com.jruizdev.rickdex.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.jruizdev.rickdex.data.database.CharacterCacheValidator
import com.jruizdev.rickdex.data.database.RickDexDatabase
import com.jruizdev.rickdex.data.database.dao.CharacterDao
import com.jruizdev.rickdex.data.datasource.CharacterDatasource
import com.jruizdev.rickdex.data.datasource.CharacterRemoteMediator
import com.jruizdev.rickdex.data.datasource.CharactersApi
import com.jruizdev.rickdex.data.mapper.mapToBO
import com.jruizdev.rickdex.domain.model.CharacterBO
import com.jruizdev.rickdex.domain.model.CharacterResponseBO
import com.jruizdev.rickdex.domain.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor(
    private val datasource: CharacterDatasource,
    private val api: CharactersApi,
    private val database: RickDexDatabase,
    private val dao: CharacterDao,
    private val cacheValidator: CharacterCacheValidator
) : CharacterRepository {

    override suspend fun getCharacters(page: Int): CharacterResponseBO {
        return datasource.getCharacters(page, null, null, null, null, null)
    }

    @OptIn(ExperimentalPagingApi::class)
    override fun getCharactersStream(
        name: String?,
        status: String?,
        species: String?,
        type: String?,
        gender: String?
    ): Flow<PagingData<CharacterBO>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                prefetchDistance = 3,
                enablePlaceholders = false
            ),
            remoteMediator = CharacterRemoteMediator(
                api = api,
                database = database,
                cacheValidator = cacheValidator,
                name = name,
                status = status,
                species = species,
                type = type,
                gender = gender
            ),
            pagingSourceFactory = {
                dao.getFilteredCharactersPagingSource(
                    name = name,
                    status = status,
                    species = species,
                    type = type,
                    gender = gender
                )
            }
        ).flow.map { pagingData ->
            pagingData.map { entity -> entity.mapToBO() }
        }
    }

    override suspend fun getCharacter(id: Int): CharacterBO {
        return datasource.getCharacter(id)
    }
}
