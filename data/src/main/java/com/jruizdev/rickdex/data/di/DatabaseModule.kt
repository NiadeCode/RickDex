package com.jruizdev.rickdex.data.di

import android.content.Context
import androidx.room.Room
import com.jruizdev.rickdex.data.database.RickDexDatabase
import com.jruizdev.rickdex.data.database.dao.CharacterDao
import com.jruizdev.rickdex.data.database.dao.CharacterRemoteKeysDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideRickDexDatabase(
        @ApplicationContext context: Context
    ): RickDexDatabase {
        return Room.databaseBuilder(
            context,
            RickDexDatabase::class.java,
            "rickdex_database"
        )
        .fallbackToDestructiveMigration(dropAllTables = true)
        .build()
    }

    @Provides
    @Singleton
    fun provideCharacterDao(
        database: RickDexDatabase
    ): CharacterDao {
        return database.characterDao()
    }

    @Provides
    @Singleton
    fun provideCharacterRemoteKeysDao(
        database: RickDexDatabase
    ): CharacterRemoteKeysDao {
        return database.characterRemoteKeysDao()
    }
}
