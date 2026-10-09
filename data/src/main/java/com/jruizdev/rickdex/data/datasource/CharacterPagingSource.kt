package com.jruizdev.rickdex.data.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.jruizdev.rickdex.domain.exception.RateLimitException
import com.jruizdev.rickdex.domain.model.CharacterBO
import retrofit2.HttpException

class CharacterPagingSource(
    private val datasource: CharacterDatasource,
    private val name: String? = null,
    private val status: String? = null,
    private val species: String? = null,
    private val type: String? = null,
    private val gender: String? = null
) : PagingSource<Int, CharacterBO>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CharacterBO> {
        val page = params.key ?: 1
        return try {
            val response = datasource.getCharacters(
                page = page,
                name = name,
                status = status,
                species = species,
                type = type,
                gender = gender
            )

            LoadResult.Page(
                data = response.characters,
                prevKey = if (page == 1) null else page - 1,
                nextKey = response.info.next
            )

        } catch (e: HttpException) {
            val error = if (e.code() == 429) RateLimitException() else e
            LoadResult.Error(error)
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, CharacterBO>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}
