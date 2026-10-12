package com.jruizdev.rickdex.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jruizdev.rickdex.data.database.dao.CharacterDao
import com.jruizdev.rickdex.data.database.dao.CharacterRemoteKeysDao
import com.jruizdev.rickdex.data.database.entity.CharacterEntity
import com.jruizdev.rickdex.data.database.entity.CharacterRemoteKeysEntity

@Database(
    entities = [CharacterEntity::class, CharacterRemoteKeysEntity::class],
    version = 2,
    exportSchema = false
)
abstract class RickDexDatabase : RoomDatabase() {
    abstract fun characterDao(): CharacterDao
    abstract fun characterRemoteKeysDao(): CharacterRemoteKeysDao
}
