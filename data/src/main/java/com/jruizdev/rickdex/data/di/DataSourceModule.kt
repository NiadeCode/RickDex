package com.jruizdev.rickdex.data.di

import com.jruizdev.rickdex.data.datasource.CharacterDatasource
import com.jruizdev.rickdex.data.datasource.CharacterDatasourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Binds
    @Singleton
    abstract fun bindCharacterDatasource(
        impl: CharacterDatasourceImpl
    ): CharacterDatasource
}
