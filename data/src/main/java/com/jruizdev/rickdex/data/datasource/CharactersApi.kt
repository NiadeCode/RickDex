package com.jruizdev.rickdex.data.datasource

import com.jruizdev.rickdex.data.model.CharacterDto
import com.jruizdev.rickdex.data.model.CharacterResponseDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CharactersApi {
    @GET("character" )
    suspend fun getCharacters(
        @Query("page") page: Int
    ): CharacterResponseDto

    @GET("character/{id}")
    suspend fun getCharacter(
        @Path("id") id: Int
    ): CharacterDto

}