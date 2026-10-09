package com.jruizdev.rickdex.data.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jruizdev.rickdex.data.database.entity.CharacterEntity

@Dao
interface CharacterDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharacters(characters: List<CharacterEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharacter(character: CharacterEntity)

    @Query("SELECT * FROM characters WHERE id = :id")
    suspend fun getCharacterById(id: Int): CharacterEntity?

    @Query("SELECT COUNT(*) FROM characters")
    suspend fun getCharacterCount(): Int

    @Query("SELECT * FROM characters ORDER BY id ASC")
    fun getCharactersPagingSource(): PagingSource<Int, CharacterEntity>

    @Query(
        """
        SELECT * FROM characters 
        WHERE (:name IS NULL OR name LIKE '%' || :name || '%')
        AND (:status IS NULL OR status LIKE :status)
        AND (:species IS NULL OR species LIKE '%' || :species || '%')
        AND (:type IS NULL OR type LIKE '%' || :type || '%')
        AND (:gender IS NULL OR gender LIKE :gender)
        ORDER BY id ASC
        """
    )
    fun getFilteredCharactersPagingSource(
        name: String? = null,
        status: String? = null,
        species: String? = null,
        type: String? = null,
        gender: String? = null
    ): PagingSource<Int, CharacterEntity>

    @Query("DELETE FROM characters")
    suspend fun clearAllCharacters()
}
