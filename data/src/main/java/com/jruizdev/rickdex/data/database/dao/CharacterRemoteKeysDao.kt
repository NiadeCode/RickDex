package com.jruizdev.rickdex.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jruizdev.rickdex.data.database.entity.CharacterRemoteKeysEntity

@Dao
interface CharacterRemoteKeysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKeys: List<CharacterRemoteKeysEntity>)

    @Query("SELECT * FROM character_remote_keys WHERE characterId = :characterId")
    suspend fun getRemoteKeysByCharacterId(characterId: Int): CharacterRemoteKeysEntity?

    @Query("DELETE FROM character_remote_keys")
    suspend fun clearRemoteKeys()
}
