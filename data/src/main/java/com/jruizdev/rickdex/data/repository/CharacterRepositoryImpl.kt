package com.jruizdev.rickdex.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.jruizdev.rickdex.data.datasource.CharacterDatasource
import com.jruizdev.rickdex.data.datasource.CharacterPagingSource
import com.jruizdev.rickdex.domain.model.CharacterBO
import com.jruizdev.rickdex.domain.model.CharacterResponseBO
import com.jruizdev.rickdex.domain.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor(
    private val datasource: CharacterDatasource
) : CharacterRepository {
    override suspend fun getCharacters(page: Int): CharacterResponseBO {
        return datasource.getCharacters(page, null, null, null, null, null)
    }

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
                prefetchDistance = 1,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                CharacterPagingSource(
                    datasource = datasource,
                    name = name,
                    status = status,
                    species = species,
                    type = type,
                    gender = gender
                )
            }
        ).flow
    }

    override suspend fun getCharacter(id: Int): CharacterBO {
        return datasource.getCharacter(id)
    }
}
